package com.example.adminpanel

import android.os.Bundle
import android.util.Log
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.classes.Post
import com.example.adminpanel.classes.PostRVAdapter
import com.example.adminpanel.databinding.FragmentEmergencyBinding
import com.example.adminpanel.databinding.FragmentNeedHelpRequestBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
private const val MY_PERMISSIONS_REQUEST_CALL_PHONE = 1

class NeedHelpRequestFragment : Fragment(), PostClickHandler {
    private var _binding: FragmentNeedHelpRequestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var postRV : RecyclerView
    private lateinit var adapter : PostRVAdapter
    private lateinit var postList : ArrayList<Post>
    private lateinit var db : FirebaseFirestore
    private val TAG = "AllDonationFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNeedHelpRequestBinding.inflate(inflater, container, false)
        val root: View = binding.root
        postList = arrayListOf()
        db = Firebase.firestore
        adapter= PostRVAdapter(postList,this)
        postRV =binding.allDonationRV
        postRV.setHasFixedSize(true)
        postRV.layoutManager = LinearLayoutManager(requireContext())
        postRV.adapter = adapter
        eventChangeListner()


        return  root
    }

    private fun eventChangeListner() {

        db.collection("NeedHelpReq").get().addOnSuccessListener{ result ->
            for (document in result)
            {
                binding.animationView.visibility = View.GONE
                postList.add(document.toObject<Post>())
                adapter.notifyDataSetChanged()
            }
        }.addOnFailureListener {
            Log.d(TAG, "Error getting documents: ", it)
        }
    }
    override fun clickedPostItem(post: Post) {
        Log.d(TAG, post.title)
        val bundle= bundleOf(
            "pid" to post.pid,
            "title" to post.title,
            "category" to post.category,
            "location" to post.location,
            "amountGoal" to post.amountGoal,
            "amountReached" to post.amountReached,
            "subCategory" to post.subCategory,
            "description" to post.description,
            "imagesNumber" to post.imagesNumber,
            "description" to post.description,
            "contact" to post.contact,
            "imagesNumber" to post.imagesNumber)



//        findNavController().navigate(R.id.navigation_details,bundle)
    }

    override fun deletePostItem(post: Post) {
        TODO("Not yet implemented")
    }

    override fun callAction(post: Post) {
        val phoneIntent = Intent(Intent.ACTION_CALL)
        phoneIntent.data = Uri.parse("tel:+213"+post.contact.toString())
        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(/* activity = */ requireActivity(),
                /* permissions = */ arrayOf(Manifest.permission.CALL_PHONE),
                /* requestCode = */ MY_PERMISSIONS_REQUEST_CALL_PHONE)

        } else {
            startActivity(phoneIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}