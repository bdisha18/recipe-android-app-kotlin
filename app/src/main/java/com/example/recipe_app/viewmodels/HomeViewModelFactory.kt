package com.example.recipe_app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.db.RecipeDatabase

class HomeViewModelFactory(private val recipeDatabase: RecipeDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(recipeDatabase) as T
    }
}