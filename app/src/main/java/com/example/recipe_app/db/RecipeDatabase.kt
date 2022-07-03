package com.example.recipe_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipe_app.pojo.RecipeDetail

@Database(entities = [RecipeDetail::class], version = 1, exportSchema = false)
@TypeConverters(RecipeTypeConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        var INSTANCE: RecipeDatabase? = null

        @Synchronized
        fun getInstance(context: Context): RecipeDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    "recipe.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as RecipeDatabase
        }
    }
}