package com.example.charityapp.ui.emergency


import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentEmergencyBinding
import com.example.charityapp.ui.emergency.bloodDonation.BloodDonationFragment
import com.example.charityapp.ui.emergency.medecine.MedecineFragment
import com.example.charityapp.ui.emergency.surgicalAids.SurgicalAidsFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class EmergencyFragment : Fragment() {
    private var _binding: FragmentEmergencyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tab: TabLayout
    private lateinit var viewModel: EmergencyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergencyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = ViewPagerEmergencyAdapter(childFragmentManager)
        pager= binding.pager
        tab =binding.tabs

        adapter.addFragment(BloodDonationFragment(),"Blood Donation")
        adapter.addFragment(MedecineFragment(),"Medecine")
        adapter.addFragment(SurgicalAidsFragment(),"Surgical Aids")

        pager.adapter=adapter
        tab.setupWithViewPager(pager)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EmergencyViewModel::class.java)
        // TODO: Use the ViewModel
        lifecycleScope.launch {
            val toolbarTitle = activity?.findViewById<TextView>(R.id.toolbar_title)
            toolbarTitle?.setText(R.string.title_emergency)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}