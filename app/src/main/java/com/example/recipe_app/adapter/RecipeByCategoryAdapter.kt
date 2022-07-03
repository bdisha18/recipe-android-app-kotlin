package com.example.recipe_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.databinding.ActivityRecipeByCategoryAdapterBinding
import com.example.recipe_app.pojo.RecipeByCategory

class RecipeByCategoryAdapter :
    RecyclerView.Adapter<RecipeByCategoryAdapter.RecipeByCategoryViewHolder>() {
    private var recipeList: List<RecipeByCategory> = ArrayList()
    lateinit var onItemClick: ((RecipeByCategory) -> Unit)

    fun setRecipesByCategory(recipeList: List<RecipeByCategory>) {
        this.recipeList = recipeList
        notifyDataSetChanged()
    }

    class RecipeByCategoryViewHolder(val binding: ActivityRecipeByCategoryAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeByCategoryViewHolder {
        return RecipeByCategoryViewHolder(
            ActivityRecipeByCategoryAdapterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeByCategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(recipeList[position].strMealThumb)
            .into(holder.binding.recipeCardImage)

        holder.binding.apply {
            recipeName.text = recipeList[position].strMeal
        }

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(recipeList[position])

        }

        holder.binding.recipeFavorites.setOnClickListener {
            onItemClick!!.invoke(recipeList[position])
        }


    }

    override fun getItemCount(): Int {
        return recipeList.size
    }


}