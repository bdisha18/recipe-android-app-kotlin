package com.example.recipe_app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.MainActivity
import com.example.recipe_app.R
import com.example.recipe_app.RecipeByCategoryActivity
import com.example.recipe_app.RecipeDetailActivity
import com.example.recipe_app.adapter.CategoryListAdapter
import com.example.recipe_app.adapter.PopularRecipesListAdapter
import com.example.recipe_app.databinding.FragmentHomeBinding
import com.example.recipe_app.pojo.RecipeByCategory
import com.example.recipe_app.viewmodels.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var popularRecipeAdapter: PopularRecipesListAdapter
    private lateinit var categoryListAdapter: CategoryListAdapter

    companion object {
        const val RECIPE_ID = "com.example.recipe_app.fragments.idMeal"
        const val RECIPE_NAME = "com.example.recipe_app.fragments.nameMeal"
        const val RECIPE_THUMB = "com.example.recipe_app.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.recipe_app.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).homeViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getPopularRecipes()
        preparePopularRecipeRecyclerView()
        observePopularItems()
        onPopularRecipeClick()

        homeViewModel.getCategories()
        observeCategories()
        prepareCategoryRecyclerView()
        onCategoryClick()

        binding.searchView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

    }


    private fun onCategoryClick() {
        categoryListAdapter.onItemClick = { category ->
            val intent = Intent(activity, RecipeByCategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }


    private fun onPopularRecipeClick() {
        popularRecipeAdapter.onItemClick = { meal ->
            val intent = Intent(activity, RecipeDetailActivity::class.java)
            intent.putExtra(RECIPE_ID, meal.idMeal)
            intent.putExtra(RECIPE_NAME, meal.strMeal)
            intent.putExtra(RECIPE_THUMB, meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun preparePopularRecipeRecyclerView() {
        popularRecipeAdapter = PopularRecipesListAdapter()
        binding.editorRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularRecipeAdapter
        }
    }

    private fun prepareCategoryRecyclerView() {
        categoryListAdapter = CategoryListAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryListAdapter
        }
    }


    private fun observePopularItems() {
        homeViewModel.observePopularRecipesLiveData().observe(viewLifecycleOwner,
            { meals ->
                popularRecipeAdapter.setPopularRecipes(popularRecipeList = meals as ArrayList<RecipeByCategory>)

            })
    }

    private fun observeCategories() {
        homeViewModel.observeCategoriesLiveData()
            .observe(viewLifecycleOwner, Observer { categories ->
                categoryListAdapter.setCategory(categories)
            })
    }


}