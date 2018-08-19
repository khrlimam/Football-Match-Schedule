package app.submissions.dicoding.footballmatchschedule.models.holders

import app.submissions.dicoding.footballmatchschedule.models.Event

data class MatchNewsHolder(val date: String, val news: List<Event>)