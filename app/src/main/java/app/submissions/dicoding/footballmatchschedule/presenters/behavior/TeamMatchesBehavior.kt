package app.submissions.dicoding.footballmatchschedule.presenters.behavior

import app.submissions.dicoding.footballmatchschedule.db.tables.Favorites
import app.submissions.dicoding.footballmatchschedule.models.Event

abstract class TeamMatchesBehavior : BaseNetworkRequestBehavior {

  abstract fun showData(results: List<Favorites>)

  override fun showLoading() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun hideLoading() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onError(msg: String) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}