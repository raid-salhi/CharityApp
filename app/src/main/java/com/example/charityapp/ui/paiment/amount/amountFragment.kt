package com.example.charityapp.ui.paiment.amount

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charityapp.R

class amountFragment : Fragment() {

    companion object {
        fun newInstance() = amountFragment()
    }

    private lateinit var viewModel: AmountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_amount, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AmountViewModel::class.java)
        // TODO: Use the ViewModel
    }

}