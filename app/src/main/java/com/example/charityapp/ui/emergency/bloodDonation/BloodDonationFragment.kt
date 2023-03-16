package com.example.charityapp.ui.emergency.bloodDonation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentBloodDonationBinding
import com.example.charityapp.databinding.FragmentDonateBinding

class BloodDonationFragment : Fragment() {

    private var _binding: FragmentBloodDonationBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BloodDonationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBloodDonationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BloodDonationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}