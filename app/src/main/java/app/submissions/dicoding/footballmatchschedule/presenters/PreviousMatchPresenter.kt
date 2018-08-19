package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.constants.League
import app.submissions.dicoding.footballmatchschedule.exts.handleSafely
import app.submissions.dicoding.footballmatchschedule.models.Events
import app.submissions.dicoding.footballmatchschedule.models.holders.MatchNewsHolder
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.PreviousMatchBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.LeagueSchedule
import io.reactivex.disposables.Disposable

class PreviousMatchPresenter(private val behavior: PreviousMatchBehavior) {
  private var disposable: Disposable? = null

  fun dispose() {
    disposable?.dispose()
  }

  fun getData() {
    behavior.showLoading()
    LeagueSchedule.Request.get.past15(League.ENGLISH)
        .handleSafely()
        .subscribe(
            { response ->
              response as Events
              val data = response.events
                  .groupBy { it.dateEvent }
                  .map { MatchNewsHolder(it.value[0].getFormattedDate(), it.value) }
              behavior.showData(data)
            },
            { behavior.onError("An error occured!\n${it.message}") },
            { behavior.hideLoading() })
  }

}