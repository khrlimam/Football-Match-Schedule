package app.submissions.dicoding.footballmatchschedule.requests.to

import app.submissions.dicoding.footballmatchschedule.models.Events
import app.submissions.dicoding.footballmatchschedule.models.Leagues
import app.submissions.dicoding.footballmatchschedule.models.Results
import app.submissions.dicoding.footballmatchschedule.requests.Retrofit
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Schedule {

  @GET("eventsnextleague.php")
  fun next15ByLeagueId(@Query("id") id: String): Observable<Events>

  @GET("eventspastleague.php")
  fun past15ByLeagueId(@Query("id") id: String): Observable<Events>

  @GET("eventsnext.php")
  fun next5ByTeamId(@Query("id") id: String): Observable<Events>

  @GET("eventslast.php")
  fun past5ByTeamId(@Query("id") id: String): Observable<Results>

  @GET("all_leagues.php")
  fun leagues(): Observable<Leagues>

  companion object {
    private val get: Schedule =
        Retrofit.client.create(Schedule::class.java)
  }

  object Request {
    val get by lazy { Schedule.get }
  }
}