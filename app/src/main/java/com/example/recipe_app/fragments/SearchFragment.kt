package com.example.recipe_app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recipe_app.RecipeDetailActivity
import com.example.recipe_app.databinding.FragmentSearchBinding
import com.example.recipe_app.pojo.RecipeDetail
import com.example.recipe_app.viewmodels.SearchViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchMvvm: SearchViewModel
    private var mealId = ""
    private var mealStr = ""
    private var mealThumb = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchMvvm = ViewModelProvider(this)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onSearchClick()
        observeSearchLiveData()
        setOnMealCardClick()
    }

    private fun setOnMealCardClick() {
        binding.searchedMealCard.setOnClickListener {
            val intent = Intent(context, RecipeDetailActivity::class.java)

            intent.putExtra(HomeFragment.RECIPE_ID, mealId)
            intent.putExtra(HomeFragment.RECIPE_NAME, mealStr)
            intent.putExtra(HomeFragment.RECIPE_THUMB, mealThumb)

            startActivity(intent)


        }
    }

    private fun onSearchClick() {
        binding.icSearch.setOnClickListener {
            searchMvvm.searchMealDetail(binding.edSearch.text.toString(), context)

        }
    }

    private fun observeSearchLiveData() {
        searchMvvm.observeSearchLiveData()
            .observe(viewLifecycleOwner, object : Observer<RecipeDetail> {
                override fun onChanged(t: RecipeDetail?) {
                    if (t == null) {
                        Toast.makeText(context, "No such a meal", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.apply {

                            mealId = t.idMeal
                            mealStr = t.strMeal.toString()
                            mealThumb = t.strMealThumb.toString()

                            Glide.with(requireContext().applicationContext)
                                .load(t.strMealThumb)
                                .into(imgSearchedMeal)

                            tvSearchedMeal.text = t.strMeal
                            searchedMealCard.visibility = View.VISIBLE
                        }
                    }
                }
            })
    }

}