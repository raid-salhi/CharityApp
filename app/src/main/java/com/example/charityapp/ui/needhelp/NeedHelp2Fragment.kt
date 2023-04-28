package com.example.charityapp.ui.needhelp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.charityapp.R
import com.example.charityapp.databinding.NeedhelpLayout2Binding
import com.google.android.material.bottomnavigation.BottomNavigationView

class NeedHelp2Fragment : Fragment() {


    private lateinit var viewModel: NeedHelp2ViewModel
    private lateinit var radioGroup: RadioGroup
    private lateinit var selectedCategory: String
    private var selectedId:Int = 0
    private var _binding: NeedhelpLayout2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NeedhelpLayout2Binding.inflate(inflater, container, false)

        val bottomNavBar : BottomNavigationView? = activity?.findViewById(R.id.bottom_nav_view)
        if (bottomNavBar != null) {
            bottomNavBar.visibility=View.GONE
        }


        radioGroup = binding.categoryRadioGroup
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Get the selected Radio Button
            val radioButton = group
                .findViewById(checkedId) as RadioButton

            selectedId = radioButton.id
            selectedCategory = radioButton.text as String
        }
        binding.next2.setOnClickListener {
            if (selectedId!=0){
                val bundle = bundleOf("category" to selectedCategory)
                findNavController().navigate(R.id.needHelp3Fragment,bundle)

            }else
                Toast.makeText(requireContext(), "Select an option please", Toast.LENGTH_SHORT).show()

        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NeedHelp2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}