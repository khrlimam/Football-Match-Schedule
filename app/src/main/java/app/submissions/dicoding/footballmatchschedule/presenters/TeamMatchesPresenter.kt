package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.db.tables.Favorites
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.TeamMatchesBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.Schedule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TeamMatchesPresenter(private val behavior: TeamMatchesBehavior) {
  private var disposable: Disposable? = null

  fun dispose() {
    disposable?.dispose()
  }

  fun getData(teamId: String) {
    val collectedData = mutableListOf<Favorites>()
    behavior.showLoading()
    disposable = Schedule.Request.get.next5ByTeamId(teamId)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          collectedData.addAll(it.events.map { item -> Favorites(-1, item.toJson(), Favorites.ItemType.NEXT, Event::class.java.name) })
          Schedule.Request.get.past5ByTeamId(teamId)
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe({ resp ->
                collectedData.addAll(resp.results.map { item -> Favorites(-1, item.toJson(), Favorites.ItemType.PAST, Event::class.java.name) })
                behavior.showData(collectedData)
              }, { err -> behavior.onError(err.localizedMessage) }, { behavior.hideLoading() })
        }, { behavior.onError(it.localizedMessage) }, { })
  }

}