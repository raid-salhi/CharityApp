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
private const val ARG_CATEGORY ="category"
private const val ARG_AMOUNTREACHED ="amountReached"
private const val ARG_AMOUNTGOAL ="amountGoal"
private const val ARG_SUBCATEGORY ="subCategory"
private const val ARG_LOCATION ="location"


class DetailsFragment : Fragment() {

    private var param1 : String? = null
    private var param2: String? = null
    private var param3: Int? = null
    private var param4: Int? = null
    private var param5: String? = null
    private var param6: String? = null

    companion object {
        fun newInstance(params1: String,
                        params2: String,
                        params3: Int,
                        params4: Int,
                        params5: String,
                        params6: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE,params1)
                    putString(ARG_CATEGORY,params2)
                    putInt(ARG_AMOUNTREACHED,params3)
                    putInt(ARG_AMOUNTGOAL,params4)
                    putString(ARG_SUBCATEGORY,params5)
                    putString(ARG_LOCATION,params6)
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
            param2 = it.getString(ARG_CATEGORY)
            param3 = it.getInt(ARG_AMOUNTREACHED)
            param4 = it.getInt(ARG_AMOUNTGOAL)
            param5 = it.getString(ARG_SUBCATEGORY)
            param6 = it.getString(ARG_LOCATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        textView = binding.detailsTitle
        textView.text = param3.toString()
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }




}