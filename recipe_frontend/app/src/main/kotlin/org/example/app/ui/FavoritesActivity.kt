package org.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.app.data.RecipeRepository
import org.example.app.model.Recipe
import org.example.app.ui.RecipeDetailActivity
import org.example.app.ui.RecipeListAdapter

/**
 * PUBLIC_INTERFACE
 * Shows list of user's favorite recipes with ability to unmark favorites.
 */
class FavoritesActivity : AppCompatActivity() {

    private lateinit var repo: RecipeRepository
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: RecipeListAdapter
    private lateinit var empty: TextView
    private lateinit var homeNav: LinearLayout
    private lateinit var favoritesNav: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        repo = RecipeRepository.getInstance(this)

        empty = findViewById(R.id.empty_state_fav)
        recycler = findViewById(R.id.recycler_favorites)
        homeNav = findViewById(R.id.nav_home)
        favoritesNav = findViewById(R.id.nav_favorites)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = RecipeListAdapter(
            this,
            items = repo.getFavorites(),
            onClick = { openDetails(it) },
            onToggleFav = {
                repo.toggleFavorite(it.id)
                refresh()
            }
        )
        recycler.adapter = adapter
        updateEmpty()

        homeNav.setOnClickListener {
            finish()
        }
        favoritesNav.setOnClickListener {
            // already here
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        adapter.update(repo.getFavorites())
        updateEmpty()
    }

    private fun updateEmpty() {
        empty.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
    }

    private fun openDetails(recipe: Recipe) {
        val intent = Intent(this, RecipeDetailActivity::class.java)
        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id)
        startActivity(intent)
    }
}
