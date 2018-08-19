package app.submissions.dicoding.footballmatchschedule.presenters.behavior

import app.submissions.dicoding.footballmatchschedule.models.holders.DataType

abstract class NextScheduleBehavior : BaseNetworkRequestBehavior {
  abstract fun showData(schedules: List<DataType>)

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