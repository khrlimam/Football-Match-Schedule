package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.exts.splitCommaSeparatedToList
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.presenters.LineupPresenter.LINEUPS.*
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.LineupBehavior

class LineupPresenter(private val behavior: LineupBehavior) {

  fun formatLineUps(event: Event) {
    val data = mutableMapOf<LINEUPS, List<String>?>()
    data.apply {
      set(HOME_DEFENSE, event.strHomeLineupDefense?.splitCommaSeparatedToList())
      set(HOME_MIDFIELD, event.strHomeLineupMidfield?.splitCommaSeparatedToList())
      set(HOME_FORWARD, event.strHomeLineupForward?.splitCommaSeparatedToList())
      set(HOME_KEEPER, event.strHomeLineupGoalkeeper?.splitCommaSeparatedToList())

      set(AWAY_DEFENSE, event.strAwayLineupDefense?.splitCommaSeparatedToList())
      set(AWAY_MIDFIELD, event.strAwayLineupMidfield?.splitCommaSeparatedToList())
      set(AWAY_FORWARD, event.strAwayLineupForward?.splitCommaSeparatedToList())
      set(AWAY_KEEPER, event.strAwayLineupGoalkeeper?.splitCommaSeparatedToList())
    }
    behavior.onLineUpFormatted(data)
  }

  enum class LINEUPS {
    HOME_KEEPER, HOME_DEFENSE, HOME_MIDFIELD, HOME_FORWARD,
    AWAY_KEEPER, AWAY_DEFENSE, AWAY_MIDFIELD, AWAY_FORWARD
  }

}