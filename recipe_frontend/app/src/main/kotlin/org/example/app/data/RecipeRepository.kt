package org.example.app.data

import android.content.Context
import org.example.app.model.Recipe

/**
 * PUBLIC_INTERFACE
 * Repository that provides recipe data and manages favorites.
 * Uses local in-memory list seeded from LocalRecipeData and SharedPreferences for favorites.
 */
class RecipeRepository private constructor(private val context: Context) {

    private val recipes: List<Recipe> = LocalRecipeData.seed()
    private val prefs by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getAllRecipes(): List<Recipe> = recipes

    fun search(query: String): List<Recipe> {
        if (query.isBlank()) return recipes
        val q = query.trim().lowercase()
        return recipes.filter { recipe ->
            recipe.title.lowercase().contains(q) ||
                    recipe.description.lowercase().contains(q) ||
                    recipe.ingredients.any { it.lowercase().contains(q) }
        }
    }

    fun getById(id: String): Recipe? = recipes.find { it.id == id }

    fun isFavorite(id: String): Boolean {
        val set = prefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
        return set.contains(id)
    }

    fun toggleFavorite(id: String): Boolean {
        val current = prefs.getStringSet(KEY_FAVORITES, emptySet())?.toMutableSet() ?: mutableSetOf()
        val nowFav: Boolean
        if (current.contains(id)) {
            current.remove(id)
            nowFav = false
        } else {
            current.add(id)
            nowFav = true
        }
        prefs.edit().putStringSet(KEY_FAVORITES, current).apply()
        return nowFav
    }

    fun getFavorites(): List<Recipe> {
        val favIds = prefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
        return recipes.filter { favIds.contains(it.id) }
    }

    companion object {
        private const val PREFS_NAME = "recipe_prefs"
        private const val KEY_FAVORITES = "favorite_ids"

        @Volatile private var INSTANCE: RecipeRepository? = null

        // PUBLIC_INTERFACE
        fun getInstance(context: Context): RecipeRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: RecipeRepository(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
