package com.example.charityapp.ui.emergency.medecine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charityapp.R

class MedecineFragment : Fragment() {

    companion object {
        fun newInstance() = MedecineFragment()
    }

    private lateinit var viewModel: MedecineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_medecine, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MedecineViewModel::class.java)
        // TODO: Use the ViewModel
    }

}