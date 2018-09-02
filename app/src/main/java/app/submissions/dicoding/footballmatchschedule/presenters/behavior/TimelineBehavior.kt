package app.submissions.dicoding.footballmatchschedule.presenters.behavior

import app.submissions.dicoding.footballmatchschedule.models.holders.TimeLineHolder

interface TimelineBehavior {
  fun showTimeline(data: List<TimeLineHolder>)
}