package com.example.charityapp.ui.details.pictures

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.charityapp.R
import com.example.charityapp.databinding.FragmentPicturesBinding
import com.example.charityapp.ui.recyclerViews.SliderImageAdapter
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.smarteist.autoimageslider.SliderView
import java.io.File

private const val ARG_TITLE ="title"
private const val ARG_PID="pid"
class PicturesFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    companion object {
        fun newInstance(params1: String,
                        params2: String) = PicturesFragment().apply {
            arguments=Bundle().apply {
                putString(ARG_TITLE,params1)
                putString(ARG_PID,params2)
            }
        }
    }

    private lateinit var viewModel: PicturesViewModel
    private var _binding: FragmentPicturesBinding? = null
    private val binding get() = _binding!!
    lateinit var imageList : ArrayList<Bitmap>
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderImageAdapter
    private lateinit var storage : StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            param1=it.getString(ARG_TITLE)
            param2=it.getString(ARG_PID)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentPicturesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.title.text=param1
        imageList = ArrayList()
        for (i in 1..2) {
            storage = FirebaseStorage.getInstance().reference.child("Images/"+param2+"$i.jpg")
//            Toast.makeText(activity,"Images/"+param2+"$i" , Toast.LENGTH_SHORT).show()
            val localFile = File.createTempFile("tempImage", "jpg")
            storage.getFile(localFile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                imageList.add(bitmap)
                sliderAdapter.notifyDataSetChanged()
            }
        }

        sliderView = binding.imageSlider


        sliderAdapter = SliderImageAdapter(imageList)
        sliderView.setSliderAdapter(sliderAdapter)
//        sliderView.startAutoCycle()

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PicturesViewModel::class.java)

    }

}