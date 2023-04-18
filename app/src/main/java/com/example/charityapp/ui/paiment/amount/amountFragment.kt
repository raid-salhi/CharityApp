package com.example.charityapp.ui.paiment.amount

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentAmountBinding
import com.example.charityapp.ui.details.*

private const val ARG_TITLE ="title"
private const val ARG_PID="pid"
private const val ARG_AMOUNT_REACHED ="amountReached"
private const val ARG_AMOUNT_GOAL ="amountGoal"
private const val ARG_CATEGORY ="category"

class amountFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var param3: Int? = null
    private var param4: Int? = null
    private var param5: String? = null
    companion object {
        fun newInstance(params1: String,
                        params2: String,
                        params3: Int,
                        params4: Int,
                        params5: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE,params1)
                    putString(ARG_PID,params2)
                    putInt(ARG_AMOUNT_REACHED,params3)
                    putInt(ARG_AMOUNT_GOAL,params4)
                    putString(ARG_CATEGORY,params5)
                }

            }
    }


    private var _binding: FragmentAmountBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AmountViewModel
    private var amountDonated: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_TITLE)
            param2 = it.getString(ARG_PID)
            param3 = it.getInt(ARG_AMOUNT_REACHED)
            param4 = it.getInt(ARG_AMOUNT_GOAL)
            param5 = it.getString(ARG_CATEGORY)


        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAmountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.nextButton.setOnClickListener {
            if (binding.amountEditText.text?.isEmpty() == false){
                amountDonated = binding.amountEditText.text.toString().toInt()
                param3 = param3?.plus(amountDonated)
                val bundle = bundleOf(
                    "pid" to param2,
                    "category" to param5,
                    "amountDonated" to amountDonated,
                    "amountReached" to param3
                )
            findNavController().navigate(R.id.navigation_complete_paiment,bundle)
            }
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AmountViewModel::class.java)
        // TODO: Use the ViewModel
    }

}