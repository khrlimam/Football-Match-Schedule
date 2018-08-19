package app.submissions.dicoding.footballmatchschedule.requests.to

import app.submissions.dicoding.footballmatchschedule.models.Events
import app.submissions.dicoding.footballmatchschedule.requests.Retrofit
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface LeagueSchedule {

  @GET("eventsnextleague.php")
  fun next15(@Query("id") id: Int): Observable<Events>

  @GET("eventspastleague.php")
  fun past15(@Query("id") id: Int): Observable<Events>

  companion object {
    private val get: LeagueSchedule =
        Retrofit.client.create(LeagueSchedule::class.java)
  }

  object Request {
    val get by lazy { LeagueSchedule.get }
  }
}

