package com.example.recipe_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.databinding.ActivityCategoryListAdapterBinding
import com.example.recipe_app.pojo.Category

class CategoryListAdapter : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    private var categoryList: List<Category> = ArrayList()
    var onItemClick: ((Category) -> Unit)? = null

    fun setCategory(categoryList: List<Category>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    class CategoryViewHolder(val binding: ActivityCategoryListAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ActivityCategoryListAdapterBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.categoryImg)

        holder.binding.apply {
            tvCategory.text = categoryList[position].strCategory
        }

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

}


