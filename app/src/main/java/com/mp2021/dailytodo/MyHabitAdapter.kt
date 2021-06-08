package com.mp2021.dailytodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyHabitAdapter(val items:ArrayList<MyHabit>):RecyclerView.Adapter<MyHabitAdapter.ViewHolder>(){
    interface OnItemClickListener{
        fun OnItemClick(holder: ViewHolder,view: View,data: MyHabit,position: Int)
    }
    interface OnItemLongClickListener {
        fun OnItemLongClick(holder: ViewHolder,view: View,data: MyHabit,position: Int)
    }
    var position: Int=0
    var itemClickListener:OnItemClickListener?=null
    var itemLongClickListener:OnItemLongClickListener?=null
    override fun getItemViewType(position: Int): Int {
        return position
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textView1:TextView = itemView.findViewById(R.id.habit_name)
        val textView2:TextView = itemView.findViewById(R.id.streak)
        val textView3:TextView = itemView.findViewById(R.id.habit_detail)
        val textView4:TextView = itemView.findViewById(R.id.start_day)
        init{
            itemView.setOnClickListener {
                itemClickListener?.OnItemClick(this,it,items[adapterPosition],adapterPosition)
            }
            itemView.setOnLongClickListener {
                itemLongClickListener?.OnItemLongClick(this,it,items[adapterPosition],adapterPosition)
                true;
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount():Int{
        return items.size
    }

    fun getName(pos:Int):String{
        return items[pos].habit_name
    }
    fun getDetail(pos:Int):String{
        return items[pos].habit_detail
    }
    fun getStreakil(pos:Int):Int{
        return items[pos].streak
    }
    fun getDate(pos:Int):String{
        return items[pos].start_date
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView1.text = items[position].habit_name
        holder.textView2.text = items[position].streak.toString()
        holder.textView3.text = items[position].habit_detail
        holder.textView4.text = items[position].start_date
    }
}