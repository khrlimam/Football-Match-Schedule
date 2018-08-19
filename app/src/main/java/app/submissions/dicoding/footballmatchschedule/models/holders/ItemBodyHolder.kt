package app.submissions.dicoding.footballmatchschedule.models.holders

import app.submissions.dicoding.footballmatchschedule.models.Event

data class ItemBodyHolder(val body: Event) : DataType() {
  override fun getType(): Int = DataType.BODY
}