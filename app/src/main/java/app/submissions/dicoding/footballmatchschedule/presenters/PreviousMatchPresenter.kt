package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.constants.League
import app.submissions.dicoding.footballmatchschedule.idlingresource.EspressoIdlingResource
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.models.holders.MatchNewsHolder
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.PreviousMatchBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.LeagueSchedule
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

class PreviousMatchPresenter(private val behavior: PreviousMatchBehavior,
                             private val mainScheduler: Scheduler,
                             private val backgroundScheduler: Scheduler) {
  private var disposable: Disposable? = null

  fun dispose() {
    disposable?.dispose()
  }

  fun getData() {
    behavior.showLoading()
    disposable = LeagueSchedule.Request.get.past15(League.ENGLISH)
        .subscribeOn(backgroundScheduler)
        .observeOn(mainScheduler)
        .subscribe(
            { behavior.showData(mapEventsToMatchNewsHolder(it.events)) },
            { behavior.onError("An error occured!\n${it.message}") },
            { behavior.hideLoading() })
  }

  companion object {
    fun mapEventsToMatchNewsHolder(data: List<Event>): List<MatchNewsHolder> =
        data
            .asSequence()
            .groupBy { it.dateEvent }
            .map { MatchNewsHolder(it.value[0].getFormattedDate(), it.value) }
            .toList()
  }

}