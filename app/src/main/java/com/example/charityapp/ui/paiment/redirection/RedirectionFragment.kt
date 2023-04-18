package com.example.charityapp.ui.paiment.redirection

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentRedirectionBinding
import com.example.charityapp.ui.details.DetailsFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val ARG_PID="pid"
private const val ARG_AMOUNT_REACHED ="amountReached"
private const val ARG_AMOUNT_DONATED ="amountDonated"
class RedirectionFragment : Fragment() {
    private var param1: String? = null
    private var param3: Int? = null
    private var param4: Int? = null
    companion object {
        fun newInstance(params1: String,
                        params3: Int,
                        params4: Int, ) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PID,params1)
                    putInt(ARG_AMOUNT_REACHED,params3)
                    putInt(ARG_AMOUNT_DONATED,params4)
                }

            }
    }
    private var _binding: FragmentRedirectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RedirectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            param1 = it.getString(ARG_PID)
            param3 = it.getInt(ARG_AMOUNT_REACHED)
            param4 = it.getInt(ARG_AMOUNT_DONATED)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRedirectionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.amount.text = param4.toString()
        binding.payButton.setOnClickListener {

            val db = Firebase.firestore
            val postRef = db.collection("Donation").document(param1.toString())

            postRef
                .update("amountReached", param3!!)
                .addOnSuccessListener {
                    Toast.makeText(activity, "DocumentSnapshot successfully updated!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.navigation_donate)
                }
                .addOnFailureListener { Toast.makeText(activity, "Error updating document", Toast.LENGTH_SHORT).show()}
        }


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RedirectionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}