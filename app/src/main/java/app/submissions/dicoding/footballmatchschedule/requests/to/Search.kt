package app.submissions.dicoding.footballmatchschedule.requests.to

import app.submissions.dicoding.footballmatchschedule.models.Events
import app.submissions.dicoding.footballmatchschedule.models.Teams
import app.submissions.dicoding.footballmatchschedule.requests.Retrofit
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Search {

  @GET("searchteams.php")
  fun searchTeams(@Query("t") team: String): Observable<Teams>

  @GET("searchevents.php")
  fun searchTeamMatches(@Query("e") event: String): Observable<Events>

  companion object {
    private val get: Search =
        Retrofit.client.create(Search::class.java)
  }

  object Request {
    val get by lazy { Search.get }
  }
}