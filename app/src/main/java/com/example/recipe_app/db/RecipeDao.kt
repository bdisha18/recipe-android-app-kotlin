package com.example.recipe_app.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipe_app.pojo.RecipeDetail

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(recipe: RecipeDetail)

    @Delete
    suspend fun delete(recipe: RecipeDetail)

    @Query("SELECT * FROM recipeInformation")
    fun getAllRecipe(): LiveData<List<RecipeDetail>>
}