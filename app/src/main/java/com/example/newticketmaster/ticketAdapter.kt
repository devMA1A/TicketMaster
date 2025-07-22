package com.example.newticketmaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private const val TAG = "ticketAdapter"

class ticketAdapter(private val events: List<Event>) : RecyclerView.Adapter<ticketAdapter.MyViewHolder>() {

    inner class MyViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {
        // This class will represent a single row in our recyclerView list
        // This class also allows caching views and reuse them
        // Each MyViewHolder object keeps a reference to 3 view items in our row_item.xml file
        val eventImage = itemView.findViewById<ImageView>(R.id.iv_image)
        val eventName = itemView.findViewById<TextView>(R.id.tv_name)
        val eventLocation = itemView.findViewById<TextView>(R.id.tv_location)
        val eventDate = itemView.findViewById<TextView>(R.id.tv_dateTime)
    //  val eventPrice = itemView.findViewById<TextView>(R.id.


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ticketAdapter.MyViewHolder {
        // Inflate a layout from our XML (row_item.XML) and return the holder
        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ticketAdapter.MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val currentItem = events[position]
        holder.eventName.text = "${currentItem.name}"
        holder.eventLocation.text = "${currentItem._embedded.venues[0].name} ${currentItem._embedded.venues[0].city}"
        holder.eventDate.text = "${currentItem.dates.start.localDate} at  ${currentItem.dates.start.localTime}"

        // Get the context for glide
        val context = holder.itemView.context

        val highestQualityImage = currentItem.image.maxByOrNull {
            it.width.toInt() * it.height.toInt()
        }

        // Load the image from the url using Glide library
        if (highestQualityImage != null){
            Glide.with(context)
                .load(currentItem.image[0].url)
                .into(holder.eventImage)
        } else {
            Glide.with(context)
                .load(R.drawable.ic_launcher_foreground) // In case the image is not loaded show this placeholder image
                .into(holder.eventImage)

        }


    }

    override fun getItemCount(): Int {
        // Return the size of your dataset (invoked by the layout manager)
        return events.size
    }




}