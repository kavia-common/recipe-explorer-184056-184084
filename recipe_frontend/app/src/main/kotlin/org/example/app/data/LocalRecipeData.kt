package org.example.app.data

import org.example.app.model.Recipe

/**
 * Local seed data for recipes to be used until a backend API is available.
 * Designed so it can be replaced with a remote data source in future.
 */
object LocalRecipeData {
    fun seed(): List<Recipe> {
        return listOf(
            Recipe(
                id = "1",
                title = "Lemon Herb Grilled Chicken",
                description = "Juicy grilled chicken with lemon and fresh herbs.",
                imageResName = "img_chicken",
                ingredients = listOf(
                    "2 chicken breasts",
                    "1 lemon (juice and zest)",
                    "2 tbsp olive oil",
                    "1 tsp dried oregano",
                    "1 tsp dried thyme",
                    "Salt & pepper"
                ),
                steps = listOf(
                    "Mix lemon juice, zest, olive oil, oregano, thyme, salt & pepper.",
                    "Marinate chicken for 20 minutes.",
                    "Grill 6-7 minutes each side until cooked through.",
                    "Rest for 5 minutes before slicing."
                ),
                timeMinutes = 30
            ),
            Recipe(
                id = "2",
                title = "Creamy Mushroom Pasta",
                description = "Rich and creamy pasta with sautéed mushrooms.",
                imageResName = "img_pasta",
                ingredients = listOf(
                    "200g pasta",
                    "200g mushrooms, sliced",
                    "1 cup cream",
                    "2 cloves garlic",
                    "2 tbsp butter",
                    "Parmesan, salt & pepper"
                ),
                steps = listOf(
                    "Cook pasta according to package instructions.",
                    "Sauté mushrooms in butter until browned.",
                    "Add minced garlic, then cream; simmer until slightly thick.",
                    "Toss pasta with sauce and top with Parmesan."
                ),
                timeMinutes = 25
            ),
            Recipe(
                id = "3",
                title = "Avocado Toast Deluxe",
                description = "Crispy toast topped with creamy avocado and eggs.",
                imageResName = "img_toast",
                ingredients = listOf(
                    "2 slices sourdough",
                    "1 ripe avocado",
                    "1 tsp lemon juice",
                    "Chili flakes",
                    "2 eggs",
                    "Salt & pepper"
                ),
                steps = listOf(
                    "Toast the bread slices.",
                    "Mash avocado with lemon juice, salt & pepper.",
                    "Spread on toast, top with fried or poached egg.",
                    "Sprinkle chili flakes."
                ),
                timeMinutes = 10
            )
        )
    }
}
