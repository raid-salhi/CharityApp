package com.example.charityapp.ui.projects.projectDonation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charityapp.R

class ProjectDonationFragment : Fragment() {

    companion object {
        fun newInstance() = ProjectDonationFragment()
    }

    private lateinit var viewModel: ProjectDonationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project_donation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProjectDonationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}