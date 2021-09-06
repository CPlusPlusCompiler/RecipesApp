package lt.ito.components.base

import lt.ito.components.recipe.RecipeContract
import lt.ito.models.Dish
import lt.ito.models.Recipe

abstract class BaseRecipePresenter: BasePresenter<RecipeContract>() {

    abstract fun load(recipe: Recipe)

    abstract fun saveAttempt(dish: Dish)
}