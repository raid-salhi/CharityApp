package com.example.adminpanel.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.PostClickHandler
import com.example.adminpanel.R

class RequestPostRVAdapter (private val mList: List<Post>, private val clickHandler: PostClickHandler) : RecyclerView.Adapter<RequestPostRVAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.request_item_card, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]
        if (ItemsViewModel.amountReached >= ItemsViewModel.amountGoal) {
            ItemsViewModel.amountReached = ItemsViewModel.amountGoal
        }
        holder.title.text = ItemsViewModel.title
        holder.category.text = ItemsViewModel.category
        holder.location.text = ItemsViewModel.location
        holder.amountReached.text = ItemsViewModel.getAmountReachedPer()
        holder.progressBar.max = ItemsViewModel.amountGoal
        holder.progressBar.progress = ItemsViewModel.amountReached

        setCustomizationBySubCategory(holder, ItemsViewModel)

        holder.acceptButton.setOnClickListener {
            clickHandler.acceptPostItem(ItemsViewModel)
        }
        holder.declineButton.setOnClickListener {
            clickHandler.deletePostItem(ItemsViewModel)
        }

    }

    private fun setCustomizationBySubCategory(holder: ViewHolder, ItemsViewModel: Post) {

        if(ItemsViewModel.subCategory == "Blood Donation") {
            holder.amountGoalIcon.setImageResource(R.drawable.person)
            holder.iconCategory.setImageResource(R.drawable.outline_bloodtype_24)
            holder.amountGoal.text = ItemsViewModel.getAmountGoalString()
        } else if (ItemsViewModel.subCategory == "Medicine") {
            holder.amountGoalIcon.setImageResource(R.drawable.pill)
            holder.iconCategory.setImageResource(R.drawable.pill_outline)
            holder.amountGoal.text = ItemsViewModel.getAmountGoalString()

        } else if (ItemsViewModel.subCategory == "Surgical Aids") {
            holder.amountGoalIcon.visibility = View.GONE
            holder.iconCategory.setImageResource(R.drawable.surgical)
            holder.amountGoal.text = ItemsViewModel.getAmountGoalCash()
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView),
        View.OnClickListener {
        val iconCategory: ImageView = itemView.findViewById(R.id.categoryIcon)
        val title: TextView = itemView.findViewById(R.id.title)
        val category: TextView = itemView.findViewById(R.id.category)
        val location: TextView = itemView.findViewById(R.id.location)
        val amountGoal: TextView = itemView.findViewById(R.id.amountGoal)
        val amountGoalIcon: ImageView = itemView.findViewById(R.id.amountGoalIcon)
        val amountReached: TextView = itemView.findViewById(R.id.amountReached)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val acceptButton: Button = itemView.findViewById(R.id.accept_btn)
        val declineButton: Button = itemView.findViewById(R.id.reject_btn)
        val frame: FrameLayout = itemView.findViewById(R.id.frame)

        init {
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            val currentPost = mList[bindingAdapterPosition]
            clickHandler.clickedPostItem(currentPost)
        }
    }
}