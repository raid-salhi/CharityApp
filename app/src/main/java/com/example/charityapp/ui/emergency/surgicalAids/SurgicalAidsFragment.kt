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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSurgicalAidsBinding.inflate(inflater, container, false)
        val postList = ArrayList<Post>()
        postList.add(Post("Needs AB+ bloodtype","Emergency","Setif",5,2))
        postList.add(Post("Needing B- as soon as possible","Emergency","Bejaia",2,0))


        adapter= PostRVAdapter(postList,this)
        postRV =binding.surgicalAidsRV
        postRV.layoutManager = LinearLayoutManager(requireContext())
        postRV.adapter = adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SurgicalAidsViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun clickedPostItem(post: Post) {
        Log.d(TAG, post.title)
        val bundle = bundleOf("title" to post.title)
        findNavController().navigate(R.id.navigation_details, bundle)
    }

}