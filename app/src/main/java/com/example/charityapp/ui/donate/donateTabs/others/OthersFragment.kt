package com.example.charityapp.ui.donate.donateTabs.others

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
import com.example.charityapp.databinding.FragmentFinancialAidsBinding
import com.example.charityapp.databinding.FragmentOthersBinding
import com.example.charityapp.ui.recyclerViews.PostClickHandler
import com.example.charityapp.ui.recyclerViews.PostRVAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class OthersFragment : Fragment(),PostClickHandler {


    private var _binding: FragmentOthersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: OthersViewModel
    private val TAG = "OthersFragment"
    private lateinit var postRV : RecyclerView
    private lateinit var adapter : PostRVAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var postList : ArrayList<Post>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOthersBinding.inflate(inflater, container, false)
        val root: View = binding.root


//        postList.add(Post("Needing clothes","Donation","Setif",100000,53000,"Others"))
//        postList.add(Post("Needing funds to buy a baby's needs","Donation","Bejaia",50000,4500,"Others"))
//        postList.add(Post("Looking for i dont know","Donation","Adrar",1000,900,"Others"))

        db= Firebase.firestore
        postList = arrayListOf()
        adapter= PostRVAdapter(postList,this)
        postRV =binding.othersRV
        postRV.layoutManager = LinearLayoutManager(requireContext())
        postRV.setHasFixedSize(true)
        postRV.adapter = adapter
        eventChangeListner()
        return root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OthersViewModel::class.java)

    }
    override fun clickedPostItem(post: Post) {
        Log.d(TAG, post.title)
        val bundle= bundleOf("title" to post.title)
        findNavController().navigate(R.id.navigation_details,bundle)
    }
    private fun eventChangeListner() {

        db.collection("Donation").get().addOnSuccessListener{ result ->
            for (document in result)
            {
                binding.animationView.visibility = View.GONE
                if (document.get("subCategory").toString()=="Others")
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