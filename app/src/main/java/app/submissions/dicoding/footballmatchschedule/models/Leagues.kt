package app.submissions.dicoding.footballmatchschedule.models

import app.submissions.dicoding.footballmatchschedule.exts.handleSafely
import app.submissions.dicoding.footballmatchschedule.requests.to.Lookup
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

  var badge: String? = null

  override fun toString(): String {
    return strLeague
  }

  fun getBadge(badgeGot: (String?) -> Unit) {
    Lookup.Request.get.byLeague(idLeague)
        .handleSafely()
        .subscribe { response ->
          response as LeagueDetails
          val league = response.leagues?.get(0)
          badgeGot(league?.strBadge)
        }.isDisposed
  }

}

data class Leagues(val leagues: List<League>)