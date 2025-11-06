package org.example.app.ui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.example.app.R
import org.example.app.data.RecipeRepository

/**
 * PUBLIC_INTERFACE
 * Displays detailed information about a recipe: image, time, ingredients, and steps.
 * Allows toggling favorite status.
 */
class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var repo: RecipeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        repo = RecipeRepository.getInstance(this)

        val id = intent.getStringExtra(EXTRA_RECIPE_ID) ?: return finish()
        val recipe = repo.getById(id) ?: return finish()

        val image: ImageView = findViewById(R.id.detail_image)
        val resId = resources.getIdentifier(recipe.imageResName, "drawable", packageName)
        if (resId != 0) image.setImageResource(resId) else image.setImageResource(R.drawable.ocean_placeholder)

        findViewById<TextView>(R.id.detail_title).text = recipe.title
        findViewById<TextView>(R.id.detail_desc).text = recipe.description
        findViewById<TextView>(R.id.detail_time).text = getString(R.string.time_minutes_format, recipe.timeMinutes)

        val favBtn: ImageButton = findViewById(R.id.detail_fav)
        setFavIcon(favBtn, repo.isFavorite(recipe.id))
        favBtn.setOnClickListener {
            val nowFav = repo.toggleFavorite(recipe.id)
            setFavIcon(favBtn, nowFav)
        }

        val ingContainer: LinearLayout = findViewById(R.id.ingredients_container)
        recipe.ingredients.forEach { addBullet(ingContainer, it) }

        val stepsContainer: LinearLayout = findViewById(R.id.steps_container)
        recipe.steps.forEachIndexed { index, step -> addStep(stepsContainer, index + 1, step) }

        findViewById<ImageButton>(R.id.btn_back).setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun setFavIcon(btn: ImageButton, isFav: Boolean) {
        btn.setImageResource(if (isFav) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline)
        btn.contentDescription = if (isFav) getString(R.string.cd_unfavorite) else getString(R.string.cd_favorite)
    }

    private fun addBullet(container: LinearLayout, text: String) {
        val tv = layoutInflater.inflate(R.layout.part_bullet_text, container, false) as TextView
        tv.text = text
        container.addView(tv)
    }

    private fun addStep(container: LinearLayout, index: Int, text: String) {
        val stepView = layoutInflater.inflate(R.layout.part_step_text, container, false)
        val number = stepView.findViewById<TextView>(R.id.step_number)
        val body = stepView.findViewById<TextView>(R.id.step_text)
        number.text = index.toString()
        body.text = text
        container.addView(stepView)
    }

    companion object {
        const val EXTRA_RECIPE_ID = "extra_recipe_id"
    }
}
