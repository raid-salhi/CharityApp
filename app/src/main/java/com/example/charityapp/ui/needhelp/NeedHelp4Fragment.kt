package com.example.charityapp.ui.needhelp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.charityapp.R
import com.example.charityapp.databinding.NeedhelpLayout3Binding
import com.example.charityapp.databinding.NeedhelpLayout4Binding
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val ARG_CATEGORY ="category"
class NeedHelp4Fragment : Fragment() {

    private var param1 : String? = null
    companion object {
        fun newInstance(params1: String)=
            NeedHelp4Fragment().apply {
                arguments=Bundle().apply {
                    putString(ARG_CATEGORY,params1)
                }
            }
    }

    private lateinit var viewModel: NeedHelp4ViewModel
    private var _binding: NeedhelpLayout4Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NeedhelpLayout4Binding.inflate(inflater, container, false)
        Toast.makeText(requireContext(), param1.toString(), Toast.LENGTH_SHORT).show()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NeedHelp4ViewModel::class.java)
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