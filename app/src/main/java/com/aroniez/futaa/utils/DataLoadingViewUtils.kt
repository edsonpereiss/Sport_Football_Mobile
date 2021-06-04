package com.aroniez.futaa.utils

import android.view.View
import com.aroniez.futaa.AppExecutors
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.view.*
import kotlinx.android.synthetic.main.include_message_layout.view.*
import kotlinx.android.synthetic.main.include_progressbar_layout.view.*

fun showLoadingProgress(itemView: View) {
    AppExecutors().mainThread().execute {
        itemView.baseProgressBar.visibility = View.VISIBLE
        if (itemView.baseRecyclerView != null) {
            itemView.baseRecyclerView.visibility = View.GONE
        }
    }
}

fun hideLoadingProgress(itemView: View?) {
    if (itemView != null) {
        itemView.baseProgressBar.visibility = View.GONE
        if (itemView.baseRecyclerView != null) {
            itemView.baseRecyclerView.visibility = View.VISIBLE
        }
    }
}

fun showMessageLayout(message: String, itemView: View) {
    AppExecutors().mainThread().execute {
        itemView.baseProgressBar.visibility = View.GONE
        if (itemView.baseRecyclerView != null) {
            itemView.baseRecyclerView.visibility = View.GONE
        }
        itemView.baseMessageTextView.visibility = View.VISIBLE
        itemView.baseMessageTextView.text = message
    }
}