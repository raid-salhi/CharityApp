package com.example.charityapp.ui.donate.donateTabs.financialAids

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charityapp.R

class FinancialAidsFragment : Fragment() {

    companion object {
        fun newInstance() = FinancialAidsFragment()
    }

    private lateinit var viewModel: FinancialAidsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_financial_aids, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FinancialAidsViewModel::class.java)
        // TODO: Use the ViewModel

    }

}