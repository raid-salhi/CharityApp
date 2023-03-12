package com.example.charityapp.ui.donate


import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.charityapp.databinding.FragmentDonateBinding
import com.example.charityapp.ui.donate.donateTabs.financialAids.FinancialAidsFragment
import com.example.charityapp.ui.donate.donateTabs.others.OthersFragment
import com.google.android.material.tabs.TabLayout

class DonateFragment : Fragment() {
    private var _binding: FragmentDonateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tab: TabLayout
    private lateinit var viewModel: DonateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDonateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = ViewPagerDonateAdapter(childFragmentManager)
        pager= binding.pager
        tab =binding.tabs

        adapter.addFragment(FinancialAidsFragment(),"Financial Aids")
        adapter.addFragment(OthersFragment(),"Others")

        pager.adapter=adapter
        tab.setupWithViewPager(pager)


        return  root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DonateViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}