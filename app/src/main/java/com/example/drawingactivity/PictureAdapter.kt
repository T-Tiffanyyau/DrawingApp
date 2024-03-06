package com.example.drawingactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter class for the RecyclerView in MainScreen
 * Responsible for the diplays drawings in card view
 */
class PictureAdapter(
    private var mList: List<DrawingItem>,
    private val onDrawingSelected: (drawing: DrawingItem) -> Unit
) : RecyclerView.Adapter<PictureAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.drawings_cardview, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageBitmap(ItemsViewModel.pic)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.title

        // Calls the onDrawingSelected listener to load the specific drawing
        holder.itemView.setOnClickListener {
            onDrawingSelected.invoke(ItemsViewModel)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    /**
     * Update the list of drawings on main screen
     */
    fun updateItems(newItems: List<DrawingItem>) {
        this.mList = newItems
        notifyDataSetChanged()
    }
}
