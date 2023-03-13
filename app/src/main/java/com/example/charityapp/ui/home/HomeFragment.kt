package com.example.charityapp.ui.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val toolbarTitle = activity?.findViewById<TextView>(R.id.toolbar_title)
        toolbarTitle?.setText(R.string.title_home)


        return root
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
        lifecycleScope.launch {
            val toolbarTitle = activity?.findViewById<TextView>(R.id.toolbar_title)
            toolbarTitle?.setText(R.string.title_home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}