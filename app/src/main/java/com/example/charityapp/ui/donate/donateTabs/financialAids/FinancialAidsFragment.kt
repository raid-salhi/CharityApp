package com.example.charityapp.ui.donate.donateTabs.financialAids

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.charityapp.R
import com.example.charityapp.classes.Post
import com.example.charityapp.databinding.FragmentBloodDonationBinding
import com.example.charityapp.databinding.FragmentFinancialAidsBinding
import com.example.charityapp.ui.recyclerViews.PostRVAdapter

class FinancialAidsFragment() : Fragment() {


    private var _binding: FragmentFinancialAidsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FinancialAidsViewModel

    private lateinit var postRV : RecyclerView
    private lateinit var adapter : PostRVAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinancialAidsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val postList = ArrayList<Post>()
        postList.add(Post("Orphins Donations","Donation","Setif",100000,53000, R.drawable.outline_payments_24))
        postList.add(Post("Poor Family Donations","Donation","Bejaia",50000,23000, R.drawable.outline_payments_24))


        adapter= PostRVAdapter(postList)
        postRV =binding.financialAidsRV
        postRV.layoutManager =LinearLayoutManager(requireContext())
        postRV.adapter = adapter


        return root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FinancialAidsViewModel::class.java)



    }

}