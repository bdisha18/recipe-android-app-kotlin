package com.example.recipe_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app.databinding.ActivityAreaListAdapterBinding
import com.example.recipe_app.pojo.Area

class AreaListAdapter : RecyclerView.Adapter<AreaListAdapter.AreaViewHolder>() {

    private var areaList: List<Area> = ArrayList()
    var onItemClick: ((Area) -> Unit)? = null

    fun setArea(areaList: List<Area>) {
        this.areaList = areaList
        notifyDataSetChanged()
    }

    class AreaViewHolder(val binding: ActivityAreaListAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        return AreaViewHolder(
            ActivityAreaListAdapterBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {

        holder.binding.apply {
            tvArea.text = areaList[position].strArea
        }

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(areaList[position])
        }
    }

    override fun getItemCount(): Int {
        return areaList.size
    }

}


