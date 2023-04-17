package com.example.charityapp.ui.paiment.redirection

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charityapp.R

class RedirectionFragment : Fragment() {

    companion object {
        fun newInstance() = RedirectionFragment()
    }

    private lateinit var viewModel: RedirectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_redirection, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RedirectionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}