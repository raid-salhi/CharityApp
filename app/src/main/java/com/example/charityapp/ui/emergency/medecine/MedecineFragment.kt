package com.example.charityapp.ui.emergency.medecine

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
import com.example.charityapp.databinding.FragmentBloodDonationBinding
import com.example.charityapp.databinding.FragmentMedecineBinding
import com.example.charityapp.ui.emergency.bloodDonation.BloodDonationViewModel
import com.example.charityapp.ui.recyclerViews.PostClickHandler
import com.example.charityapp.ui.recyclerViews.PostRVAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MedecineFragment : Fragment(), PostClickHandler {

    companion object {
        fun newInstance() = MedecineFragment()
    }

    private var _binding: FragmentMedecineBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MedecineViewModel
    private val TAG = "MedecineFragment"
    private lateinit var postRV : RecyclerView
    private lateinit var adapter : PostRVAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var postList : ArrayList<Post>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedecineBinding.inflate(inflater, container, false)
        val root: View = binding.root

        postList = arrayListOf()
//        postList.add(Post("Needs Tramadol","Emergency","Setif",5,2,"Medicine"))
//        postList.add(Post("Needing Dolipran","Emergency","Bejaia",2,0,"Medicine"))

        db = Firebase.firestore
        adapter= PostRVAdapter(postList,this)
        postRV =binding.medicineRV
        postRV.layoutManager = LinearLayoutManager(requireContext())
        postRV.setHasFixedSize(true)
        postRV.adapter = adapter
        eventChangeListner()

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MedecineViewModel::class.java)
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
            "imagesNumber" to post.imagesNumber)
        findNavController().navigate(R.id.navigation_details,bundle)
    }
    private fun eventChangeListner() {

        db.collection("Emergency").get().addOnSuccessListener{ result ->
            for (document in result)
            {
                binding.animationView.visibility = View.GONE
                if (document.get("subCategory").toString()=="Medicine")
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