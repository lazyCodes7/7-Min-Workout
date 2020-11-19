package com.sevenminuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.exercise_timer.view.*

class ExerciseViewAdapter(val items: ArrayList<ExerciseModel>,val context: Context) : RecyclerView.Adapter<ExerciseViewAdapter.ViewHolder>() {
    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val tvItem = view.tvItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.exercise_timer,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        if(item.getisSelected()){
            holder.tvItem.background = ContextCompat.getDrawable(context,R.drawable.item_circular_color_accent_1)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }
        else if(item.getisCompleted()){
            holder.tvItem.background = ContextCompat.getDrawable(context,R.drawable.item_circular_color_accent_background)
            holder.tvItem.setTextColor(Color.WHITE)
        }
        else{
            holder.tvItem.background = ContextCompat.getDrawable(context,R.drawable.item_circular_color_gray_background)
            holder.tvItem.setTextColor(Color.WHITE)
        }
        holder.tvItem.text=item.getId().toString()
    }
}