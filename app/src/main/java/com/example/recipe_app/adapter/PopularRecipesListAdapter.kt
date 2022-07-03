package com.example.recipe_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipe_app.databinding.ActivityPopularRecipesListAdapterBinding
import com.example.recipe_app.pojo.RecipeByCategory

class PopularRecipesListAdapter :
    RecyclerView.Adapter<PopularRecipesListAdapter.PopularRecipeViewHolder>() {
    private var popularRecipeList: List<RecipeByCategory> = ArrayList()
    lateinit var onItemClick: ((RecipeByCategory) -> Unit)

    fun setPopularRecipes(popularRecipeList: List<RecipeByCategory>) {
        this.popularRecipeList = popularRecipeList
        notifyDataSetChanged()
    }

    class PopularRecipeViewHolder(val binding: ActivityPopularRecipesListAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularRecipeViewHolder {
        return PopularRecipeViewHolder(
            ActivityPopularRecipesListAdapterBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: PopularRecipeViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(popularRecipeList[position].strMealThumb)
            .into(holder.binding.popularRecipeImg)

//        holder.binding.apply {
//            tvRecipe.text = popularRecipeList[position].strMeal
//        }
        holder.itemView.setOnClickListener {
            onItemClick.invoke(popularRecipeList[position])
        }
    }

    override fun getItemCount(): Int {
        return popularRecipeList.size
    }
}