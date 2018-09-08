package app.submissions.dicoding.footballmatchschedule.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.submissions.dicoding.footballmatchschedule.BuildConfig
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.adapters.TimelineAdapter
import app.submissions.dicoding.footballmatchschedule.constants.Constants
import app.submissions.dicoding.footballmatchschedule.exts.gone
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.models.holders.TimeLineHolder
import app.submissions.dicoding.footballmatchschedule.presenters.TimelinePresenter
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.TimelineBehavior
import kotlinx.android.synthetic.main.recycler_view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.ctx

class Timeline : Fragment(), TimelineBehavior, AnkoLogger {

  private val presenter = TimelinePresenter(this)
  private val data: MutableList<TimeLineHolder> = mutableListOf()
  private val adapter: TimelineAdapter = TimelineAdapter(data)

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.recycler_view, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val event = arguments?.getParcelable<Event>(Constants.EVENT_DATA)

    shimmer.stopShimmer()
    shimmer.gone()

    rvRecyclerView.adapter = adapter
    rvRecyclerView.layoutManager = LinearLayoutManager(ctx)

    event?.let { presenter.mapEventToTimeline(it) }
  }

  override fun showTimeline(data: List<TimeLineHolder>) {
    this.data.clear()
    this.data.addAll(data)
    adapter.notifyDataSetChanged()
  }

}