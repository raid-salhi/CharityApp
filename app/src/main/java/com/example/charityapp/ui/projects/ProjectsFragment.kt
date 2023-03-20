package com.example.charityapp.ui.projects

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentProjectsBinding
import com.example.charityapp.databinding.FragmentVolunteerBinding
import com.example.charityapp.ui.emergency.ViewPagerEmergencyAdapter
import com.example.charityapp.ui.projects.allProjects.AllProjectsFragment
import com.example.charityapp.ui.projects.projectDonation.ProjectDonationFragment
import com.example.charityapp.ui.projects.volunteer.VolunteerFragment

import com.google.android.material.tabs.TabLayout

import kotlinx.coroutines.launch

class ProjectsFragment : Fragment()  {
    private val TAG = "ProjectsFragment"
    private var _binding: FragmentProjectsBinding? = null
    private val binding get() = _binding!!
    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tab: TabLayout
    private lateinit var viewModel: ProjectsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectsBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        postList.add(Post("Building a mosquee","Projects","Setif",100000,53000 ))
//        postList.add(Post("Building a well","Projects","Bejaia",50000,23000 ))
//        postList.add(Post("Building a fountain","Projects","Alger",30000,29000))
//        postList.add(Post("Building a house for poor family","Projects","Setif",40000,13000))

        val adapter = ViewPagerEmergencyAdapter(childFragmentManager)
        pager= binding.pager
        tab =binding.tabs

        adapter.addFragment(AllProjectsFragment(),"All")
        adapter.addFragment(VolunteerFragment(),"Volunteer")
        adapter.addFragment(ProjectDonationFragment(),"Donate")

        pager.adapter=adapter
        tab.setupWithViewPager(pager)


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