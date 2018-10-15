package app.submissions.dicoding.footballmatchschedule.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class League(
    @SerializedName("idLeague")
    @Expose
    val idLeague: String,
    @SerializedName("strLeague")
    @Expose
    val strLeague: String,
    @SerializedName("strSport")
    @Expose
    val strSport: String,
    @SerializedName("strLeagueAlternate")
    @Expose
    val strLeagueAlternate: String) {
  override fun toString(): String {
    return strLeague
  }
}

data class Leagues(val leagues: List<League>)