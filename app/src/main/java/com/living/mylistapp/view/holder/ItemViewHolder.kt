package com.living.mylistapp.view.holder

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.living.mylistapp.R

class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var imgView = view.findViewById<ImageView>(R.id.img)
    var deleteView = view.findViewById<ImageButton>(R.id.imb_delete)
    var nameView = view.findViewById<TextView>(R.id.tv_name)
}