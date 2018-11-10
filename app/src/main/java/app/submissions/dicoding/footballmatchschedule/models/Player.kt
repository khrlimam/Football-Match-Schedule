package app.submissions.dicoding.footballmatchschedule.models

import android.os.Parcelable
import app.submissions.dicoding.footballmatchschedule.fabric.GsonFabric
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Player(
    @SerializedName("idPlayer")
    @Expose
    var idPlayer: String?,
    @SerializedName("idTeam")
    @Expose
    var idTeam: String?,
    @SerializedName("idSoccerXML")
    @Expose
    var idSoccerXML: String?,
    @SerializedName("idPlayerManager")
    @Expose
    var idPlayerManager: String?,
    @SerializedName("strNationality")
    @Expose
    var strNationality: String?,
    @SerializedName("strPlayer")
    @Expose
    var strPlayer: String?,
    @SerializedName("strTeam")
    @Expose
    var strTeam: String?,
    @SerializedName("strSport")
    @Expose
    var strSport: String?,
    @SerializedName("intSoccerXMLTeamID")
    @Expose
    var intSoccerXMLTeamID: String?,
    @SerializedName("dateBorn")
    @Expose
    var dateBorn: String?,
    @SerializedName("dateSigned")
    @Expose
    var dateSigned: String?,
    @SerializedName("strSigning")
    @Expose
    var strSigning: String?,
    @SerializedName("strWage")
    @Expose
    var strWage: String?,
    @SerializedName("strBirthLocation")
    @Expose
    var strBirthLocation: String?,
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
    @SerializedName("strPosition")
    @Expose
    var strPosition: String?,
    @SerializedName("strCollege")
    @Expose
    var strCollege: String?,
    @SerializedName("strFacebook")
    @Expose
    var strFacebook: String?,
    @SerializedName("strWebsite")
    @Expose
    var strWebsite: String?,
    @SerializedName("strTwitter")
    @Expose
    var strTwitter: String?,
    @SerializedName("strInstagram")
    @Expose
    var strInstagram: String?,
    @SerializedName("strYoutube")
    @Expose
    var strYoutube: String?,
    @SerializedName("strHeight")
    @Expose
    var strHeight: String?,
    @SerializedName("strWeight")
    @Expose
    var strWeight: String?,
    @SerializedName("intLoved")
    @Expose
    var intLoved: String?,
    @SerializedName("strThumb")
    @Expose
    var strThumb: String?,
    @SerializedName("strCutout")
    @Expose
    var strCutout: String?,
    @SerializedName("strBanner")
    @Expose
    var strBanner: String?,
    @SerializedName("strFanart1")
    @Expose
    var strFanart1: String?,
    @SerializedName("strFanart2")
    @Expose
    var strFanart2: String?,
    @SerializedName("strFanart3")
    @Expose
    var strFanart3: String?,
    @SerializedName("strFanart4")
    @Expose
    var strFanart4: String?,
    @SerializedName("strLocked")
    @Expose
    var strLocke: String?
) : Parcelable {
  fun toJson(): String? = GsonFabric.build.toJson(this)
}

class Players(var player: List<Player>)