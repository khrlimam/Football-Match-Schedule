package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.exts.handleSafely
import app.submissions.dicoding.footballmatchschedule.models.Players
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.TeamPlayersBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.Lookup
import io.reactivex.disposables.Disposable

class TeamPlayersPresenter(private val behavior: TeamPlayersBehavior) {
  private var disposable: Disposable? = null

  fun dispose() {
    disposable?.dispose()
  }

  fun getData(teamId: String) {
    behavior.showLoading()
    disposable = Lookup.Request.get.playersByTeamId(teamId)
        .handleSafely()
        .subscribe({
          it as Players
          behavior.showData(it.player)
        }, { behavior.onError(it.localizedMessage) }, { behavior.hideLoading() })
  }

}