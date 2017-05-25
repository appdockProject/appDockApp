package com.appdockproject.appdock.Data

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.appdockproject.appdock.R

/**
 * Created by jangerhard on 04-May-17.
 */

class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var vidLogo: ImageView
    internal var vidName: TextView
    var vidClass: Class<*>? = null

    fun setVidName(name: String) {
        vidName.text = name
    }

    init {

        vidLogo = itemView.findViewById(R.id.vidIcon) as ImageView
        vidName = itemView.findViewById(R.id.vidName) as TextView

        itemView.setOnClickListener { v -> mClickListener!!.onItemClick(v, adapterPosition) }

    }

    private var mClickListener: VideoViewHolder.ClickListener? = null

    //Interface to send callbacks...
    interface ClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnClickListener(clickListener: VideoViewHolder.ClickListener) {
        mClickListener = clickListener
    }

}
