package com.example.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ShopItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
    val tvProduct=view.findViewById<TextView>(R.id.tvProduct)
    val tvNumber=view.findViewById<TextView>(R.id.tvNumber)
}