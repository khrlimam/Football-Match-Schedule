package app.submissions.dicoding.footballmatchschedule.models.holders

import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.presenters.TimelinePresenter.Predicate

data class TimeLineHolder(val team: Team, val predicate: Predicate, val time: String, val player: Player) {

  fun timeToInt(): Int = this.time.removeSuffix("'").toInt()

  enum class WHICH {
    HOME, AWAY
  }

  data class Team(val which: WHICH, val event: Event) {
    fun getTeamName(): String? {
      return when (which) {
        WHICH.HOME -> event.strHomeTeam
        WHICH.AWAY -> event.strAwayTeam
      }
    }

    fun getTeamBadge(callback: (String) -> Unit) {
      when (which) {
        WHICH.HOME -> event.teamHomeBadge(callback)
        WHICH.AWAY -> event.teamAwayBadge(callback)
      }
    }
  }

  data class Player(val name: String, val position: String?)

}