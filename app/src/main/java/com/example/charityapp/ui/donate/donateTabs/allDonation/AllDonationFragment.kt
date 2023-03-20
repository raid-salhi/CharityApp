package com.example.charityapp.ui.donate.donateTabs.allDonation

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
import com.example.charityapp.databinding.FragmentAllDonationBinding
import com.example.charityapp.databinding.FragmentFinancialAidsBinding
import com.example.charityapp.ui.donate.donateTabs.financialAids.FinancialAidsViewModel
import com.example.charityapp.ui.recyclerViews.PostClickHandler
import com.example.charityapp.ui.recyclerViews.PostRVAdapter

class AllDonationFragment : Fragment(), PostClickHandler {

    companion object {
        fun newInstance() = AllDonationFragment()
    }

    private var _binding: FragmentAllDonationBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AllDonationViewModel
    private val TAG = "AllDonationFragment"
    private lateinit var postRV : RecyclerView
    private lateinit var adapter : PostRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllDonationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val postList = ArrayList<Post>()
        postList.add(Post("Orphins Donations","Donation","Setif",100000,53000))
        postList.add(Post("Poor Family Donations","Donation","Bejaia",50000,23000))
        postList.add(Post("Needing clothes","Donation","Setif",100000,53000))
        postList.add(Post("Needing funds to buy a baby's needs","Donation","Bejaia",50000,4500))
        postList.add(Post("Looking for i dont know","Donation","Adrar",1000,900))


        adapter= PostRVAdapter(postList,this)
        postRV =binding.allDonationRV
        postRV.layoutManager = LinearLayoutManager(requireContext())
        postRV.adapter = adapter
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AllDonationViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun clickedPostItem(post: Post) {
        Log.d(TAG, post.title)
        val bundle= bundleOf("title" to post.title)
        findNavController().navigate(R.id.navigation_details,bundle)
    }

}