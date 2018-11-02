package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.presenters.behavior.MatchNewsBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.LeagueSchedule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MatchNewsPresenter(private val behavior: MatchNewsBehavior) {

  private var disposable: Disposable? = null

  fun getData() {
    disposable = LeagueSchedule.Request.get.leagues()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .map { leagues ->
          leagues.leagues.asSequence()
              .filter { it.strSport == "Soccer" }
              .map { league -> league.apply { getBadge(idLeague) { badge = it } } }.toList()
        }
        .subscribe {
          behavior.showData(it)
        }
  }

  fun dispose() {
    disposable?.dispose()
  }

}