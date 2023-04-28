package com.example.charityapp.ui.needhelp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charityapp.R

class NeedHelp2Fragment : Fragment() {

    companion object {
        fun newInstance() = NeedHelp2Fragment()
    }

    private lateinit var viewModel: NeedHelp2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.needhelp_layout_2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NeedHelp2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}