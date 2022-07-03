package com.example.recipe_app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.db.RecipeDatabase

class RecipeViewModelFactory(private val recipeDatabase: RecipeDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeViewModel(recipeDatabase) as T
    }
}