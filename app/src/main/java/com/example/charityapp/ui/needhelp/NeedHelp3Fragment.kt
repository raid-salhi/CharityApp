package com.example.charityapp.ui.needhelp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.charityapp.R
import com.example.charityapp.databinding.NeedhelpLayout3Binding
import com.example.charityapp.ui.details.*
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val ARG_CATEGORY ="category"
class NeedHelp3Fragment : Fragment() {
    private var param1 : String? = null
    companion object {
        fun newInstance(params1: String)=
            NeedHelp3Fragment().apply {
            arguments=Bundle().apply {
                putString(ARG_CATEGORY,params1)

            }
        }
    }

    private lateinit var viewModel: NeedHelp3ViewModel
    private var _binding: NeedhelpLayout3Binding? = null
    private lateinit var title : String
    private var phone : Int = 0
    private lateinit var location : String
    private lateinit var description : String
    private val binding get() = _binding!!
    private lateinit var uriList : ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NeedhelpLayout3Binding.inflate(inflater, container, false)

        uriList=ArrayList()
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->

            for(uri in uris)
                uriList.add(uri.toString())
            Toast.makeText(requireContext(), "You have selected "+uriList.size.toString()+ " pictures", Toast.LENGTH_SHORT).show()
        }
        binding.buttonUploadPics.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.nextBtn.setOnClickListener {
            if (binding.titleEditText.text!!.isNotEmpty() && binding.descriptionEditText.text!!.isNotEmpty() && binding.phoneEditText.text!!.isNotEmpty()){
                title = binding.titleEditText.text.toString()
                description = binding.descriptionEditText.text.toString()
                location = binding.wilaya.text.toString()
                phone = binding.phoneEditText.text.toString().toInt()
                val bundle = bundleOf(
                    "title" to title,
                    "description" to description,
                    "contact" to phone,
                    "location" to location,
                    "listUri" to uriList,
                    "subCategory" to param1)

                findNavController().navigate(R.id.needHelp4Fragment,bundle)
            }else
                Toast.makeText(requireContext(), "Fill all the cases please !", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NeedHelp3ViewModel::class.java)
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
//    override fun onDestroyView() {
//        super.onDestroyView()
//        val bottomNavBar : BottomNavigationView? = activity?.findViewById(R.id.bottom_nav_view)
//        if (bottomNavBar != null) {
//            bottomNavBar.visibility=View.VISIBLE
//        }
//        _binding = null
//    }

}