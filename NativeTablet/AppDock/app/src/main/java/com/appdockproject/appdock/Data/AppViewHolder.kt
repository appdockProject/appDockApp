package com.appdockproject.appdock.Data

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.appdockproject.appdock.R

import android.os.Build.VERSION_CODES.M

/**
 * Created by jangerhard on 02-May-17.
 */

class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var logo: ImageView = itemView.findViewById(R.id.appLogo) as ImageView
    var appName: TextView = itemView.findViewById(R.id.appName) as TextView

    fun setAppName(name: String) {
        appName.text = name
    }

    init {

        itemView.setOnClickListener { v -> mClickListener!!.onItemClick(v, adapterPosition) }
    }

    private var mClickListener: AppViewHolder.ClickListener? = null

    //Interface to send callbacks...
    interface ClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnClickListener(clickListener: AppViewHolder.ClickListener) {
        mClickListener = clickListener
    }
}
