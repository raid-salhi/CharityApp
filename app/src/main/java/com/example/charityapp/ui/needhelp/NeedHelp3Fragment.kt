package com.example.charityapp.ui.needhelp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.charityapp.R
import com.example.charityapp.databinding.NeedhelpLayout2Binding
import com.example.charityapp.databinding.NeedhelpLayout3Binding
import com.example.charityapp.ui.details.*
import com.google.android.material.bottomnavigation.BottomNavigationView
private const val ARG_CATEGORY ="category"
class NeedHelp3Fragment : Fragment() {
    private var param1 : String? = null
    companion object {
        fun newInstance(params1: String)=
            NeedHelp3Fragment().apply {
            arguments=Bundle().apply {
                putString(ARG_CATEGORY,params1)
            }
        }
    }

    private lateinit var viewModel: NeedHelp3ViewModel
    private var _binding: NeedhelpLayout3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NeedhelpLayout3Binding.inflate(inflater, container, false)



        Toast.makeText(requireContext(), param1.toString(), Toast.LENGTH_SHORT).show()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NeedHelp3ViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_CATEGORY)
        }
        val bottomNavBar : BottomNavigationView? = activity?.findViewById(R.id.bottom_nav_view)
        if (bottomNavBar != null) {
            bottomNavBar.visibility=View.GONE
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNavBar : BottomNavigationView? = activity?.findViewById(R.id.bottom_nav_view)
        if (bottomNavBar != null) {
            bottomNavBar.visibility=View.VISIBLE
        }
        _binding = null
    }

}