package com.example.recipe_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipe_app.adapter.RecipeByCategoryAdapter
import com.example.recipe_app.databinding.ActivityRecipeByCategoryBinding
import com.example.recipe_app.fragments.HomeFragment
import com.example.recipe_app.viewmodels.CategoryViewModel

class RecipeByCategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecipeByCategoryBinding
    lateinit var categoryViewmodel: CategoryViewModel
    lateinit var recipeByCategoryAdapter: RecipeByCategoryAdapter
    private var categoryname = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()
        onRecipeByCategoryClick()

        categoryViewmodel = ViewModelProvider(this)[CategoryViewModel::class.java]

        categoryViewmodel.getRecipeByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryViewmodel.observeRecipeByCategoryLiveData().observe(this, Observer { recipeList ->
            binding.recipeCount.text = recipeList.size.toString()
            binding.categoryName.text = intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!
            recipeByCategoryAdapter.setRecipesByCategory(recipeList)
        })
    }

    private fun onRecipeByCategoryClick() {
        recipeByCategoryAdapter.onItemClick = { meal ->
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra(HomeFragment.RECIPE_ID, meal.idMeal)
            intent.putExtra(HomeFragment.RECIPE_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.RECIPE_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        recipeByCategoryAdapter = RecipeByCategoryAdapter()
        binding.recipesRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = recipeByCategoryAdapter
        }
    }
}