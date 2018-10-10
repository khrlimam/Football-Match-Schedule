package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.constants.League
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.models.holders.DataType
import app.submissions.dicoding.footballmatchschedule.models.holders.ItemBodyHolder
import app.submissions.dicoding.footballmatchschedule.models.holders.ItemHeaderHolder
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.NextScheduleBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.LeagueSchedule
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

class NextSchedulePresenter(private val behavior: NextScheduleBehavior,
                            private val mainScheduler: Scheduler,
                            private val backgroundScheduler: Scheduler) {

  private var disposable: Disposable? = null

  fun dispose() {
    disposable?.dispose()
  }

  private fun onError(msg: String?) {
    behavior.onError("Oops. Error occured!\n%s".format(msg))
  }

  companion object {
    fun getGroupedDataItem(data: List<Event>): MutableList<DataType> {
      val dataItem: MutableList<DataType> = mutableListOf()
      data.groupBy { it.dateEvent }.forEach { eventsWithDate ->
        dataItem.add(ItemHeaderHolder(eventsWithDate.value[0].getFormattedDate()))
        dataItem.addAll(eventsWithDate.value.map { ItemBodyHolder(it) })
      }
      return dataItem
    }
  }

  fun getData() {
    behavior.showLoading()
    disposable = LeagueSchedule.Request.get.next15(League.ENGLISH)
        .subscribeOn(backgroundScheduler)
        .observeOn(mainScheduler)
        .subscribe({
          behavior.showData(getGroupedDataItem(it.events))
        }, {
          onError(it.message)
        }, {
          behavior.hideLoading()
        })
  }
}