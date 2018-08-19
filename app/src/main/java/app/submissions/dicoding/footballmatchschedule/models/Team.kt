package app.submissions.dicoding.footballmatchschedule.models

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idTeam")
    var teamId: String = "",

    @SerializedName("strTeam")
    var teamName: String? = null,

    @SerializedName("strTeamBadge")
    var teamBadge: String = "",

    @SerializedName("strTeamFanart1")
    var fanArt1: String = "",

    @SerializedName("strTeamFanart2")
    var fanArt2: String = "",

    @SerializedName("strTeamFanart3")
    var fanArt3: String = "",

    @SerializedName("strTeamFanart4")
    var fanArt4: String = ""
)

data class Teams(val teams: List<Team>)