package com.example.charityapp.ui.projects.volunteer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.charityapp.R
import com.example.charityapp.classes.Post
import com.example.charityapp.databinding.FragmentVolunteerBinding
import com.example.charityapp.ui.recyclerViews.PostClickHandler
import com.example.charityapp.ui.recyclerViews.PostRVAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class VolunteerFragment : Fragment(), PostClickHandler {

    companion object {
        fun newInstance() = VolunteerFragment()
    }

    private lateinit var viewModel: VolunteerViewModel
    private var _binding: FragmentVolunteerBinding? = null
    private val binding get() = _binding!!
    private lateinit var postProjectsRV : RecyclerView
    private lateinit var adapter : PostRVAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var postList : ArrayList<Post>
    private val TAG = "VolunteerFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVolunteerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = Firebase.firestore
        postProjectsRV =binding.projectsRV
        postProjectsRV.layoutManager = LinearLayoutManager(requireContext())
        postProjectsRV.setHasFixedSize(true)
        postList = arrayListOf()
        adapter= PostRVAdapter(postList,this)
        postProjectsRV.adapter = adapter
        eventChangeListner()
        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VolunteerViewModel::class.java)
        // TODO: Use the ViewModel
    }
    private fun eventChangeListner() {

        db.collection("Projects").get().addOnSuccessListener{ result ->
            for (document in result)
            {
                binding.animationView.visibility = View.GONE
                postList.add(document.toObject<Post>())
                adapter.notifyDataSetChanged()
            }
        }.addOnFailureListener {
            Log.d(TAG, "Error getting documents: ", it)
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