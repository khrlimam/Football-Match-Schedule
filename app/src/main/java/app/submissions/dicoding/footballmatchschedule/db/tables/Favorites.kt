package app.submissions.dicoding.footballmatchschedule.db.tables

import app.submissions.dicoding.footballmatchschedule.models.holders.FavoriteData

data class Favorites(val data: FavoriteData) {
  companion object {
    const val TABLE_NAME = "favorites"
    const val ID = "id"
    const val DATA = "data"
  }
}

