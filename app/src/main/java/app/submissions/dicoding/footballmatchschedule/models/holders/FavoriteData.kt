package app.submissions.dicoding.footballmatchschedule.models.holders

import app.submissions.dicoding.footballmatchschedule.models.Event
import com.google.gson.Gson

class FavoriteData(val event: Event, val itemType: ItemType) {
  fun toJson(): String {
    return Gson().toJson(this)
  }

  companion object {
    fun fromJson(serialized: String): FavoriteData {
      return Gson().fromJson(serialized, FavoriteData::class.java)
    }
  }
}

enum class ItemType {
  NEWS, SCHEDULE
}