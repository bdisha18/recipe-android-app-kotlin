package com.example.recipe_app.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.db.RecipeDatabase
import com.example.recipe_app.pojo.RecipeDetail
import com.example.recipe_app.pojo.RecipeDetailList
import com.example.recipe_app.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel(private val recipeDatabase: RecipeDatabase) : ViewModel() {
    private var recipeDetailsLiveData = MutableLiveData<RecipeDetail>()

    fun getRecipeDetail(id: String) {
        RetrofitInstance.api.getRecipeDetail(id).enqueue(object : Callback<RecipeDetailList> {
            override fun onResponse(
                call: Call<RecipeDetailList>,
                response: Response<RecipeDetailList>
            ) {
                if (response.body() != null) {
                    recipeDetailsLiveData.value = response.body()!!.meals[0]
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<RecipeDetailList>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })
    }

    fun observeRecipeDetailsLiveData(): LiveData<RecipeDetail> {
        return recipeDetailsLiveData
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


}



