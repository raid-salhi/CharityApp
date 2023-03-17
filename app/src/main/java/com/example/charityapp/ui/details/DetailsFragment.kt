package com.example.charityapp.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentDetailsBinding
import com.example.charityapp.databinding.FragmentProjectsBinding

private const val ARG_TITLE ="title"

class DetailsFragment : Fragment() {

    private var param1 : String? = null

    companion object {
        fun newInstance(params1: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE,params1)
                }

        }
    }
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailsViewModel
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_TITLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        textView = binding.detailsTitle
        textView.text = param1
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }




}