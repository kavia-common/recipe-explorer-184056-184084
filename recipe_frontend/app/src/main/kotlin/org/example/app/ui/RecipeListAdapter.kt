package org.example.app.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.data.RecipeRepository
import org.example.app.model.Recipe

/**
 * Adapter for displaying a list of recipes with ocean-professional styling.
 */
class RecipeListAdapter(
    private val context: Context,
    private var items: List<Recipe>,
    private val onClick: (Recipe) -> Unit,
    private val onToggleFav: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeListAdapter.VH>() {

    private val repo = RecipeRepository.getInstance(context)

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.item_image)
        val title: TextView = view.findViewById(R.id.item_title)
        val desc: TextView = view.findViewById(R.id.item_desc)
        val fav: ImageButton = view.findViewById(R.id.item_fav)
        val card: View = view.findViewById(R.id.item_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val r = items[position]
        val resId = context.resources.getIdentifier(r.imageResName, "drawable", context.packageName)
        if (resId != 0) holder.img.setImageResource(resId) else holder.img.setImageResource(R.drawable.ocean_placeholder)

        holder.title.text = r.title
        holder.desc.text = r.description
        val isFav = repo.isFavorite(r.id)
        holder.fav.setImageResource(if (isFav) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline)

        holder.card.setOnClickListener { onClick(r) }
        holder.fav.setOnClickListener {
            onToggleFav(r)
            notifyItemChanged(position)
        }
    }

    fun update(newItems: List<Recipe>) {
        items = newItems
        notifyDataSetChanged()
    }
}
