package com.example.adminpanel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.classes.Post
import com.example.adminpanel.classes.PostRVAdapter
import com.example.adminpanel.databinding.FragmentDonateBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class donateFragment : Fragment() ,PostClickHandler {
    private var _binding: FragmentDonateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var postRV : RecyclerView
    private lateinit var adapter : PostRVAdapter
    private lateinit var postList : ArrayList<Post>
    private lateinit var db : FirebaseFirestore
    private val TAG = "AllDonationFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDonateBinding.inflate(inflater, container, false)
        val root: View = binding.root
        postList = arrayListOf()
        db = Firebase.firestore
        adapter= PostRVAdapter(postList,this)
        postRV =binding.allDonationRV
        postRV.setHasFixedSize(true)
        postRV.layoutManager = LinearLayoutManager(requireContext())
        postRV.adapter = adapter
        eventChangeListner()


        return  root
    }

    private fun eventChangeListner() {

        db.collection("Donation").get().addOnSuccessListener{ result ->
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
            "contact" to post.contact,
            "imagesNumber" to post.imagesNumber)



//        findNavController().navigate(R.id.navigation_details,bundle)
    }

    override fun deletePostItem(post: Post) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}