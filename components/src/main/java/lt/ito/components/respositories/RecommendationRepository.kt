package lt.ito.components.respositories

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import lt.ito.AppFileUtils
import lt.ito.Utils
import lt.ito.components.R
import lt.ito.models.Difficulty
import lt.ito.models.Recipe
import javax.inject.Inject

@SuppressLint("CheckResult")
class RecommendationRepository @Inject constructor(
    private val dishesRepository: DishesRepository,
    private val recipesRepository: RecipesRepository,
    private val appFileUtils: AppFileUtils
) {

    fun getRecommendation(): Observable<Recipe> {
        return Observable.create<Recipe> { subscriber ->
            dishesRepository.getDishes()
                .subscribe({ triedDishes ->

                    if (triedDishes.isEmpty()) {
                        recipesRepository.getRecipesByDifficulty(Difficulty.EASY)
                            .subscribe({ recipes ->
                                onLoadRecipes(subscriber, recipes)
                            }, {
                                it.printStackTrace()
                            })
                    } else {
                        val skillLevel = Utils.getSkillLevel(5, triedDishes)
                        recipesRepository.getRecipesByDifficulty(skillLevel)
                            .subscribe({ recipes ->
                                onLoadRecipes(subscriber, recipes)
                            }, {
                                it.printStackTrace()
                            })
                    }
                }, {
                    it.printStackTrace()
                })
        }
    }

    /** Helper function to make things more readable */
    private fun onLoadRecipes(
        subscriber: ObservableEmitter<Recipe>,
        recipes: List<Recipe>
    ) {
        if (recipes.isEmpty()) {
            subscriber.onError(
                Throwable(
                    appFileUtils.getRawText(R.string.error_no_recommendations)
                )
            )
        } else {
            val random = recipes.random()
            subscriber.onNext(random)
            subscriber.onComplete()
        }
    }
}