package com.example.charityapp.ui.needhelp

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.charityapp.R
import com.example.charityapp.classes.Post
import com.example.charityapp.databinding.NeedhelpLayout3Binding
import com.example.charityapp.databinding.NeedhelpLayout4Binding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.UUID

private const val ARG_SUB_CATEGORY = "subCategory"
private const val ARG_TITLE = "title"
private const val ARG_LOCATION = "location"
private const val ARG_DESCRIPTION = "description"
private const val ARG_URI = "uri"
private const val ARG_CONTACT = "contact"

class NeedHelp4Fragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null
    private var param5: String? = null
    private var param6: Int? = null

    companion object {
        fun newInstance(
            params1: String,
            params2: String,
            params3: String,
            params5: String,
            params6: Int,
            params4: String
        ) =
            NeedHelp4Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SUB_CATEGORY, params1)
                    putString(ARG_TITLE, params2)
                    putString(ARG_DESCRIPTION, params3)
                    putString(ARG_URI, params4)
                    putString(ARG_LOCATION, params5)
                    putInt(ARG_CONTACT, params6)
                }
            }
    }

    private lateinit var viewModel: NeedHelp4ViewModel
    private lateinit var storage: StorageReference
    private lateinit var postId: String
    private lateinit var post: Post
    private lateinit var db: FirebaseFirestore
    private var _binding: NeedhelpLayout4Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NeedhelpLayout4Binding.inflate(inflater, container, false)
        db = Firebase.firestore
        setEditTextSuffix(param1)
        postId = UUID.randomUUID().toString()
        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.submitButton.isEnabled = isChecked
        }


        binding.submitButton.setOnClickListener {
            post = Post(
                pid = postId,
                title = param2.toString(),
                "Emergency",
                location = param5.toString(),
                amountGoal = binding.amountEditText.text.toString().toInt(),
                0,
                subCategory = param1.toString(),
                description = param3.toString(),
                1,
                contact = param6!!
            )
            if (param4 != null)
                saveImageInFirebase(param4!!.toUri())
            savePostInFirebase(post)


        }
        return binding.root
    }

    private fun setEditTextSuffix(param1: String?) {
        if (param1!! == "Medicine")
            binding.amountLayout.suffixText = "Pcs"
        else if (param1!! == "Blood Donation")
            binding.amountLayout.suffixText = "Person"
    }

    private fun savePostInFirebase(post: Post) {
        db.collection("NeedHelpReq").document(postId)
            .set(post)
            .addOnSuccessListener {
                val dialog = Dialog(requireContext())
                dialog.setContentView(R.layout.dialog_bg_needhelp)
                dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                val dismissButton = dialog.findViewById<ImageView>(R.id.dismiss)
                dismissButton.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
                findNavController().navigate(R.id.navigation_home)
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Check your internet connexion",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NeedHelp4ViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_SUB_CATEGORY)
            param2 = it.getString(ARG_TITLE)
            param3 = it.getString(ARG_DESCRIPTION)
            param4 = it.getString(ARG_URI)
            param5 = it.getString(ARG_LOCATION)
            param6 = it.getInt(ARG_CONTACT)
        }
        val bottomNavBar: BottomNavigationView? = activity?.findViewById(R.id.bottom_nav_view)
        if (bottomNavBar != null) {
            bottomNavBar.visibility = View.GONE
        }

    }

    private fun saveImageInFirebase(imgUri: Uri) {

        val reducedImage = reduceImageSize(imgUri)

        storage = FirebaseStorage.getInstance().getReference("NeedHelpReq/" + postId)
        storage.putBytes(reducedImage).addOnSuccessListener {
            Toast.makeText(context, "upload success", Toast.LENGTH_SHORT).show()
        }


    }

    private fun reduceImageSize(imgUri: Uri): ByteArray {
        // Get the Uri of the image you want to upload
        val imageUri: Uri = imgUri

// Get the InputStream from the Uri
        val inputStream = context?.contentResolver?.openInputStream(imageUri)

// Create a Bitmap from the InputStream
        val bitmap = BitmapFactory.decodeStream(inputStream)

// Set the maximum image size you want to allow
        val maxSize = 1024 // in pixels

// Get the current dimensions of the bitmap
        val width = bitmap.width
        val height = bitmap.height

// Calculate the new dimensions for the bitmap
        var newWidth = width
        var newHeight = height
        if (width > maxSize || height > maxSize) {
            if (width > height) {
                newWidth = maxSize
                newHeight = (newWidth * height) / width
            } else {
                newHeight = maxSize
                newWidth = (newHeight * width) / height
            }
        }

// Create a new bitmap with the new dimensions
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)

// Create a ByteArrayOutputStream to hold the compressed image data
        val outputStream = ByteArrayOutputStream()

// Compress the resized bitmap and write it to the ByteArrayOutputStream
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

// Get the compressed image data as a ByteArray

        return outputStream.toByteArray()
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