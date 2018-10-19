package app.submissions.dicoding.footballmatchschedule.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TeamDetail(
    @SerializedName("idTeam")
    @Expose
    var idTeam: String?,
    @SerializedName("idSoccerXML")
    @Expose
    var idSoccerXML: String?,
    @SerializedName("intLoved")
    @Expose
    var intLoved: String?,
    @SerializedName("strTeam")
    @Expose
    var strTeam: String?,
    @SerializedName("strTeamShort")
    @Expose
    var strTeamShort: String?,
    @SerializedName("strAlternate")
    @Expose
    var strAlternate: String?,
    @SerializedName("intFormedYear")
    @Expose
    var intFormedYear: String?,
    @SerializedName("strSport")
    @Expose
    var strSport: String?,
    @SerializedName("strLeague")
    @Expose
    var strLeague: String?,
    @SerializedName("idLeague")
    @Expose
    var idLeague: String?,
    @SerializedName("strDivision")
    @Expose
    var strDivision: String?,
    @SerializedName("strManager")
    @Expose
    var strManager: String?,
    @SerializedName("strStadium")
    @Expose
    var strStadium: String?,
    @SerializedName("strKeywords")
    @Expose
    var strKeywords: String?,
    @SerializedName("strRSS")
    @Expose
    var strRSS: String?,
    @SerializedName("strStadiumThumb")
    @Expose
    var strStadiumThumb: String?,
    @SerializedName("strStadiumDescription")
    @Expose
    var strStadiumDescription: String?,
    @SerializedName("strStadiumLocation")
    @Expose
    var strStadiumLocation: String?,
    @SerializedName("intStadiumCapacity")
    @Expose
    var intStadiumCapacity: String?,
    @SerializedName("strWebsite")
    @Expose
    var strWebsite: String?,
    @SerializedName("strFacebook")
    @Expose
    var strFacebook: String?,
    @SerializedName("strTwitter")
    @Expose
    var strTwitter: String?,
    @SerializedName("strInstagram")
    @Expose
    var strInstagram: String?,
    @SerializedName("strDescriptionEN")
    @Expose
    var strDescriptionEN: String?,
    @SerializedName("strDescriptionDE")
    @Expose
    var strDescriptionDE: String?,
    @SerializedName("strDescriptionFR")
    @Expose
    var strDescriptionFR: String?,
    @SerializedName("strDescriptionCN")
    @Expose
    var strDescriptionCN: String?,
    @SerializedName("strDescriptionIT")
    @Expose
    var strDescriptionIT: String?,
    @SerializedName("strDescriptionJP")
    @Expose
    var strDescriptionJP: String?,
    @SerializedName("strDescriptionRU")
    @Expose
    var strDescriptionRU: String?,
    @SerializedName("strDescriptionES")
    @Expose
    var strDescriptionES: String?,
    @SerializedName("strDescriptionPT")
    @Expose
    var strDescriptionPT: String?,
    @SerializedName("strDescriptionSE")
    @Expose
    var strDescriptionSE: String?,
    @SerializedName("strDescriptionNL")
    @Expose
    var strDescriptionNL: String?,
    @SerializedName("strDescriptionHU")
    @Expose
    var strDescriptionHU: String?,
    @SerializedName("strDescriptionNO")
    @Expose
    var strDescriptionNO: String?,
    @SerializedName("strDescriptionIL")
    @Expose
    var strDescriptionIL: String?,
    @SerializedName("strDescriptionPL")
    @Expose
    var strDescriptionPL: String?,
    @SerializedName("strGender")
    @Expose
    var strGender: String?,
    @SerializedName("strCountry")
    @Expose
    var strCountry: String?,
    @SerializedName("strTeamBadge")
    @Expose
    var strTeamBadge: String?,
    @SerializedName("strTeamJersey")
    @Expose
    var strTeamJersey: String?,
    @SerializedName("strTeamLogo")
    @Expose
    var strTeamLogo: String?,
    @SerializedName("strTeamFanart1")
    @Expose
    var strTeamFanart1: String?,
    @SerializedName("strTeamFanart2")
    @Expose
    var strTeamFanart2: String?,
    @SerializedName("strTeamFanart3")
    @Expose
    var strTeamFanart3: String?,
    @SerializedName("strTeamFanart4")
    @Expose
    var strTeamFanart4: String?,
    @SerializedName("strTeamBanner")
    @Expose
    var strTeamBanner: String?,
    @SerializedName("strYoutube")
    @Expose
    var strYoutube: String?,
    @SerializedName("strLocked")
    @Expose
    var strLocked: String?)

data class TeamDetails(val teams: List<TeamDetail>)