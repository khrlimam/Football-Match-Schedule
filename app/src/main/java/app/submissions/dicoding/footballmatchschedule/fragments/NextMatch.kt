package app.submissions.dicoding.footballmatchschedule.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.adapters.HeaderBodyRecyclerViewAdapter
import app.submissions.dicoding.footballmatchschedule.exts.gone
import app.submissions.dicoding.footballmatchschedule.exts.visible
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.models.holders.DataType
import app.submissions.dicoding.footballmatchschedule.presenters.NextSchedulePresenter
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.NextScheduleBehavior
import kotlinx.android.synthetic.main.recycler_view.*
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.ctx

class NextMatch : Fragment(), (Event) -> Unit {

  private val presenter: NextSchedulePresenter = NextSchedulePresenter(MyBehavior())
  private val dataItems: MutableList<DataType> = mutableListOf()
  private val adapter: HeaderBodyRecyclerViewAdapter = HeaderBodyRecyclerViewAdapter(dataItems, this)

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.recycler_view, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    presenter.getData()
    rvRecyclerView.adapter = adapter
    rvRecyclerView.layoutManager = LinearLayoutManager(ctx)
  }

  override fun invoke(p1: Event) {
    alert(p1.winnerDescription()).show()
  }

  override fun onStop() {
    presenter.dispose()
    super.onStop()
  }

  override fun onPause() {
    presenter.dispose()
    super.onPause()
  }

  inner class MyBehavior : NextScheduleBehavior() {

    override fun showData(schedules: List<DataType>) {
      dataItems.clear()
      dataItems.addAll(schedules)
      adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
      shimmer.startShimmer()
      shimmer.visible()
    }

    override fun hideLoading() {
      shimmer.stopShimmer()
      shimmer.gone()
    }

    override fun onError(msg: String) {
      alert(msg) {
        okButton { }
      }
    }

  }

}