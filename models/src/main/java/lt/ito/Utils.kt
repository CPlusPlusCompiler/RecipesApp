package lt.ito

import lt.ito.models.Difficulty
import lt.ito.models.Dish

object Utils {

    /** Returns user's skill level based on [lastAttemptsToUse]  */
    fun getSkillLevel(lastAttemptsToUse: Int, triedDishes: List<Dish>): Difficulty {
        val scores = hashMapOf<Difficulty, Int>(
            Difficulty.EASY to 0,
            Difficulty.NORMAL to 0,
            Difficulty.HARD to 0
        )

        val lastDishes = triedDishes.takeLast(lastAttemptsToUse)

        for (dish in lastDishes) {
            val currentScore = scores[dish.recipe.difficulty]!!
            scores[dish.recipe.difficulty] = currentScore + dish.result.value
        }

        return scores.entries.maxBy { it.value }!!.key
    }
}