package com.example.recipe_app.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe_app.pojo.Area
import com.example.recipe_app.pojo.AreaList
import com.example.recipe_app.pojo.RecipeByCategory
import com.example.recipe_app.pojo.RecipeByCategoryList
import com.example.recipe_app.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel() : ViewModel() {

    private var recipes: MutableLiveData<List<RecipeByCategory>> =
        MutableLiveData<List<RecipeByCategory>>()
    private var areas: MutableLiveData<List<Area>> = MutableLiveData<List<Area>>()


    fun getAreas() {
        RetrofitInstance.api.getArea("list").enqueue(object : Callback<AreaList> {
            override fun onResponse(call: Call<AreaList>, response: Response<AreaList>) {
                if (response.body() != null) {
                    areas.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<AreaList>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

        })
    }


    fun getRecipeByCategory(categoryName: String) {
        RetrofitInstance.api.getRecipeByCategory(categoryName)
            .enqueue(object : Callback<RecipeByCategoryList> {

                override fun onResponse(
                    call: Call<RecipeByCategoryList>,
                    response: Response<RecipeByCategoryList>,
                ) {
                    response.body()?.let { mealsList ->
                        recipes.postValue(mealsList.meals)

                    }
                }


                override fun onFailure(call: Call<RecipeByCategoryList>, t: Throwable) {
                    Log.d("Category Fragment", t.message.toString())
                }

            })
    }

    fun getRecipeByArea(areaName: String) {
        RetrofitInstance.api.getRecipeByArea(areaName)
            .enqueue(object : Callback<RecipeByCategoryList> {

                override fun onResponse(
                    call: Call<RecipeByCategoryList>,
                    response: Response<RecipeByCategoryList>,
                ) {
                    if (response.body() != null) {
                        recipes.value = response.body()!!.meals
                    }
                }


                override fun onFailure(call: Call<RecipeByCategoryList>, t: Throwable) {
                    Log.d("Category Fragment", t.message.toString())
                }

            })
    }

    fun observeRecipeByCategoryLiveData(): LiveData<List<RecipeByCategory>> {
        return recipes
    }

    fun observeAreaLiveData(): LiveData<List<Area>> {
        return areas
    }




}