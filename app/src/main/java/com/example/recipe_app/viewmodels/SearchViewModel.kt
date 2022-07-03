package com.example.recipe_app.viewmodels

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe_app.pojo.RecipeDetail
import com.example.recipe_app.pojo.RecipeDetailList
import com.example.recipe_app.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private var searchedMealLiveData = MutableLiveData<RecipeDetail>()


    fun searchMealDetail(name: String, context: Context?) {
        RetrofitInstance.api.getMealByName(name)
            .enqueue(object : Callback<RecipeDetailList> {
                override fun onResponse(
                    call: Call<RecipeDetailList>,
                    response: Response<RecipeDetailList>,
                ) {
                    if (response.body()?.meals == null)
                        Toast.makeText(context?.applicationContext,
                            "No such a meal",
                            Toast.LENGTH_SHORT).show()
                    else
                        searchedMealLiveData.value = response.body()!!.meals[0]
                }

                override fun onFailure(call: Call<RecipeDetailList>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                }

            })
    }

    fun observeSearchLiveData(): LiveData<RecipeDetail> {
        return searchedMealLiveData
    }
}