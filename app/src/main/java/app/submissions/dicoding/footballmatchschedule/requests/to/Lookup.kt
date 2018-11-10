package app.submissions.dicoding.footballmatchschedule.requests.to

import app.submissions.dicoding.footballmatchschedule.models.LeagueDetails
import app.submissions.dicoding.footballmatchschedule.models.Players
import app.submissions.dicoding.footballmatchschedule.models.Teams
import app.submissions.dicoding.footballmatchschedule.requests.Retrofit
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Lookup {
  @GET("lookupteam.php")
  fun byTeam(@Query("id") id: String): Observable<Teams>

  @GET("lookupleague.php")
  fun byLeague(@Query("id") id: String): Observable<LeagueDetails>

  @GET("lookup_all_teams.php")
  fun teamsByLeagueId(@Query("id") id: String): Observable<Teams>

  @GET("lookup_all_players.php")
  fun playersByTeamId(@Query("id") id: String): Observable<Players>

  companion object {
    private val get: Lookup =
        Retrofit.client.create(Lookup::class.java)
  }

  object Request {
    val get by lazy { Lookup.get }
  }

}

//https://www.thesportsdb.com/api/v1/json/1/lookupplayer.php?id=34145937
//https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=133712