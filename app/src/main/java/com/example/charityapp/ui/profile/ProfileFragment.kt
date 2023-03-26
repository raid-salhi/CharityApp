package com.example.charityapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.charityapp.MainActivity
import com.example.charityapp.R
import com.example.charityapp.SignIn
import com.example.charityapp.classes.User
import com.example.charityapp.databinding.FragmentProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    private lateinit var logoutButton : Button
    private lateinit var database : DatabaseReference
    private var user : User? = null
    private var userUpdate : User? = null
    private var auth: FirebaseAuth? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.firstNameLayout.setEndIconOnClickListener {
           handleClick(binding.firstNameEditText)
        }
        binding.lastNameLayout.setEndIconOnClickListener {
            handleClick(binding.lastNameEditText)
        }
        binding.emailLayout.setEndIconOnClickListener {
            handleClick(binding.emailEditText)
        }
        binding.wilayaLayout.setStartIconOnClickListener {
            handleClick(binding.wilaya)
        }
        binding.bloodTypeLayout.setStartIconOnClickListener {
            handleClick(binding.bloodGroup)
        }

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            binding.profileImage.load(uri)
        }
        binding.profileImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        return root
    }

    private fun handleClick(it: View?) {
        if (it!!.isEnabled) {
            saveChanges(it)
        }
        else
            it.isEnabled=true
    }

    private fun saveChanges(view: View) {
        //TODO: implement later
        userUpdate = User()

        userUpdate!!.firstName= binding.firstNameEditText.text.toString()
        userUpdate!!.lastName=binding.lastNameEditText.text.toString()
        userUpdate!!.email=binding.emailEditText.text.toString()
        userUpdate!!.wilaya=binding.wilaya.text.toString()
        userUpdate!!.bloodGroup=binding.bloodGroup.text.toString()


        val userMap = mapOf<String,String>(
            "firstName" to userUpdate!!.firstName,
            "lastName" to userUpdate!!.lastName,
            "email" to userUpdate!!.email,
            "bloodGroup" to userUpdate!!.bloodGroup,
            "wilaya" to userUpdate!!.wilaya
        )
        database.child(auth!!.currentUser!!.uid).updateChildren(userMap).addOnSuccessListener {
            setUpTextFeilds(false)
        }

    }

    private fun setUpProfile(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            database.child(currentUser.uid).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = User()
                    user=snapshot.getValue(User::class.java)!!
                    setUpProfileWithUserInfo(user!!)
                    binding.animationView.visibility=View.GONE

                }

                private fun setUpProfileWithUserInfo(user: User) {
                    binding.firstNameEditText.setText(user.firstName)
                    binding.lastNameEditText.setText(user.lastName)
                    binding.emailEditText.setText(user.email)
                    binding.wilaya.setText(user.wilaya)
                    binding.bloodGroup.setText(user.bloodGroup)

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        lifecycleScope.launch {
            logoutButton = activity?.findViewById(R.id.logoutButton)!!
            logoutButton.visibility = View.VISIBLE
            database = Firebase.database.getReference("Users")
            auth = FirebaseAuth.getInstance()
            logoutButton.setOnClickListener {
                auth?.signOut()
                startActivity(Intent(requireContext(),SignIn::class.java))
            }
            val currentUser = auth!!.currentUser
            setUpProfile(currentUser)
            setUpTextFeilds(false)
            val bottomNavBar : BottomNavigationView? = activity?.findViewById(R.id.bottom_nav_view)
            if (bottomNavBar != null) {
                bottomNavBar.visibility=View.GONE
            }
        }
    }


    private fun setUpTextFeilds(state:Boolean) {
        binding.firstNameEditText.isEnabled = state
        binding.lastNameEditText.isEnabled=state
        binding.bloodGroup.isEnabled=state
        binding.wilaya.isEnabled=state
        binding.emailEditText.isEnabled=state
    }

}