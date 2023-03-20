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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedecineBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val postList = ArrayList<Post>()
        postList.add(Post("Needs Tramadol","Emergency","Setif",5,2))
        postList.add(Post("Needing Dolipran","Emergency","Bejaia",2,0))


        adapter= PostRVAdapter(postList,this)
        postRV =binding.medicineRV
        postRV.layoutManager = LinearLayoutManager(requireContext())
        postRV.adapter = adapter

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MedecineViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun clickedPostItem(post: Post) {
        Log.d(TAG, post.title)
        val bundle= bundleOf("title" to post.title)
        findNavController().navigate(R.id.navigation_details,bundle)
    }

}