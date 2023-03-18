package com.example.charityapp.ui.projects

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.charityapp.MainActivity
import com.example.charityapp.R
import com.example.charityapp.classes.Post
import com.example.charityapp.databinding.FragmentProjectsBinding
import com.example.charityapp.ui.recyclerViews.PostClickHandler
import com.example.charityapp.ui.recyclerViews.PostProjectsRVAdapter
import com.example.charityapp.ui.recyclerViews.PostRVAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlin.math.log

class ProjectsFragment : Fragment() , PostClickHandler {
    private val TAG = "ProjectsFragment"
    private var _binding: FragmentProjectsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProjectsViewModel
    private lateinit var postProjectsRV : RecyclerView
    private lateinit var adapter : PostProjectsRVAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var postList : ArrayList<Post>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectsBinding.inflate(inflater, container, false)
        db = Firebase.firestore
        postProjectsRV =binding.projectsRV
        postProjectsRV.layoutManager = LinearLayoutManager(requireContext())
        postProjectsRV.setHasFixedSize(true)
        postList = arrayListOf()
//        postList.add(Post("Building a mosquee","Projects","Setif",100000,53000 ))
//        postList.add(Post("Building a well","Projects","Bejaia",50000,23000 ))
//        postList.add(Post("Building a fountain","Projects","Alger",30000,29000))
//        postList.add(Post("Building a house for poor family","Projects","Setif",40000,13000))


        adapter= PostProjectsRVAdapter(postList,this)
        postProjectsRV.adapter = adapter
        eventChangeListner()




        return binding.root
    }

    private fun eventChangeListner() {
//        db.collection("Projects").addSnapshotListener(object : EventListener<QuerySnapshot> {
//            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
//                if (error != null) {
//                    Log.e("firestore Error", error.message.toString())
//                    return
//                }
//                for (dc : DocumentChange in value?.documentChanges!!){
//                    if (dc.type == DocumentChange.Type.ADDED){
//                        postList.add(Post(dc.document.get("title").toString(),
//                            dc.document.get("category").toString(),
//                            dc.document.get("location").toString(),
//                            15000,12000))
////                        toObject(Post::class.java
//                    }
//                }
//
//
//            })
        db.collection("Projects").get().addOnSuccessListener{ result ->
                    for (document in result)
                    {
//                        postList.add(Post(document.get("title").toString(),document.get("category").toString(),document.get("location").toString(),15000,12000))
                        postList.add(document.toObject<Post>())
                        adapter.notifyDataSetChanged()
                    }
            }.addOnFailureListener {
            Log.d(TAG, "Error getting documents: ", it)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProjectsViewModel::class.java]
        // TODO: Use the ViewModel
        lifecycleScope.launch {
            val toolbarTitle = activity?.findViewById<TextView>(R.id.toolbar_title)
            toolbarTitle?.setText(R.string.title_projects)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickedPostItem(post: Post) {
        Log.d(TAG, post.title)
        val bundle= bundleOf("title" to post.title)
        findNavController().navigate(R.id.navigation_details,bundle)
    }

}