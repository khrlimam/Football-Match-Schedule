package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.exts.getOrAnother
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.models.holders.TimeLineHolder
import app.submissions.dicoding.footballmatchschedule.models.holders.TimeLineHolder.Player
import app.submissions.dicoding.footballmatchschedule.models.holders.TimeLineHolder.Team
import app.submissions.dicoding.footballmatchschedule.models.holders.TimeLineHolder.WHICH.AWAY
import app.submissions.dicoding.footballmatchschedule.models.holders.TimeLineHolder.WHICH.HOME
import app.submissions.dicoding.footballmatchschedule.presenters.LineupPresenter.LINEUPS.*
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.LineupBehavior
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.TimelineBehavior

class TimelinePresenter(val behavior: TimelineBehavior) {

  private lateinit var event: Event
  private val onLineUpFormatted: OnLineUpFormatted = OnLineUpFormatted()
  private val lineUpPresenter: LineupPresenter = LineupPresenter(onLineUpFormatted)

  private fun eventToTimeline(event: Event, playerPosition: Map<String, String>) {
    val events = listOf(
        TeamHolder(Team(AWAY, event), Predicate.RED_CARD, event.strAwayRedCards),
        TeamHolder(Team(AWAY, event), Predicate.YELLOW_CARD, event.strAwayYellowCards),
        TeamHolder(Team(AWAY, event), Predicate.GOAL, event.strAwayGoalDetails),
        TeamHolder(Team(HOME, event), Predicate.RED_CARD, event.strHomeRedCards),
        TeamHolder(Team(HOME, event), Predicate.YELLOW_CARD, event.strHomeYellowCards),
        TeamHolder(Team(HOME, event), Predicate.GOAL, event.strHomeGoalDetails))

    val timeLines: MutableList<TimeLineHolder> = mutableListOf()
    events.forEach { event_ ->
      event_.aboutSomething?.split(";")?.forEach { it: String ->
        val playerDidSomething = it.split(":")
        if (playerDidSomething.size > 1) {
          val on = playerDidSomething[0]
          var by = playerDidSomething[1]
          if (by.isEmpty()) by = "The player"
          timeLines.add(TimeLineHolder(event_.team, event_.doSomething, on, Player(by, playerPosition.getOrAnother(by, "Some Position"))))
        }
      }
    }
    timeLines.sortBy { it.timeToInt() }
    behavior.showTimeline(timeLines)
  }

  fun mapEventToTimeline(event: Event) {
    this.event = event
    lineUpPresenter.formatLineUps(event)
  }

  data class TeamHolder(val team: Team, val doSomething: Predicate, val aboutSomething: String?)

  enum class Predicate {
    YELLOW_CARD, RED_CARD, GOAL
  }

  private inner class OnLineUpFormatted : LineupBehavior {
    override fun onLineUpFormatted(data: Map<LineupPresenter.LINEUPS, List<String>?>) {
      val map: MutableMap<String, String> = mutableMapOf()
      data[HOME_DEFENSE]?.forEach { map[it] = "Defense" }
      data[HOME_MIDFIELD]?.forEach { map[it] = "Midfield" }
      data[HOME_FORWARD]?.forEach { map[it] = "Forward" }

      data[AWAY_DEFENSE]?.forEach { map[it] = "Defense" }
      data[AWAY_MIDFIELD]?.forEach { map[it] = "Midfield" }
      data[AWAY_FORWARD]?.forEach { map[it] = "Forward" }
      eventToTimeline(event, map)
    }

  }

}