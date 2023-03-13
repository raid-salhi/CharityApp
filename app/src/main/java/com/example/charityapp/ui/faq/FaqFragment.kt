package com.example.charityapp.ui.faq

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.charityapp.R
import kotlinx.coroutines.launch

class FaqFragment : Fragment() {

    companion object {
        fun newInstance() = FaqFragment()
    }

    private lateinit var viewModel: FaqViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[FaqViewModel::class.java]
        // TODO: Use the ViewModel
        lifecycleScope.launch {
            val toolbarTitle = activity?.findViewById<TextView>(R.id.toolbar_title)
            toolbarTitle?.setText(R.string.faq)
        }
    }

}