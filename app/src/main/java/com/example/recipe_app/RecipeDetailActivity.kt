package com.example.recipe_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recipe_app.databinding.ActivityRecipeDetailBinding
import com.example.recipe_app.db.RecipeDatabase
import com.example.recipe_app.fragments.HomeFragment
import com.example.recipe_app.pojo.RecipeDetail
import com.example.recipe_app.viewmodels.HomeViewModel
import com.example.recipe_app.viewmodels.RecipeViewModel
import com.example.recipe_app.viewmodels.RecipeViewModelFactory

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var recipeId: String
    private lateinit var recipeName: String
    private lateinit var recipeThumb: String

    private lateinit var youtubeLink: String

    private lateinit var binding: ActivityRecipeDetailBinding
    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.youtubeBtn.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red)


//        recipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        val recipeDatabase = RecipeDatabase.getInstance(this)
        val viewModelFactory = RecipeViewModelFactory(recipeDatabase)
        recipeViewModel = ViewModelProvider(this, viewModelFactory)[RecipeViewModel::class.java]


        getRecipeDetailFromIntent()
        setRecipeDetail()

        onLoadingCases()
        recipeViewModel.getRecipeDetail(recipeId)
        observeRecipeDetails()

        onFavoriteClick()
        onYoutubeBtnClick()


    }

    private fun onYoutubeBtnClick() {
        binding.youtubeBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }


    private fun onFavoriteClick() {
        binding.favoriteBtn.setOnClickListener {
            recipeToSave?.let {
                recipeViewModel.insertRecipe(it)
                Toast.makeText(this, "Recipe Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private var recipeToSave: RecipeDetail? = null

    private fun observeRecipeDetails() {
        recipeViewModel.observeRecipeDetailsLiveData()
            .observe(this, object : Observer<RecipeDetail> {
                override fun onChanged(t: RecipeDetail?) {
                    onResponseCase()
                    val meal = t
                    recipeToSave = meal
                    binding.apply {
                        category.text = "${meal!!.strCategory}"
                        instruction.text = meal.strInstructions
                        instruction.movementMethod = ScrollingMovementMethod() //Scrollbar
                        area.text = meal.strArea
                        youtubeLink = meal.strYoutube.toString()
                    }

                }
            })
    }


    private fun setRecipeDetail() {
        Glide.with(applicationContext)
            .load(recipeThumb)
            .into(binding.recipeImg)

        binding.recipeName.text = recipeName
    }

    private fun getRecipeDetailFromIntent() {
        val intent = intent
        recipeId = intent.getStringExtra(HomeFragment.RECIPE_ID)!!
        recipeName = intent.getStringExtra(HomeFragment.RECIPE_NAME)!!
        recipeThumb = intent.getStringExtra(HomeFragment.RECIPE_THUMB)!!
    }

    private fun onLoadingCases() {
        binding.favoriteBtn.visibility = View.INVISIBLE
        binding.instruction.visibility = View.INVISIBLE
        binding.category.visibility = View.INVISIBLE
        binding.area.visibility = View.INVISIBLE
        binding.youtubeBtn.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        binding.favoriteBtn.visibility = View.VISIBLE
        binding.instruction.visibility = View.VISIBLE
        binding.category.visibility = View.VISIBLE
        binding.area.visibility = View.VISIBLE
        binding.youtubeBtn.visibility = View.VISIBLE
    }
}