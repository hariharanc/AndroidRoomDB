package com.kotlin.roomdbrecyclerview.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.roomdbrecyclerview.R

import com.kotlin.roomdbrecyclerview.model.Student
import kotlinx.android.synthetic.main.recycler_item.view.*


class RecyclerAdapter(val items: List<Student>, val context: Context,val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.studentName?.text = items[position].name


        holder.itemView.setOnClickListener {
            clickListener.onItemClick(position)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recycler_item,
                parent,
                false
            )
        )
    }




}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val studentName = view.studentName
}


interface OnItemClickListener {
   public fun onItemClick(pos: Int)
}