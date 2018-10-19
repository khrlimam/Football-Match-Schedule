package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.exts.handleSafely
import app.submissions.dicoding.footballmatchschedule.models.TeamDetails
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.TeamsBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.Lookup
import io.reactivex.disposables.Disposable

class TeamsPresenter(private val behavior: TeamsBehavior) {
  private var disposable: Disposable? = null

  fun getData(leagueId: String) {
    behavior.showLoading()
    disposable = Lookup.Request.get.teamsByLeagueId(leagueId)
        .handleSafely()
        .subscribe(
            {
              it as TeamDetails
              behavior.showData(it.teams)
            },
            { behavior.onError(it.localizedMessage) },
            { behavior.hideLoading() })
  }

  fun dispose() {
    disposable?.dispose()
  }

}