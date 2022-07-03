package com.example.recipe_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.databinding.ActivityFavoritesListAdapterBinding
import com.example.recipe_app.pojo.RecipeDetail

class FavoritesListAdapter :
    RecyclerView.Adapter<FavoritesListAdapter.FavoritesAdapterViewHolder>() {

    inner class FavoritesAdapterViewHolder(val binding: ActivityFavoritesListAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<RecipeDetail>() {
        override fun areItemsTheSame(oldItem: RecipeDetail, newItem: RecipeDetail): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: RecipeDetail, newItem: RecipeDetail): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapterViewHolder {
        return FavoritesAdapterViewHolder(
            ActivityFavoritesListAdapterBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesAdapterViewHolder, position: Int) {
        val recipe = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(recipe.strMealThumb)
            .into(holder.binding.favoriteImage)

        holder.binding.itemName.text = recipe.strMeal
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}