package com.example.charityapp.ui.emergency.bloodDonation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charityapp.R

class BloodDonationFragment : Fragment() {

    companion object {
        fun newInstance() = BloodDonationFragment()
    }

    private lateinit var viewModel: BloodDonationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blood_donation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BloodDonationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}