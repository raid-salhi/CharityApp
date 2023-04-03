package com.example.charityapp.ui.details.pictures

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentEmergencyBinding
import com.example.charityapp.databinding.FragmentPicturesBinding
import com.example.charityapp.ui.recyclerViews.SliderImageAdapter
import com.smarteist.autoimageslider.SliderView

class PicturesFragment : Fragment() {

    companion object {
        fun newInstance() = PicturesFragment()
    }

    private lateinit var viewModel: PicturesViewModel
    private var _binding: FragmentPicturesBinding? = null
    private val binding get() = _binding!!
    lateinit var imageList : ArrayList<Int>
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentPicturesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sliderView = binding.imageSlider

        imageList = ArrayList()
        imageList.add(R.drawable.img1)
        imageList.add(R.drawable.logo)
        imageList.add(R.drawable.event)

        sliderAdapter = SliderImageAdapter(imageList)
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.startAutoCycle()

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PicturesViewModel::class.java)

    }

}