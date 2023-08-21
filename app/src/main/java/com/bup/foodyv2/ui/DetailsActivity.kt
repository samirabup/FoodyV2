package com.bup.foodyv2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.bup.foodyv2.R
import com.bup.foodyv2.adapters.PagerAdapter
import com.bup.foodyv2.data.database.entities.FavoritesEntity
import com.bup.foodyv2.databinding.ActivityDetailsBinding
import com.bup.foodyv2.ui.fragments.ingredients.IngredientsFragment
import com.bup.foodyv2.ui.fragments.instructions.InstructionsFragment
import com.bup.foodyv2.ui.fragments.overview.OverviewFragment
import com.bup.foodyv2.util.Constants.Companion.RECIPE_RESULT_KEY
import com.bup.foodyv2.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var recipeSaved = false
    private var savedRecipeId = 0

    private lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu!!.findItem(R.id.save_to_favorites_menu)
        checkSavedRecipes(menuItem)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favorites_menu && !recipeSaved) {
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_to_favorites_menu && recipeSaved) {
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this) { favoritesEntity ->
            try {
                for (savedRecipe in favoritesEntity) {
                    if (savedRecipe.result.id == args.result.id) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    } else {
                        changeMenuItemColor(menuItem, R.color.white)
                    }
                }
            } catch (e: Exception) {
                Log.d("Samira", e.message.toString())
            }
        }
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            id = 0,
            args.result
        )
        mainViewModel.insertFavoriteRecipes(favoritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnakeBar("Recipe saved.")
        recipeSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            savedRecipeId,
            args.result
        )
        mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnakeBar("Removed from Favorites.")
        recipeSaved = false
    }

    private fun showSnakeBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }
}