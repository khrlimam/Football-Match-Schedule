package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.exts.handleSafely
import app.submissions.dicoding.footballmatchschedule.models.League
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.MatchNewsBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.Schedule
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MatchNewsPresenter(private val behavior: MatchNewsBehavior) {

  private var disposable: Disposable? = null
  private var counter: Int = 0

  fun getData() {
    disposable = Schedule.Request.get.leagues()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { leagues ->
          val onlySportLeagues = leagues.leagues.filter { it.strSport == "Soccer" }
          // show data first time retrieved
          behavior.showData(onlySportLeagues)
          Observable.fromIterable(onlySportLeagues)
              .handleSafely()
              .forEach { league ->
                league as League
                with(league) {
                  getBadge {
                    badge = it
                    ++counter
                    if (counter == onlySportLeagues.count())
                    // update data after all league images are loaded
                      behavior.showData(onlySportLeagues)
                  }
                }
              }
        }
  }

  fun dispose() {
    disposable?.dispose()
  }

}