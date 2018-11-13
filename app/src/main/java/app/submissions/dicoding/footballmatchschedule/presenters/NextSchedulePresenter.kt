package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.exts.handleSafely
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.models.holders.DataType
import app.submissions.dicoding.footballmatchschedule.models.holders.ItemBodyHolder
import app.submissions.dicoding.footballmatchschedule.models.holders.ItemHeaderHolder
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.NextScheduleBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.Schedule
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NextSchedulePresenter(private val behavior: NextScheduleBehavior) {

  private var disposable: Disposable? = null
  private var counter: Int = 0

  fun dispose() {
    disposable?.dispose()
  }

  private fun onError(msg: String?) {
    behavior.onError("Oops. Error occurred!\n%s".format(msg))
  }

  companion object {
    fun getGroupedDataItem(data: List<Event>): MutableList<DataType> {
      val dataItem: MutableList<DataType> = mutableListOf()
      data.groupBy { it.dateEvent }.forEach { entry ->
        dataItem.add(ItemHeaderHolder(entry.value[0].localDateWithDayName()))
        dataItem.addAll(entry.value.map { ItemBodyHolder(it) })
      }
      return dataItem
    }
  }

  fun getData(leagueId: String) {
    behavior.showLoading()
    disposable = Schedule.Request.get.next15ByLeagueId(leagueId)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ data ->
          // show first retrieved data
          behavior.showData(getGroupedDataItem(data.events))
          Observable.fromIterable(data.events)
              .handleSafely()
              .forEach { event ->
                event as Event
                with(event) {
                  // make this process serial to make sure the counter added once
                  teamAwayBadge {
                    awayBadge = it
                    teamHomeBadge {
                      homeBadge = it
                      ++counter
                      if (counter == data.events.size)
                      // show the data again after all images are loaded
                        behavior.showData(getGroupedDataItem(data.events))
                    }
                  }
                }
              }
        }, { onError(it.message) },
            { behavior.hideLoading() })
  }
}