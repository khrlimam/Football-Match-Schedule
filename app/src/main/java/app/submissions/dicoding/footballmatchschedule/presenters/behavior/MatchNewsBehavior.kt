package app.submissions.dicoding.footballmatchschedule.presenters.behavior

import app.submissions.dicoding.footballmatchschedule.models.League

interface MatchNewsBehavior {
  fun showData(leagues: List<League>)
}