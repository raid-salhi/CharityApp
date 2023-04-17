package com.example.charityapp.ui.paiment.amount

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentAmountBinding
import com.example.charityapp.databinding.FragmentDonateBinding

class amountFragment : Fragment() {

    companion object {
        fun newInstance() = amountFragment()
    }

    private var _binding: FragmentAmountBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AmountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAmountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_complete_paiment)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AmountViewModel::class.java)
        // TODO: Use the ViewModel
    }

}