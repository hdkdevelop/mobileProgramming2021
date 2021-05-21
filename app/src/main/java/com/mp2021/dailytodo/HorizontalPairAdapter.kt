package com.mp2021.dailytodo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mp2021.dailytodo.databinding.RowHorizontalPairBinding

class HorizontalPairAdapter(val items: ArrayList<Pair<String, String>>): RecyclerView.Adapter<HorizontalPairAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RowHorizontalPairBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowHorizontalPairBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.left.text = items[position].first
        holder.binding.right.text = items[position].second
    }

    override fun getItemCount(): Int {
        return items.size
    }
}