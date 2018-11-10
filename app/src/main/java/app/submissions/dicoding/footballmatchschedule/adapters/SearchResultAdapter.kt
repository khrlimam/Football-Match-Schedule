package app.submissions.dicoding.footballmatchschedule.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.exts.fontGoogleProductRegular
import app.submissions.dicoding.footballmatchschedule.exts.loadWithGlide
import app.submissions.dicoding.footballmatchschedule.models.Team
import com.bumptech.glide.Glide
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.team_item.view.*

class SearchResultAdapter(private val teams: List<Team>, private val onClick: (Team) -> Unit) : RecyclerView.Adapter<SearchResultAdapter.SearchItem>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItem =
      SearchItem(LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false), onClick)

  override fun getItemCount(): Int = teams.size

  override fun onBindViewHolder(holder: SearchItem, position: Int) {
    holder.bind(teams[position])
  }

  class SearchItem(view: View, private val onClick: (Team) -> Unit) : RecyclerView.ViewHolder(view) {
    @SuppressLint("SetTextI18n")
    fun bind(team: Team) {
      itemView.setOnClickListener { onClick(team) }
      itemView.apply {
        tvClubName.fontGoogleProductRegular()
        tvSince.fontGoogleProductRegular()

        tvClubName.text = team.strTeam
        tvSince.text = "Since ${team.intFormedYear}"
        ivTeamBadge.loadWithGlide(team.strTeamBadge ?: "")
        Glide.with(context)
            .asBitmap()
            .load(team.strTeamFanart2
                ?: "https://images.pexels.com/photos/274506/pexels-photo-274506.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260")
            .listener(GlideCustomImageHandler {
              Blurry.with(context).async().from(it).into(ivBackground)
            })
            .into(ivBackground)
      }
    }
  }
}