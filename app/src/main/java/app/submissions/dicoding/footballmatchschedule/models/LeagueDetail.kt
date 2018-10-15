package app.submissions.dicoding.footballmatchschedule.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class LeagueDetail(
    @SerializedName("idLeague")
    @Expose
    var idLeague: String? = null,
    @SerializedName("idSoccerXML")
    @Expose
    var idSoccerXML: String? = null,
    @SerializedName("strSport")
    @Expose
    var strSport: String? = null,
    @SerializedName("strLeague")
    @Expose
    var strLeague: String? = null,
    @SerializedName("strLeagueAlternate")
    @Expose
    var strLeagueAlternate: String? = null,
    @SerializedName("idCup")
    @Expose
    var idCup: String? = null,
    @SerializedName("intFormedYear")
    @Expose
    var intFormedYear: String? = null,
    @SerializedName("dateFirstEvent")
    @Expose
    var dateFirstEvent: String? = null,
    @SerializedName("strGender")
    @Expose
    var strGender: String? = null,
    @SerializedName("strCountry")
    @Expose
    var strCountry: String? = null,
    @SerializedName("strWebsite")
    @Expose
    var strWebsite: String? = null,
    @SerializedName("strFacebook")
    @Expose
    var strFacebook: String? = null,
    @SerializedName("strTwitter")
    @Expose
    var strTwitter: String? = null,
    @SerializedName("strYoutube")
    @Expose
    var strYoutube: String? = null,
    @SerializedName("strRSS")
    @Expose
    var strRSS: String? = null,
    @SerializedName("strDescriptionEN")
    @Expose
    var strDescriptionEN: String? = null,
    @SerializedName("strDescriptionDE")
    @Expose
    var strDescriptionDE: Any? = null,
    @SerializedName("strDescriptionFR")
    @Expose
    var strDescriptionFR: String? = null,
    @SerializedName("strDescriptionIT")
    @Expose
    var strDescriptionIT: Any? = null,
    @SerializedName("strDescriptionCN")
    @Expose
    var strDescriptionCN: Any? = null,
    @SerializedName("strDescriptionJP")
    @Expose
    var strDescriptionJP: Any? = null,
    @SerializedName("strDescriptionRU")
    @Expose
    var strDescriptionRU: Any? = null,
    @SerializedName("strDescriptionES")
    @Expose
    var strDescriptionES: Any? = null,
    @SerializedName("strDescriptionPT")
    @Expose
    var strDescriptionPT: Any? = null,
    @SerializedName("strDescriptionSE")
    @Expose
    var strDescriptionSE: Any? = null,
    @SerializedName("strDescriptionNL")
    @Expose
    var strDescriptionNL: Any? = null,
    @SerializedName("strDescriptionHU")
    @Expose
    var strDescriptionHU: Any? = null,
    @SerializedName("strDescriptionNO")
    @Expose
    var strDescriptionNO: Any? = null,
    @SerializedName("strDescriptionPL")
    @Expose
    var strDescriptionPL: Any? = null,
    @SerializedName("strFanart1")
    @Expose
    var strFanart1: String? = null,
    @SerializedName("strFanart2")
    @Expose
    var strFanart2: String? = null,
    @SerializedName("strFanart3")
    @Expose
    var strFanart3: String? = null,
    @SerializedName("strFanart4")
    @Expose
    var strFanart4: String? = null,
    @SerializedName("strBanner")
    @Expose
    var strBanner: Any? = null,
    @SerializedName("strBadge")
    @Expose
    var strBadge: String? = null,
    @SerializedName("strLogo")
    @Expose
    var strLogo: String? = null,
    @SerializedName("strPoster")
    @Expose
    var strPoster: String? = null,
    @SerializedName("strTrophy")
    @Expose
    var strTrophy: String? = null,
    @SerializedName("strNaming")
    @Expose
    var strNaming: String? = null,
    @SerializedName("strLocked")
    @Expose
    var strLocked: String? = null)

data class LeagueDeatails(private val leagueDeatails: List<LeagueDetail>)