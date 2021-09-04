package lt.ito.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    val id: String,
    val title: String,
    val imageUrl: String,
    val text: String,
    override val difficulty: Difficulty
): RecipeListItem(difficulty), Parcelable