package com.example.charityapp.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentDonateBinding
import com.example.charityapp.databinding.FragmentProfileBinding
import com.example.charityapp.databinding.FragmentProjectsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        lifecycleScope.launch {
            val logoutButton : Button?= activity?.findViewById(R.id.logoutButton)
            logoutButton?.visibility = View.VISIBLE
            val bottomNavBar : BottomNavigationView? = activity?.findViewById(R.id.bottom_nav_view)
            if (bottomNavBar != null) {
                bottomNavBar.visibility=View.GONE
            }
        }
    }

}