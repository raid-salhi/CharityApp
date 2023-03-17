package com.example.charityapp.ui.recyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.charityapp.MainActivity
import com.example.charityapp.R
import com.example.charityapp.classes.Post
import com.example.charityapp.ui.details.DetailsFragment

class PostProjectsRVAdapter(private val mList: List<Post>,private val clickHandler: PostClickHandler) : RecyclerView.Adapter<PostProjectsRVAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_project, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        holder.iconCategory.setImageResource(ItemsViewModel.iconCategory)
        holder.title.text = ItemsViewModel.title
        holder.category.text = ItemsViewModel.category
        holder.location.text = ItemsViewModel.location
        holder.amountRemaining.text = ItemsViewModel.getAmountRemaining()
        holder.progressBar.max= ItemsViewModel.amountGoal
        holder.progressBar.progress= ItemsViewModel.amountReached
        holder.donateButton.setOnClickListener {
            navigateTofragment(DetailsFragment())
        }


    }

    private fun navigateTofragment(detailsFragment: DetailsFragment) {

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView),View.OnClickListener {
        val iconCategory: ImageView = itemView.findViewById(R.id.categoryIcon)
        val title: TextView = itemView.findViewById(R.id.title)
        val category: TextView = itemView.findViewById(R.id.category)
        val location: TextView = itemView.findViewById(R.id.location)
        val amountRemaining: TextView = itemView.findViewById(R.id.amountRemaining)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val donateButton : Button = itemView.findViewById(R.id.buttonDonate)
        init {
            itemView.setOnClickListener (this)

        }
        override fun onClick(v: View?) {
            val currentPost = mList[bindingAdapterPosition]
            clickHandler.clickedPostItem(currentPost)
        }
    }
}