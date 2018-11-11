package app.submissions.dicoding.footballmatchschedule.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.adapters.FavoritesAdapter.BaseFavoriteViewHolder
import app.submissions.dicoding.footballmatchschedule.db.tables.Favorites
import app.submissions.dicoding.footballmatchschedule.exts.fontGoogleProductBold
import app.submissions.dicoding.footballmatchschedule.exts.fontGoogleProductRegular
import app.submissions.dicoding.footballmatchschedule.exts.loadWithGlide
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.models.Team
import com.bumptech.glide.Glide
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.next_match_item.view.*
import kotlinx.android.synthetic.main.single_news_item.view.*
import kotlinx.android.synthetic.main.team_item.view.*

class FavoritesAdapter(val favorites: List<Favorites>, val click: (Int) -> Unit) : RecyclerView.Adapter<BaseFavoriteViewHolder>() {

  lateinit var holder: BaseFavoriteViewHolder

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseFavoriteViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    when (viewType) {
      Favorites.ItemType.NEXT -> holder = NextMatchViewHolder(inflater.inflate(R.layout.next_match_item, parent, false), click)
      Favorites.ItemType.PAST -> holder = PreviousMatchViewHolder(inflater.inflate(R.layout.single_news_item, parent, false), click)
      Favorites.ItemType.TEAM -> holder = TeamViewHolder(inflater.inflate(R.layout.team_item, parent, false), click)
    }
    return holder
  }

  override fun getItemViewType(position: Int): Int = favorites[position].type

  override fun getItemCount(): Int = favorites.size

  override fun onBindViewHolder(holder: BaseFavoriteViewHolder, position: Int) {
    holder.bind(favorites[position])
  }

  class PreviousMatchViewHolder(view: View, val click: (Int) -> Unit) : BaseFavoriteViewHolder(view) {
    override fun bind(data: Favorites) {
      itemView?.apply {
        val event = data.dataToObject() as Event
        event.apply {
          winnerBanner { ivImageView.loadWithGlide(it) }
          tvTitle.text = headline()
          tvDate.text = localDateWithDayName()
        }

        tvTitle.fontGoogleProductBold()
        tvDate.fontGoogleProductRegular()
        setOnClickListener { click(position) }
        ivImageView.setOnClickListener { click(position) }
      }
    }
  }

  class NextMatchViewHolder(view: View, val click: (Int) -> Unit) : BaseFavoriteViewHolder(view) {
    override fun bind(data: Favorites) {
      itemView?.apply {
        val event = data.dataToObject() as Event
        event.apply {
          teamHomeBadge { ivHome.loadWithGlide(it) }
          teamAwayBadge { ivAway.loadWithGlide(it) }
          tvTime.text = localTime()
          tvHome.text = strHomeTeam
          tvAway.text = strAwayTeam
        }

        setOnClickListener { click(position) }
      }
    }
  }

  class TeamViewHolder(view: View, val click: (Int) -> Unit) : BaseFavoriteViewHolder(view) {
    @SuppressLint("SetTextI18n")
    override fun bind(data: Favorites) {
      itemView.apply {
        val team = data.dataToObject() as Team
        team.apply {
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
        setOnClickListener { click(position) }
      }
    }

  }

  abstract class BaseFavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(data: Favorites)
  }
}