package com.example.charityapp.ui.recyclerViews



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.charityapp.R
import com.smarteist.autoimageslider.SliderViewAdapter


class SliderImageAdapter(imageList: ArrayList<Int>) :
    SliderViewAdapter<SliderImageAdapter.SliderAdapterVH>() {

    private var mSliderItems:ArrayList<Int> = imageList



    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.slider_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem = mSliderItems[position]
        viewHolder.imageView.setImageResource(sliderItem)

    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.myimage)
    }
}