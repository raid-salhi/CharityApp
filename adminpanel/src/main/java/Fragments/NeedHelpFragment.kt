package Fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adminpanel.R

class NeedHelpFragment : Fragment() {

    companion object {
        fun newInstance() = NeedHelpFragment()
    }

    private lateinit var viewModel: NeedHelpViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_need_help_request, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NeedHelpViewModel::class.java)
        // TODO: Use the ViewModel
    }

}