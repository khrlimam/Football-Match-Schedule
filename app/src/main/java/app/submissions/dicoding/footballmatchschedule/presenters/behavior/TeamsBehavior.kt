package app.submissions.dicoding.footballmatchschedule.presenters.behavior

import app.submissions.dicoding.footballmatchschedule.models.TeamDetail

abstract class TeamsBehavior : BaseNetworkRequestBehavior {

  abstract fun showData(teamDetails: List<TeamDetail>)

  override fun hideLoading() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onError(msg: String) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun showLoading() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}