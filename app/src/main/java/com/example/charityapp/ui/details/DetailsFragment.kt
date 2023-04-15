package com.example.charityapp.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.example.charityapp.databinding.FragmentDetailsBinding
import com.example.charityapp.ui.details.description.DescriptionFragment
import com.example.charityapp.ui.details.pictures.PicturesFragment
import com.example.charityapp.ui.donate.DonateViewModel
import com.google.android.material.tabs.TabLayout

private const val ARG_TITLE ="title"
private const val ARG_CATEGORY ="category"
private const val ARG_AMOUNT_REACHED ="amountReached"
private const val ARG_AMOUNT_GOAL ="amountGoal"
private const val ARG_SUBCATEGORY ="subCategory"
private const val ARG_LOCATION ="location"
private const val ARG_IMAGES_NUMBER="imagesNumber"



class DetailsFragment : Fragment() {

    private var param1 : String? = null
    private var param2: String? = null
    private var param3: Int? = null
    private var param4: Int? = null
    private var param5: String? = null
    private var param6: String? = null
    private var param7: Int? = null


    companion object {
        fun newInstance(params1: String,
                        params2: String,
                        params3: Int,
                        params4: Int,
                        params5: String,
                        params6: String,
                        params7: Int) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE,params1)
                    putString(ARG_CATEGORY,params2)
                    putInt(ARG_AMOUNT_REACHED,params3)
                    putInt(ARG_AMOUNT_GOAL,params4)
                    putString(ARG_SUBCATEGORY,params5)
                    putString(ARG_LOCATION,params6)
                    putInt(ARG_IMAGES_NUMBER,params7)
                }

        }
    }
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailsViewModel
    private lateinit var pager: ViewPager
    private lateinit var tab: TabLayout
    private lateinit var amountReachedTV : TextView
    private lateinit var amountGoalTV : TextView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_TITLE)
            param2 = it.getString(ARG_CATEGORY)
            param3 = it.getInt(ARG_AMOUNT_REACHED)
            param4 = it.getInt(ARG_AMOUNT_GOAL)
            param5 = it.getString(ARG_SUBCATEGORY)
            param6 = it.getString(ARG_LOCATION)
            param7 = it.getInt(ARG_IMAGES_NUMBER)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val bundle = requireArguments()
        val adapter = ViewPagerDetailsAdapter(childFragmentManager,bundle)


        bindingViews()
        setFragmentsToViewPager(adapter)

        pager.adapter=adapter
        tab.setupWithViewPager(pager)

        amountReachedTV.setText("$param3 DA")
        amountGoalTV.setText("$param4 DA")

        progressBar.max=param4!!
        progressBar.progress= param3!!

        return binding.root

    }

    private fun setFragmentsToViewPager(adapter: ViewPagerDetailsAdapter) {
        if (param7!! >= 1){
            adapter.addFragment(DescriptionFragment(),"Description")
            adapter.addFragment(PicturesFragment(),"Pictures")
        }else
            adapter.addFragment(DescriptionFragment(),"Description")

    }

    private fun bindingViews() {
        pager= binding.viewPager
        tab =binding.tabLayout
        amountReachedTV = binding.amountReached
        amountGoalTV = binding.amountGoal
        progressBar = binding.progressBar2
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

    }




}