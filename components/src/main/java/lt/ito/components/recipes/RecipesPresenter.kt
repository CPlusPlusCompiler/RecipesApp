package lt.ito.components.recipes

import lt.ito.components.base.BasePresenter
import lt.ito.components.respositories.RecipesRepository
import lt.ito.components.respositories.RecommendationRepository
import lt.ito.components.schedulers.SchedulerProvider
import lt.ito.components.test.IdlingResourceCountable
import javax.inject.Inject


class RecipesPresenter @Inject constructor(
    private val idlingResourceCountable: IdlingResourceCountable,
    private val recipesRepository: RecipesRepository,
    private val schedulerProvider: SchedulerProvider,
    private val recommendationRepository: RecommendationRepository
) : BasePresenter<RecipesContract>() {

    fun onAttach() {
        loadData()
    }

    private fun loadData() {
        subscriptions.add(
            recipesRepository.getRecipes()
                .subscribeOn(schedulerProvider.io())
                .doOnSubscribe { idlingResourceCountable.increase() }
                .doFinally { idlingResourceCountable.decrease() }
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    { view?.updateDataList(it) },
                    { view?.showError(it) })
        )
    }

    fun onLoadRecommendation() {
        subscriptions.add(
            recommendationRepository.getRecommendation()
                .subscribeOn(schedulerProvider.io())
                .doOnSubscribe { view?.showProgress() }
                .observeOn(schedulerProvider.ui())
                .subscribe({ recipe ->
                    view?.hideProgress()
                    view?.showRecommendation(recipe)
                }, {
                    it.printStackTrace()
                })
        )
    }
}
