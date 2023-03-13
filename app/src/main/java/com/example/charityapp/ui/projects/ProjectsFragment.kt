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
import com.example.charityapp.ui.projects.projectDonation.ProjectDonationFragment
import com.example.charityapp.ui.projects.volunteer.VolunteerFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class ProjectsFragment : Fragment() {
    private var _binding: FragmentProjectsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        val adapter = ViewPagerProjectsAdapter(childFragmentManager)
        pager= binding.pager
        tab =binding.tabs

        adapter.addFragment(ProjectDonationFragment(),"Project Donation")
        adapter.addFragment(VolunteerFragment(),"Volunteer")

        pager.adapter=adapter
        tab.setupWithViewPager(pager)
        return root
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