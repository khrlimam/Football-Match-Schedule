package app.submissions.dicoding.footballmatchschedule.adapters

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.exts.loadWithGlide
import app.submissions.dicoding.footballmatchschedule.models.holders.TimeLineHolder
import app.submissions.dicoding.footballmatchschedule.presenters.TimelinePresenter
import kotlinx.android.synthetic.main.timeline_holder.view.*
import org.jetbrains.anko.AnkoLogger

class TimelineAdapter(private val data: List<TimeLineHolder>) : RecyclerView.Adapter<TimelineAdapter.Holder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
    return Holder(LayoutInflater.from(parent.context)
        .inflate(R.layout.timeline_holder, parent, false))
  }

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(holder: Holder, position: Int) {
    holder.bind(data[position])
  }

  class Holder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

    @SuppressLint("SetTextI18n")
    fun bind(data: TimeLineHolder) {
      when (data.predicate) {
        TimelinePresenter.Predicate.YELLOW_CARD -> {
          itemView.ivPredicate.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.yellow_card))
          itemView.tvPredicate.text = "yellow card".toUpperCase()
        }
        TimelinePresenter.Predicate.RED_CARD -> {
          itemView.ivPredicate.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.red_card))
          itemView.tvPredicate.text = "red card".toUpperCase()
        }
        TimelinePresenter.Predicate.GOAL -> {
          itemView.ivPredicate.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_a00_goal_icon))
          itemView.tvPredicate.text = data.predicate.name
        }
      }
      itemView.tvTime.text = data.time
      itemView.tvPlayer.text = data.player.name
      itemView.tvPlayerPosition.text = "${data.team.getTeamName()} · ${data.player.position}"
      data.team.getTeamBadge { itemView.ivBadge.loadWithGlide(it) }
    }

  }
}