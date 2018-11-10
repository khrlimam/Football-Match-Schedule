package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.presenters.behavior.SearchBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.Search
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter(private val behavior: SearchBehavior) {
  private var disposable: Disposable? = null

  fun dispose() {
    disposable?.dispose()
  }

  fun getData(query: String) {
    behavior.showLoading()
    disposable = Search.Request.get.searchTeams(query)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ data ->
          behavior.showData(data.teams.filter { it.strSport == "Soccer" })
        }, {
          behavior.onError(it.localizedMessage)
        }, {
          behavior.hideLoading()
        })
  }

}