package app.submissions.dicoding.footballmatchschedule.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import app.submissions.dicoding.footballmatchschedule.BuildConfig
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.exts.abbreviatedName
import app.submissions.dicoding.footballmatchschedule.exts.loadWithGlide
import app.submissions.dicoding.footballmatchschedule.layouts.PlayerItemLineup
import app.submissions.dicoding.footballmatchschedule.layouts.PlayerItemLineup.WhichPlayer.AWAY
import app.submissions.dicoding.footballmatchschedule.layouts.PlayerItemLineup.WhichPlayer.HOME
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.presenters.LineupPresenter
import app.submissions.dicoding.footballmatchschedule.presenters.LineupPresenter.LINEUPS.*
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.LineupBehavior
import kotlinx.android.synthetic.main.team_lineups.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.ctx

class Lineups : Fragment(), LineupBehavior, AnkoLogger {

  private val presenter = LineupPresenter(this)

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.team_lineups, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val event = arguments?.getParcelable<Event>(BuildConfig.EVENT_DATA)
    event?.teamHomeBadge { ivHome.loadWithGlide(it) }
    event?.teamAwayBadge { ivAway.loadWithGlide(it) }
    tvHome.text = event?.strHomeTeam
    tvAway?.text = event?.strAwayTeam
    event?.let { presenter.formatLineUps(it) }
  }

  @SuppressLint("SetTextI18n")
  override fun onLineUpFormatted(data: Map<LineupPresenter.LINEUPS, List<String>?>) {
    val homeDefense = data[HOME_DEFENSE]
    val homeMidField = data[HOME_MIDFIELD]
    val homeForward = data[HOME_FORWARD]
    val homeKeeper = data[HOME_KEEPER]
    val awayDefense = data[AWAY_DEFENSE]
    val awayMidField = data[AWAY_MIDFIELD]
    val awayForward = data[AWAY_FORWARD]
    val awayKeeper = data[AWAY_KEEPER]

    tvHomeFormation.text = "${homeDefense?.size}-${homeMidField?.size}-${homeForward?.size}"
    tvAwayFormation.text = "${awayDefense?.size}-${awayMidField?.size}-${awayForward?.size}"

    addTeamLineup(llHomeBack, homeDefense, HOME)
    addTeamLineup(llHomeMidField, homeMidField, HOME)
    addTeamLineup(llHomeForward, homeForward, HOME)
    addTeamLineup(llHomeKeeper, homeKeeper, HOME)

    addTeamLineup(llAwayBack, awayDefense, AWAY)
    addTeamLineup(llAwayMidField, awayMidField, AWAY)
    addTeamLineup(llAwayForward, awayForward, AWAY)
    addTeamLineup(llAwayKeeper, awayKeeper, AWAY)

  }

  @SuppressLint("InflateParams")
  private fun addTeamLineup(lineUpName: LinearLayout, teams: List<String>?, which: PlayerItemLineup.WhichPlayer) {
    teams?.forEach {
      lineUpName.addView(PlayerItemLineup(it.abbreviatedName(), which).createView(AnkoContext.create(ctx)))
    }
  }
}