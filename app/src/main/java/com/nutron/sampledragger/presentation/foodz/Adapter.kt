package com.nutron.sampledragger.presentation.foodz

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nutron.sampledragger.R
import com.nutron.sampledragger.data.entity.FoodzItem
import com.nutron.sampledragger.extensions.findView
import com.nutron.sampledragger.extensions.stripPrefix


interface FoodzItemClickedListener {
    fun onFoodzClicked(item: FoodzItem)
}

class FoodzAdapter() : RecyclerView.Adapter<FoodzViewHolder>() {

    var listener: ((FoodzItem) -> Unit)? = null
    var items: List<FoodzItem> = listOf()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodzViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val holder = FoodzViewHolder(inflater.inflate(R.layout.item_food, parent, false))
        holder.itemView.setOnClickListener { listener?.invoke(items[holder.adapterPosition]) }
        return holder
    }

    override fun onBindViewHolder(holder: FoodzViewHolder, position: Int) {
        val foodzItem = items[position]
        holder.bind(foodzItem)
    }
}

class FoodzViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val foodName: TextView = view.findView(R.id.itemFoodName)

    fun bind(item: FoodzItem) {
        foodName.text = item.name.stripPrefix()
    }


}