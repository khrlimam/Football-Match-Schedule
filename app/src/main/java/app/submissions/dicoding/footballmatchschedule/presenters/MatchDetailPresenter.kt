package app.submissions.dicoding.footballmatchschedule.presenters

import app.submissions.dicoding.footballmatchschedule.exts.handleSafely
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.models.Teams
import app.submissions.dicoding.footballmatchschedule.models.holders.TimeLineHolder
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.MatchDetailBehavior
import app.submissions.dicoding.footballmatchschedule.requests.to.Lookup
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MatchDetailPresenter(private val behavior: MatchDetailBehavior) : AnkoLogger {
  fun getTeamDetail(teamId: String) {
    behavior.showLoading()
    behavior.setDisposable(
        Lookup.Request.get.byTeam(teamId)
            .handleSafely()
            .subscribe({
              it as Teams
              behavior.showImage(it)
            }, {
              behavior.showError("Error occured! ${it.message}")
            }, {
              behavior.hideLoading()
            }))
  }

  fun mapScheduleToTimeline(data: Event) {
    val events = listOf(
        TeamHolder(TimeLineHolder.AWAY, "got red card", data.strAwayRedCards),
        TeamHolder(TimeLineHolder.AWAY, "got yellow card", data.strAwayYellowCards),
        TeamHolder(TimeLineHolder.AWAY, "made a goal", data.strAwayGoalDetails),
        TeamHolder(TimeLineHolder.HOME, "got red card", data.strHomeRedCards),
        TeamHolder(TimeLineHolder.HOME, "got yellow card", data.strHomeYellowCards),
        TeamHolder(TimeLineHolder.HOME, "made a goal", data.strHomeGoalDetails))

    val timeLines: MutableList<TimeLineHolder> = mutableListOf()
    events.forEach { team ->
      team.event?.split(";")?.forEach { it: String ->
        val playerDidSomething = it.split(":")
        if (playerDidSomething.size > 1) {
          info(playerDidSomething[1])
          val on = playerDidSomething[0]
          var by = playerDidSomething[1]
          if (by.isEmpty()) by = "The player"
          timeLines.add(TimeLineHolder(team.which, team.doSomething, on, by))
        }
      }
    }
    timeLines.sortBy { it.timeToInt() }
    behavior.showTimeline(timeLines)
  }

  data class TeamHolder(val which: Int, val doSomething: String, val event: String?)

}