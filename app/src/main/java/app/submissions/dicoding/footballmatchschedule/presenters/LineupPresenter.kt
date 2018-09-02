package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.exts.splitCommaSeparatedToList
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.presenters.LineupPresenter.LINEUPS.*
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.LineupBehavior

class LineupPresenter(private val behavior: LineupBehavior) {

  fun formatLineUps(event: Event) {
    val data = mutableMapOf<LINEUPS, List<String>?>()
    data[HOME_DEFENSE] = event.strHomeLineupDefense?.splitCommaSeparatedToList()
    data[HOME_MIDFIELD] = event.strHomeLineupMidfield?.splitCommaSeparatedToList()
    data[HOME_FORWARD] = event.strHomeLineupForward?.splitCommaSeparatedToList()
    data[HOME_KEEPER] = event.strHomeLineupGoalkeeper?.splitCommaSeparatedToList()


    data[AWAY_DEFENSE] = event.strAwayLineupDefense?.splitCommaSeparatedToList()
    data[AWAY_MIDFIELD] = event.strAwayLineupMidfield?.splitCommaSeparatedToList()
    data[AWAY_FORWARD] = event.strAwayLineupForward?.splitCommaSeparatedToList()
    data[AWAY_KEEPER] = event.strAwayLineupGoalkeeper?.splitCommaSeparatedToList()
    behavior.onLineUpFormatted(data)
  }

  enum class LINEUPS {
    HOME_KEEPER, HOME_DEFENSE, HOME_MIDFIELD, HOME_FORWARD,
    AWAY_KEEPER, AWAY_DEFENSE, AWAY_MIDFIELD, AWAY_FORWARD
  }

}