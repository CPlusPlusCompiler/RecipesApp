package lt.ito.mycooking.recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import it.ito.mycooking.R
import kotlinx.android.synthetic.main.item_recipe.view.*
import kotlinx.android.synthetic.main.item_recipe.view.titleView
import kotlinx.android.synthetic.main.item_recipe_section_header.view.*
import lt.ito.components.base.OnItemClickListener
import lt.ito.models.Recipe
import lt.ito.models.RecipeListItem
import lt.ito.models.color
import lt.ito.mycooking.utils.stringResId

class RecipesAdapter(
    private val onItemClickListener: OnItemClickListener<Recipe>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val recipesList: MutableList<RecipeListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RecipeViewType.Recipe.ordinal ->
                RecipesHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_recipe, parent, false)
                )
            else ->
                HeaderHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_recipe_section_header, parent, false)
                )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            recipesList[position] is Recipe ->
                RecipeViewType.Recipe.ordinal
            else ->
                RecipeViewType.SectionHeader.ordinal
        }
    }

    override fun getItemCount(): Int = recipesList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataItem = recipesList[position]

        when (holder) {
            is RecipesHolder -> {
                dataItem as Recipe
//                holder.imageView.setImageURI(recipesList[if(position == 8) 7 else if(position == 7) 8 else position].imageUrl)
                holder.imageView.setImageURI(dataItem.imageUrl)
                holder.titleView.text = dataItem.title
                holder.difficultyView.setText(dataItem.difficulty.stringResId)
                holder.difficultyView.setTextColor(dataItem.difficulty.color)
                holder.itemView.setOnClickListener {
                    onItemClickListener.onClick(
                        position,
                        dataItem
                    )
                }
            }

            is HeaderHolder -> {
                holder.titleView.setText(dataItem.difficulty.stringResId)
            }
        }
    }

    fun setValues(newRecipes: List<Recipe>) {
        this.recipesList.clear()

        val sortedRecipes = newRecipes.sortedBy {
            it.difficulty
        }

        for (i in sortedRecipes.indices) {
            if (i == 0) {
                recipesList.add(RecipeListItem(sortedRecipes[0].difficulty))
                recipesList.add(sortedRecipes[0])
                continue
            }

            val currentItem = sortedRecipes[i]
            val nextItem = sortedRecipes.getOrNull(i + 1)

            if (nextItem == null) {
                recipesList.add(currentItem)
                break
            }

            recipesList.add(currentItem)

            if (currentItem.difficulty != nextItem.difficulty)
                recipesList.add(RecipeListItem(nextItem.difficulty))
        }

        notifyDataSetChanged()
    }

    inner class RecipesHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var imageView: SimpleDraweeView = v.imageView
        internal var titleView: TextView = v.titleView
        internal var difficultyView: TextView = v.difficultyView
    }

    inner class HeaderHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var titleView: TextView = v.difficultyTitleView
    }

    enum class RecipeViewType {
        Recipe,
        SectionHeader
    }
}