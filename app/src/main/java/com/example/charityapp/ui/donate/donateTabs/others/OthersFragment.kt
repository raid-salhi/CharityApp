package com.example.charityapp.ui.donate.donateTabs.others

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
import com.example.charityapp.databinding.FragmentFinancialAidsBinding
import com.example.charityapp.databinding.FragmentOthersBinding
import com.example.charityapp.ui.recyclerViews.PostRVAdapter

class OthersFragment : Fragment() {


    private var _binding: FragmentOthersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: OthersViewModel
    private lateinit var postRV : RecyclerView
    private lateinit var adapter : PostRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOthersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val postList = ArrayList<Post>()

        postList.add(Post("Needing clothes","Donation","Setif",100000,53000))
        postList.add(Post("Needing funds to buy a baby's needs","Donation","Bejaia",50000,4500))
        postList.add(Post("Looking for i dont know","Donation","Adrar",1000,900))


        adapter= PostRVAdapter(postList)
        postRV =binding.othersRV
        postRV.layoutManager = LinearLayoutManager(requireContext())
        postRV.adapter = adapter

        return root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OthersViewModel::class.java)

    }

}