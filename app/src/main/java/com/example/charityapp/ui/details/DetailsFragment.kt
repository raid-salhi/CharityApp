package com.example.charityapp.ui.details

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentDetailsBinding
import com.example.charityapp.ui.details.description.DescriptionFragment
import com.example.charityapp.ui.details.pictures.PicturesFragment
import com.example.charityapp.ui.donate.DonateViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

private const val ARG_TITLE ="title"
private const val ARG_CATEGORY ="category"
private const val ARG_AMOUNT_REACHED ="amountReached"
private const val ARG_AMOUNT_GOAL ="amountGoal"
private const val ARG_SUBCATEGORY ="subCategory"
private const val ARG_LOCATION ="location"
private const val ARG_CONTACT ="contact"
private const val ARG_IMAGES_NUMBER="imagesNumber"
private const val ARG_PID="pid"
private const val MY_PERMISSIONS_REQUEST_CALL_PHONE = 1



class DetailsFragment : Fragment() {

    private var param1 : String? = null
    private var param2: String? = null
    private var param3: Int? = null
    private var param4: Int? = null
    private var param5: String? = null
    private var param6: String? = null
    private var param7: Int? = null
    private var param8: Int? = null
    private var param9: String? = null



    companion object {
        fun newInstance(params1: String,
                        params2: String,
                        params3: Int,
                        params4: Int,
                        params5: String,
                        params6: String,
                        params7: Int,
                        params9: String,
                        params8: Int) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE,params1)
                    putString(ARG_CATEGORY,params2)
                    putInt(ARG_AMOUNT_REACHED,params3)
                    putInt(ARG_AMOUNT_GOAL,params4)
                    putString(ARG_SUBCATEGORY,params5)
                    putString(ARG_LOCATION,params6)
                    putString(ARG_PID,params9)
                    putInt(ARG_IMAGES_NUMBER,params7)
                    putInt(ARG_CONTACT,params8)
                }

        }
    }
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailsViewModel
    private lateinit var pager: ViewPager
    private lateinit var tab: TabLayout
    private lateinit var amountReachedTV : TextView
    private lateinit var amountGoalTV : TextView
    private lateinit var categoryImg : ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var shareButton: ImageView
    private lateinit var actionButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_TITLE)
            param2 = it.getString(ARG_CATEGORY)
            param3 = it.getInt(ARG_AMOUNT_REACHED)
            param4 = it.getInt(ARG_AMOUNT_GOAL)
            param5 = it.getString(ARG_SUBCATEGORY)
            param6 = it.getString(ARG_LOCATION)
            param7 = it.getInt(ARG_IMAGES_NUMBER)
            param8= it.getInt(ARG_CONTACT)
            param9=it.getString(ARG_PID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val bottomNavBar : BottomNavigationView? = activity?.findViewById(R.id.bottom_nav_view)
        if (bottomNavBar != null) {
            bottomNavBar.visibility=View.GONE
        }

        val bundle = bundleOf(
            ARG_PID to param9,
            ARG_TITLE to param1,
            ARG_IMAGES_NUMBER to param7
        )
        val adapter = ViewPagerDetailsAdapter(childFragmentManager,bundle)


        bindingViews()
        setFragmentsToViewPager(adapter)

        pager.adapter=adapter
        tab.setupWithViewPager(pager)
        if (param3!! >= param4!!){
            param3 = param4
            actionButton.isEnabled = false
            actionButton.setBackgroundColor(resources.getColor(R.color.green_main_disable))
        }
        setUpDetailsByCategory()

        progressBar.max=param4!!
        progressBar.progress= param3!!

        actionButton.setOnClickListener {
            handleButtonClicked()
            }
        return binding.root
        }

    private fun handleButtonClicked() {
        if(actionButton.text == "Volunteer"){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSdJ6P6qdd51X-5Tww5yNiTK-PdYlYCkY25p0f1vNQacssnABw/viewform?usp=sf_link"))
            startActivity(intent)
        }else if (actionButton.text == "Donate"){
            val bundle : Bundle = requireArguments()
            findNavController().navigate(R.id.navigation_paiement,bundle)
        }else{
            val phoneIntent = Intent(Intent.ACTION_CALL)
            phoneIntent.data = Uri.parse("tel:+213"+param8.toString())
            if (ContextCompat.checkSelfPermission(requireActivity(),
                    Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(/* activity = */ requireActivity(),
                    /* permissions = */ arrayOf(Manifest.permission.CALL_PHONE),
                    /* requestCode = */ MY_PERMISSIONS_REQUEST_CALL_PHONE)

            } else {
                startActivity(phoneIntent)
            }
        }
    }

    private fun setUpDetailsByCategory() {
        if (param2 == "Donation" || param5== "Project Donation" || param5 == "Surgical Aids"){
            actionButton.setText(R.string.donate)
            categoryImg.visibility=View.GONE
            amountReachedTV.setText("$param3 DA")
            amountGoalTV.setText("$param4 DA")
        }else if (param5 == "Volunteer") {
            actionButton.setText(R.string.volunteer)
            categoryImg.setImageResource(R.drawable.person)
            amountReachedTV.setText(param3.toString())
            amountGoalTV.setText(param4.toString())
        }

        else if (param5 == "Blood Donation") {
            actionButton.setText(R.string.contact)
            categoryImg.setImageResource(R.drawable.person)
            amountReachedTV.setText(param3.toString())
            amountGoalTV.setText(param4.toString())
        }

        else if (param5 == "Medicine") {
            actionButton.setText(R.string.contact)
            categoryImg.setImageResource(R.drawable.pill)
            amountReachedTV.setText(param3.toString())
            amountGoalTV.setText(param4.toString())
        }
    }

    private fun setFragmentsToViewPager(adapter: ViewPagerDetailsAdapter) {
        if (param7!! >= 1){

            adapter.addFragment(DescriptionFragment(),"Description")
            adapter.addFragment(PicturesFragment(),"Pictures")
        }else
            adapter.addFragment(DescriptionFragment(),"Description")

    }

    private fun bindingViews() {
        pager= binding.viewPager
        tab =binding.tabLayout
        amountReachedTV = binding.amountReached
        amountGoalTV = binding.amountGoal
        categoryImg = binding.categoryImg
        progressBar = binding.progressBar2
        actionButton=binding.actionButton

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Camera Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        val bottomNavBar : BottomNavigationView? = activity?.findViewById(R.id.bottom_nav_view)
//        if (bottomNavBar != null) {
//            bottomNavBar.visibility=View.VISIBLE
//        }
        _binding = null
    }
}