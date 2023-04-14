package com.example.charityapp.ui.details.description

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentDescriptionBinding
import com.example.charityapp.databinding.FragmentDetailsBinding
import com.example.charityapp.ui.details.*
import com.example.charityapp.ui.donate.DonateViewModel
private const val ARG_TITLE ="title"
private const val ARG_DESCRIPTION ="description"
class DescriptionFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    companion object {
        fun newInstance(params1:String,
                        params2: String) =
            DescriptionFragment().apply {
                arguments=Bundle().apply {
                    putString(ARG_DESCRIPTION,params2)
                    putString(ARG_TITLE,params1)
                }
         }
    }


    private lateinit var viewModel: DescriptionViewModel
    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            param1=it.getString(ARG_TITLE)
            param2= it.getString(ARG_DESCRIPTION)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)

        binding.title.text=param1
        binding.descriptionContent.text=param2

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DescriptionViewModel::class.java)


    }

}