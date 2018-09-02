package app.submissions.dicoding.footballmatchschedule.presenters.behavior

import app.submissions.dicoding.footballmatchschedule.presenters.LineupPresenter

interface LineupBehavior {
  fun onLineUpFormatted(data: Map<LineupPresenter.LINEUPS, List<String>?>)
}