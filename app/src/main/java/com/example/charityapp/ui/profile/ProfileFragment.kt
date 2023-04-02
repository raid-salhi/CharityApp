package com.example.charityapp.ui.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch
import java.io.File

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    private lateinit var logoutButton : Button
    private lateinit var database : DatabaseReference
    private lateinit var storage : StorageReference
    private var user : User? = null
    private var userUpdate : User? = null
    private var auth: FirebaseAuth? = null
    private lateinit var imgUri : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        handleIconClick()

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            binding.profileImage.load(uri)
            saveImageInFirebase(uri!!)
        }
        binding.profileImage.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.dialogTitle))
                .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->

                }
                .setPositiveButton(resources.getString(R.string.import_)) { dialog, which ->
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                }
                .show()

        }
        return root
    }

    private fun saveImageInFirebase(imgUri: Uri) {

        storage =FirebaseStorage.getInstance().getReference("Users/"+ auth!!.currentUser!!.uid)
        storage.putFile(imgUri).addOnSuccessListener {
            Toast.makeText(context, "upload success", Toast.LENGTH_SHORT).show()
        }


    }

    private fun handleIconClick() {
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
    }

    private fun handleClick(it: View?) {
        if (it!!.isEnabled) {
            saveChanges(it)
            it.isEnabled = false
        }
        else
            it.isEnabled=true
    }

    private fun saveChanges(view: View) {

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
        database.child(auth!!.currentUser!!.uid).updateChildren(userMap)


    }

    private fun setUpProfile(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            database.child(currentUser.uid).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = User()
                    user=snapshot.getValue(User::class.java)!!
                    setUpProfileWithUserInfo(user!!)
                    binding.animationView.visibility=View.GONE
                    binding.container.visibility=View.VISIBLE

                }

                private fun setUpProfileWithUserInfo(user: User) {
                    getImageFromFirebase()
                    binding.firstNameEditText.setText(user.firstName)
                    binding.lastNameEditText.setText(user.lastName)
                    binding.emailEditText.setText(user.email)
                    binding.wilaya.setText(user.wilaya)
                    binding.bloodGroup.setText(user.bloodGroup)
                    binding.userName.setText(user.lastName+" "+user.firstName)

                }
                private fun getImageFromFirebase() {
                    storage = FirebaseStorage.getInstance().reference.child("Users/"+ auth!!.currentUser!!.uid)
                    val localFile = File.createTempFile("tempImage","jpg")
                    storage.getFile(localFile).addOnSuccessListener{
                        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        binding.profileImage.setImageBitmap(bitmap)
                    }
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
            setUpTextFeilds()
            val bottomNavBar : BottomNavigationView? = activity?.findViewById(R.id.bottom_nav_view)
            if (bottomNavBar != null) {
                bottomNavBar.visibility=View.GONE
            }
        }
    }


    private fun setUpTextFeilds() {
        binding.firstNameEditText.isEnabled = false
        binding.lastNameEditText.isEnabled=false
        binding.bloodGroup.isEnabled=false
        binding.wilaya.isEnabled=false
        binding.emailEditText.isEnabled=false
    }
    override fun onDestroyView() {
        super.onDestroyView()
        logoutButton = activity?.findViewById(R.id.logoutButton)!!
        logoutButton.visibility = View.INVISIBLE

    }

}