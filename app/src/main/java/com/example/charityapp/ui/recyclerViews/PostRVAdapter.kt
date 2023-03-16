package com.example.charityapp.ui.recyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.charityapp.R
import com.example.charityapp.classes.Post


class PostRVAdapter(private val mList: List<Post>) : RecyclerView.Adapter<PostRVAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)

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

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val iconCategory: ImageView = itemView.findViewById(R.id.categoryIcon)
        val title: TextView = itemView.findViewById(R.id.title)
        val category: TextView = itemView.findViewById(R.id.category)
        val location: TextView = itemView.findViewById(R.id.location)
        val amountRemaining: TextView = itemView.findViewById(R.id.amountRemaining)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    }
}
