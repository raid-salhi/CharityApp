package com.example.charityapp.ui.emergency.surgicalAids

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charityapp.R

class SurgicalAidsFragment : Fragment() {

    companion object {
        fun newInstance() = SurgicalAidsFragment()
    }

    private lateinit var viewModel: SurgicalAidsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_surgical_aids, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SurgicalAidsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}