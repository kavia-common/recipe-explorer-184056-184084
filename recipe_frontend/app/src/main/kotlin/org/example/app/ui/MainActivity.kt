package org.example.app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
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
 * Main activity showing a searchable list of recipes and a bottom navigation
 * to Favorites. Adheres to Ocean Professional visual style.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var repo: RecipeRepository
    private lateinit var searchInput: EditText
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: RecipeListAdapter
    private lateinit var favoritesNav: LinearLayout
    private lateinit var homeNav: LinearLayout
    private lateinit var emptyState: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        repo = RecipeRepository.getInstance(this)

        searchInput = findViewById(R.id.search_input)
        recycler = findViewById(R.id.recycler_recipes)
        favoritesNav = findViewById(R.id.nav_favorites)
        homeNav = findViewById(R.id.nav_home)
        emptyState = findViewById(R.id.empty_state)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = RecipeListAdapter(
            context = this,
            items = repo.getAllRecipes(),
            onClick = { openDetails(it) },
            onToggleFav = { repo.toggleFavorite(it.id) }
        )
        recycler.adapter = adapter
        updateEmptyState(adapter.itemCount == 0)

        favoritesNav.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
        homeNav.setOnClickListener {
            // No-op, already on home
        }

        findViewById<ImageButton>(R.id.btn_clear).setOnClickListener {
            searchInput.setText("")
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performSearch(s?.toString().orEmpty())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun performSearch(query: String) {
        val results = repo.search(query)
        adapter.update(results)
        updateEmptyState(results.isEmpty())
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun openDetails(recipe: Recipe) {
        val intent = Intent(this, RecipeDetailActivity::class.java)
        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id)
        startActivity(intent)
    }
}
