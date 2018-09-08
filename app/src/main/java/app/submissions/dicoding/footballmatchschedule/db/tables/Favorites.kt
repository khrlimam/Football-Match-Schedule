package app.submissions.dicoding.footballmatchschedule.db.tables

import android.os.Parcelable
import app.submissions.dicoding.footballmatchschedule.fabric.GsonFabric
import app.submissions.dicoding.footballmatchschedule.models.holders.FavoriteData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorites(val id: Long?, val data: String?) : Parcelable {
  companion object {
    const val TABLE_NAME = "favorites"
    const val ID = "id"
    const val DATA = "data"
  }

  fun dataToObject(): FavoriteData {
    return GsonFabric.build.fromJson(data, FavoriteData::class.java)
  }

}

