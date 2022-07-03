package com.example.recipe_app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe_app.MainActivity
import com.example.recipe_app.RecipeDetailActivity
import com.example.recipe_app.adapter.AreaListAdapter
import com.example.recipe_app.adapter.RecipeByCategoryAdapter
import com.example.recipe_app.databinding.FragmentCategoryBinding
import com.example.recipe_app.pojo.RecipeDetail
import com.example.recipe_app.viewmodels.CategoryViewModel
import com.example.recipe_app.viewmodels.HomeViewModel


class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var recipeAdapter: RecipeByCategoryAdapter
    private lateinit var areaListAdapter: AreaListAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var categoryViewModel: CategoryViewModel

    companion object {
        const val RECIPE_ID = "com.example.recipe_app.fragments.idMeal"
        const val RECIPE_NAME = "com.example.recipe_app.fragments.nameMeal"
        const val RECIPE_THUMB = "com.example.recipe_app.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).homeViewModel
        recipeAdapter = RecipeByCategoryAdapter()
        areaListAdapter = AreaListAdapter()

        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryViewModel.getAreas()
        observeAreas()
        prepareAreasRecyclerView()

        observeRecipes()
        prepareRecipeByCategoryRecyclerView()
        //Click Function
        onAreaClick()
        onRecipeByCategoryClick()
//        onRecipeByCategoryFavoriteClick()

    }


//    private fun onRecipeByCategoryFavoriteClick() {
//        recipeAdapter.onItemClick = {
//            recipeToSave?.let {
//                viewModel.insertRecipe(it)
//                Toast.makeText(this@CategoryFragment.requireActivity(),
//                    "Recipe Saved",
//                    Toast.LENGTH_SHORT).show()
//            }
//
//        }
//    }

//    private var recipeToSave: RecipeDetail? = null

    private fun onRecipeByCategoryClick() {
        recipeAdapter.onItemClick = { recipeByCategory ->
            val intent = Intent(activity, RecipeDetailActivity::class.java)
            intent.putExtra(RECIPE_ID, recipeByCategory.idMeal)
            intent.putExtra(RECIPE_NAME, recipeByCategory.strMeal)
            intent.putExtra(RECIPE_THUMB, recipeByCategory.strMealThumb)
            startActivity(intent)

        }
    }

    private fun onAreaClick() {
        areaListAdapter.onItemClick = { area ->
            categoryViewModel.getRecipeByArea(area.strArea)
        }
    }

    private fun prepareRecipeByCategoryRecyclerView() {
        recipeAdapter = RecipeByCategoryAdapter()
        binding.recipeRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = recipeAdapter
        }
    }

    private fun prepareAreasRecyclerView() {
        areaListAdapter = AreaListAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = areaListAdapter
        }
    }


    private fun observeRecipes() {
        categoryViewModel.observeRecipeByCategoryLiveData()
            .observe(viewLifecycleOwner, Observer { recipeList ->
                recipeAdapter.setRecipesByCategory(recipeList)

            })
    }

    private fun observeAreas() {
        categoryViewModel.observeAreaLiveData().observe(viewLifecycleOwner, Observer { areaList ->
            areaListAdapter.setArea(areaList)

        })
    }
}


