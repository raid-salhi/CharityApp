package com.example.charityapp.ui.needhelp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentDonateBinding
import com.example.charityapp.databinding.FragmentNeedHelpBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class NeedHelpFragment : Fragment() {


    private lateinit var viewModel: NeedHelpViewModel
    private var _binding: FragmentNeedHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNeedHelpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val bottomNavBar : BottomNavigationView? = activity?.findViewById(R.id.bottom_nav_view)
        if (bottomNavBar != null) {
            bottomNavBar.visibility=View.GONE
        }

        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.needHelp2Fragment)
        }
        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NeedHelpViewModel::class.java)
        // TODO: Use the ViewModel
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