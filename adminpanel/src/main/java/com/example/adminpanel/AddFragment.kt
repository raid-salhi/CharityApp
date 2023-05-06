package com.example.adminpanel

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.example.adminpanel.classes.Post
import com.example.adminpanel.databinding.FragmentAddBinding
import com.example.adminpanel.databinding.FragmentDonateBinding
import com.example.adminpanel.databinding.FragmentNeedHelpRequestBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!
    private lateinit var newPost : Post
    private var categorySelected : String? =null
    private var subcategorySelected : String? = null
    private var wilayaSelected : String? = null
    private lateinit var pid :String
    private lateinit var db : FirebaseFirestore
    private lateinit var storage: StorageReference
    private lateinit var uriList : ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)

        binding.category.setOnItemClickListener { parent, view, position, id ->

            categorySelected= parent.getItemAtPosition(position).toString()
            if (categorySelected=="Projects"){
                binding.subCategoryLayout.visibility=View.VISIBLE
                val items = resources.getStringArray(R.array.sub_Projects)
                (binding.subCategory as? MaterialAutoCompleteTextView)?.setSimpleItems(items)
            }else if (categorySelected=="Emergency"){
                binding.subCategoryLayout.visibility=View.VISIBLE
                val items = resources.getStringArray(R.array.sub_Emergency)
                (binding.subCategory as? MaterialAutoCompleteTextView)?.setSimpleItems(items)
            }else{
                binding.subCategoryLayout.visibility=View.GONE
                subcategorySelected=""
            }
        }
        binding.subCategory.setOnItemClickListener { parent, view, position, id ->
            subcategorySelected= parent.getItemAtPosition(position).toString()
        }
        binding.wilaya.setOnItemClickListener { parent, view, position, id ->
            wilayaSelected=parent.getItemAtPosition(position).toString()
        }
        uriList=ArrayList()
        pid = UUID.randomUUID().toString()
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->

            for(uri in uris)
                uriList.add(uri.toString())
            Toast.makeText(requireContext(), "You have selected "+uriList.size.toString()+ " pictures", Toast.LENGTH_SHORT).show()
        }
        binding.buttonUploadPics.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.postButton.setOnClickListener {
            if (binding.titleEditText.text!!.isNotEmpty() && binding.descriptionEditText.text!!.isNotEmpty() && binding.phoneEditText.text!!.isNotEmpty() && binding.amountEditText.text!!.isNotEmpty() && wilayaSelected != null && categorySelected != null && subcategorySelected != null){
                newPost = Post(
                    title = binding.titleEditText.text.toString(),
                    pid = pid,
                    category = categorySelected!!,
                    subCategory = subcategorySelected!!,
                    location = wilayaSelected!!,
                    amountGoal = binding.amountEditText.text.toString().toInt(),
                    amountReached = 0,
                    description =  binding.descriptionEditText.text.toString(),
                    contact = binding.phoneEditText.text.toString().toInt(),
                    imagesNumber = uriList.size
                )
                if (uriList.isNotEmpty())
                    saveImageInFirebase(uriList)
                savePostInFirebase(newPost)

            }else{
                Toast.makeText(requireContext(), "Please fill all the necessar information", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }

    private fun savePostInFirebase(newPost: Post) {
        db = Firebase.firestore
        db.collection(newPost.category).document(newPost.pid)
            .set(newPost)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "post uploaded", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Check your internet connexion",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun saveImageInFirebase(uriList: ArrayList<String>) {
        for (i in uriList.indices){
            val reducedImage = reduceImageSize(uriList[i].toUri())
            storage = FirebaseStorage.getInstance().getReference("Images/"+ pid+ i)
            storage.putBytes(reducedImage).addOnSuccessListener {
                Toast.makeText(context, "upload success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(context, "upload failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun reduceImageSize(uri: Uri): ByteArray {
        val imageUri: Uri = uri
        val inputStream = context?.contentResolver?.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val maxSize = 1024 // in pixels
        val width = bitmap.width
        val height = bitmap.height
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
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
        val outputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        return outputStream.toByteArray()
    }

}