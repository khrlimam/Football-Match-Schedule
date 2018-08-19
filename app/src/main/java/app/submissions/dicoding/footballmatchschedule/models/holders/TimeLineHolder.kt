package app.submissions.dicoding.footballmatchschedule.models.holders

data class TimeLineHolder(val teamType: Int, val predicate: String, val time: String, val subject: String) {

  fun timeToInt(): Int = this.time.removeSuffix("'").toInt()

  companion object {
    const val HOME: Int = 0
    const val AWAY: Int = 1
  }
}