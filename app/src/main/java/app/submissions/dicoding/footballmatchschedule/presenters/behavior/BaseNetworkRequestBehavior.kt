package app.submissions.dicoding.footballmatchschedule.presenters.behavior

interface BaseNetworkRequestBehavior {
  fun showLoading()
  fun hideLoading()
  fun onError(msg: String)
}