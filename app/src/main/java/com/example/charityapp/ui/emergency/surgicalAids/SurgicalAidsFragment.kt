package com.example.charityapp.ui.emergency.surgicalAids

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
import com.example.charityapp.databinding.FragmentSurgicalAidsBinding
import com.example.charityapp.ui.recyclerViews.PostClickHandler
import com.example.charityapp.ui.recyclerViews.PostRVAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class SurgicalAidsFragment : Fragment() , PostClickHandler {

    companion object {
        fun newInstance() = SurgicalAidsFragment()
    }

    private var _binding: FragmentSurgicalAidsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SurgicalAidsViewModel
    private val TAG = "SurgicalAidsFragment"
    private lateinit var postRV : RecyclerView
    private lateinit var adapter : PostRVAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var postList : ArrayList<Post>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSurgicalAidsBinding.inflate(inflater, container, false)
         postList = arrayListOf()
//        postList.add(Post("Needs AB+ bloodtype","Emergency","Setif",5,2,"Surgical Aids"))
//        postList.add(Post("Needing B- as soon as possible","Emergency","Bejaia",2,0,"Surgical Aids"))
        db = Firebase.firestore
        adapter= PostRVAdapter(postList,this)
        postRV =binding.surgicalAidsRV
        postRV.layoutManager = LinearLayoutManager(requireContext())
        postRV.setHasFixedSize(true)
        postRV.adapter = adapter
        eventChangeListner()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SurgicalAidsViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun clickedPostItem(post: Post) {
        Log.d(TAG, post.title)
        val bundle= bundleOf(
            "title" to post.title,
            "category" to post.category,
            "location" to post.location,
            "amountGoal" to post.amountGoal,
            "amountReached" to post.amountReached,
            "subCategory" to post.subCategory,
            "description" to post.description,
            "contact" to post.contact,
            "imagesNumber" to post.imagesNumber)
        findNavController().navigate(R.id.navigation_details, bundle)
    }
    private fun eventChangeListner() {

        db.collection("Emergency").get().addOnSuccessListener{ result ->
            for (document in result)
            {
                binding.animationView.visibility = View.GONE
                if (document.get("subCategory").toString()=="Surgical Aids")
                {
                    postList.add(document.toObject<Post>())
                    adapter.notifyDataSetChanged()
                }
            }
        }.addOnFailureListener {
            Log.d(TAG, "Error getting documents: ", it)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}