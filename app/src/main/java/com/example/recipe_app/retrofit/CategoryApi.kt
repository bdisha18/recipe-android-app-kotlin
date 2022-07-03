package com.example.recipe_app.retrofit

import com.example.recipe_app.pojo.AreaList
import com.example.recipe_app.pojo.CategoryList
import com.example.recipe_app.pojo.RecipeByCategoryList
import com.example.recipe_app.pojo.RecipeDetailList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApi {

    @GET("categories.php")
    fun getCategory(): Call<CategoryList>

    @GET("filter.php?")
    fun getPopularRecipes(@Query("c") categoryname: String): Call<RecipeByCategoryList>

    @GET("list.php")
    fun getArea(@Query("a") areaName: String): Call<AreaList>

    @GET("filter.php")
    fun getRecipeByArea(@Query("a") areaName: String): Call<RecipeByCategoryList>

    @GET("filter.php")
    fun getRecipeByCategory(@Query("c") categoryName: String): Call<RecipeByCategoryList>

    @GET("lookup.php?")
    fun getRecipeDetail(@Query("i") id: String): Call<RecipeDetailList>

    @GET("search.php?")
    fun getMealByName(@Query("s") s: String): Call<RecipeDetailList>

}