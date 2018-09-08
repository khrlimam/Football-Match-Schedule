package app.submissions.dicoding.footballmatchschedule.models.holders

import app.submissions.dicoding.footballmatchschedule.fabric.GsonFabric
import app.submissions.dicoding.footballmatchschedule.models.Event

class FavoriteData(val event: Event, val itemType: EItemType) {
  fun toJson(): String {
    return GsonFabric.build.toJson(this)
  }
}

enum class EItemType(val field: Int) {
  PAST(ItemType.PAST), NEXT(ItemType.NEXT)
}

object ItemType {
  const val PAST = 0
  const val NEXT = 1
}