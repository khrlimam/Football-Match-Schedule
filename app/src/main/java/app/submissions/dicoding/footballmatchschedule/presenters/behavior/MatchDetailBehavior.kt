package app.submissions.dicoding.footballmatchschedule.presenters.behavior

import app.submissions.dicoding.footballmatchschedule.models.Teams
import app.submissions.dicoding.footballmatchschedule.models.holders.TimeLineHolder
import io.reactivex.disposables.Disposable

interface MatchDetailBehavior {
  fun showLoading()
  fun hideLoading()
  fun showImage(teams: Teams)
  fun setDisposable(disposable: Disposable)
  fun showError(message: String)
  fun showTimeline(data:List<TimeLineHolder>)
}