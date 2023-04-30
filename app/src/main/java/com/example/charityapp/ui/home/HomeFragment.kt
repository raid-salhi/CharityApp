package com.example.charityapp.ui.home
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.charityapp.R
import com.example.charityapp.classes.Post
import com.example.charityapp.databinding.FragmentHomeBinding
import com.example.charityapp.ui.recyclerViews.PostClickHandler
import com.example.charityapp.ui.recyclerViews.PostRVAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class HomeFragment : Fragment() , PostClickHandler {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var postEmergencyRV : RecyclerView
    private lateinit var postProjectsRV : RecyclerView
    private lateinit var postDonationRV : RecyclerView
    private lateinit var adapter1 : PostRVAdapter
    private lateinit var adapter2 : PostRVAdapter
    private lateinit var adapter3 : PostRVAdapter
    private lateinit var eventImg : ImageView
    private lateinit var db : FirebaseFirestore
    private lateinit var postEmergencyList : ArrayList<Post>
    private lateinit var postProjectList : ArrayList<Post>
    private lateinit var postDonationList : ArrayList<Post>

    private val TAG = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        val toolbarTitle = activity?.findViewById<TextView>(R.id.toolbar_title)
//        toolbarTitle?.setText(R.string.title_home)
        db = Firebase.firestore
        setupEmergencyRV()
        setupProjectsRV()
        setupDonationRV()
        eventChangeListner()
        eventImg = binding.eventImg
        eventImg.setOnClickListener {
            Toast.makeText(context, "Coming Soon!", Toast.LENGTH_SHORT).show()
        }
        return root
    }

    private fun setupDonationRV() {
        postDonationList = arrayListOf()
        adapter1= PostRVAdapter(postDonationList,this)
        postDonationRV =binding.donateRV
        postDonationRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        postDonationRV.adapter = adapter1
    }

    private fun setupProjectsRV() {
        postProjectList = arrayListOf()
        adapter2= PostRVAdapter(postProjectList,this)
        postProjectsRV =binding.projectRV
        postProjectsRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        postProjectsRV.adapter = adapter2

    }

    private fun setupEmergencyRV() {
        postEmergencyList = arrayListOf()
        adapter3= PostRVAdapter(postEmergencyList,this)
        postEmergencyRV =binding.emergencyRV
        postEmergencyRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        postEmergencyRV.adapter = adapter3

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val bottomNavBar : BottomNavigationView? = activity?.findViewById(R.id.bottom_nav_view)
        if (bottomNavBar != null) {
            bottomNavBar.visibility=View.VISIBLE
        }
    }
    private fun eventChangeListner() {

        db.collection("Donation").get().addOnSuccessListener{ result ->
            for (document in result)
            {
               binding.animationView2.visibility = View.GONE
                postDonationList.add(document.toObject<Post>())
                adapter1.notifyDataSetChanged()
            }
        }.addOnFailureListener {
            Log.d(TAG, "Error getting documents: ", it)
        }
        db.collection("Emergency").get().addOnSuccessListener{ result ->
            for (document in result)
            {
                binding.animationView.visibility = View.GONE
                postEmergencyList.add(document.toObject<Post>())
                adapter3.notifyDataSetChanged()
            }
        }.addOnFailureListener {
            Log.d(TAG, "Error getting documents: ", it)
        }
        db.collection("Projects").get().addOnSuccessListener{ result ->
            for (document in result)
            {
                binding.animationView1.visibility = View.GONE
                postProjectList.add(document.toObject<Post>())
                adapter2.notifyDataSetChanged()
            }
        }.addOnFailureListener {
            Log.d(TAG, "Error getting documents: ", it)
        }

    }
    override fun clickedPostItem(post: Post) {
        Log.d(TAG, post.title)
        val bundle= bundleOf(
            "pid" to post.pid,
            "title" to post.title,
            "category" to post.category,
            "location" to post.location,
            "amountGoal" to post.amountGoal,
            "amountReached" to post.amountReached,
            "subCategory" to post.subCategory,
            "description" to post.description,
            "imagesNumber" to post.imagesNumber,
            "description" to post.description,
            "imagesNumber" to post.imagesNumber)

        findNavController().navigate(R.id.navigation_details,bundle)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}