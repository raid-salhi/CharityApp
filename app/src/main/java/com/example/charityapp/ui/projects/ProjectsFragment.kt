package com.example.charityapp.ui.projects

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.charityapp.R
import com.example.charityapp.classes.Post
import com.example.charityapp.databinding.FragmentProjectsBinding
import com.example.charityapp.ui.recyclerViews.PostProjectsRVAdapter
import com.example.charityapp.ui.recyclerViews.PostRVAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class ProjectsFragment : Fragment() {
    private var _binding: FragmentProjectsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProjectsViewModel
    private lateinit var postProjectsRV : RecyclerView
    private lateinit var adapter : PostProjectsRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectsBinding.inflate(inflater, container, false)
        val postList = ArrayList<Post>()
        postList.add(Post("Building a mosquee","Projects","Setif",100000,53000, R.drawable.outline_construction_24))
        postList.add(Post("Building a well","Projects","Bejaia",50000,23000, R.drawable.outline_construction_24))
        postList.add(Post("Building a fountain","Projects","Alger",30000,29000, R.drawable.outline_construction_24))
        postList.add(Post("Building a house for poor family","Projects","Setif",40000,13000, R.drawable.outline_construction_24))


        adapter= PostProjectsRVAdapter(postList)
        postProjectsRV =binding.projectsRV
        postProjectsRV.layoutManager = LinearLayoutManager(requireContext())
        postProjectsRV.adapter = adapter


        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProjectsViewModel::class.java]
        // TODO: Use the ViewModel
        lifecycleScope.launch {
            val toolbarTitle = activity?.findViewById<TextView>(R.id.toolbar_title)
            toolbarTitle?.setText(R.string.title_projects)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}