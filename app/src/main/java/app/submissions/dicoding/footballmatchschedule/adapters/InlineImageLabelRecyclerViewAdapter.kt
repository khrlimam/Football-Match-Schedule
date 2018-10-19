package app.submissions.dicoding.footballmatchschedule.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.adapters.InlineImageLabelRecyclerViewAdapter.ViewHolder
import app.submissions.dicoding.footballmatchschedule.exts.loadWithGlide
import app.submissions.dicoding.footballmatchschedule.models.TeamDetail
import kotlinx.android.synthetic.main.linear_image_label.view.*

class InlineImageLabelRecyclerViewAdapter(private val data: List<TeamDetail>) : RecyclerView.Adapter<ViewHolder>() {
  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(data[position])
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.linear_image_label, parent, false))
  }

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(teamDetail: TeamDetail) {
      itemView.apply {
        ivLogo.loadWithGlide(teamDetail.strTeamBadge ?: "")
        tvTitle.text = teamDetail.strTeam
      }
    }
  }
}