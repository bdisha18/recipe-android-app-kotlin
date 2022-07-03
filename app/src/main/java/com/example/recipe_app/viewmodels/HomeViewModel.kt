package com.example.recipe_app.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.db.RecipeDatabase
import com.example.recipe_app.pojo.*
import com.example.recipe_app.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(private val recipeDatabase: RecipeDatabase) : ViewModel() {

    private var popularRecipes: MutableLiveData<List<RecipeByCategory>> =
        MutableLiveData<List<RecipeByCategory>>()
    private var categories: MutableLiveData<List<Category>> = MutableLiveData<List<Category>>()
    private var favorites = recipeDatabase.recipeDao().getAllRecipe()


    fun getPopularRecipes() {
        RetrofitInstance.api.getPopularRecipes("seafood")
            .enqueue(object : Callback<RecipeByCategoryList> {
                override fun onResponse(
                    call: Call<RecipeByCategoryList>,
                    response: Response<RecipeByCategoryList>
                ) {
                    if (response.body() != null) {
                        popularRecipes.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<RecipeByCategoryList>, t: Throwable) {
                    Log.d(TAG, t.message.toString())
                }

            })
    }

    fun getCategories() {
        RetrofitInstance.api.getCategory().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    categories.value = response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

        })
    }

    fun observePopularRecipesLiveData(): LiveData<List<RecipeByCategory>> {
        return popularRecipes
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categories
    }

    fun insertRecipe(recipe: RecipeDetail) {
        viewModelScope.launch {
            recipeDatabase.recipeDao().upsert(recipe)
        }
    }

    fun deleteRecipe(recipe: RecipeDetail) {
        viewModelScope.launch {
            recipeDatabase.recipeDao().delete(recipe)
        }
    }

    fun observeFavoritesRecipeLiveData(): LiveData<List<RecipeDetail>> {
        return favorites
    }
}