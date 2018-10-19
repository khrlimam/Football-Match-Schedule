package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.presenters.behavior.MatchNewsBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.LeagueSchedule
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MatchNewsPresenter(private val behavior: MatchNewsBehavior) {

  private var disposable: Disposable? = null

  fun getData() {
    disposable = LeagueSchedule.Request.get.leagues()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { response ->
          val leagues = response.leagues.filter { it.strSport == "Soccer" }
          Observable.fromIterable(leagues)
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread())
              .map { league -> league.apply { getBadge(idLeague) { badge = it } } }
              .toList()
              .subscribe { data -> behavior.showData(data) }
        }
  }

  fun dispose() {
    disposable?.dispose()
  }

}