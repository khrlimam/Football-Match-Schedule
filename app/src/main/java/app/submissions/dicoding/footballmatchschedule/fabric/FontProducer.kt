package app.submissions.dicoding.footballmatchschedule.fabric

import android.content.res.AssetManager
import android.graphics.Typeface
import app.submissions.dicoding.footballmatchschedule.constants.FontPath

object FontProducer {
  private var googleProductBoldFont: Typeface? = null
  private var googleProductRegularFont: Typeface? = null

  fun googleProductBoldFont(assets: AssetManager): Typeface? {
    if (googleProductBoldFont == null)
      googleProductBoldFont = Typeface.createFromAsset(assets, FontPath.GOOGLE_PRODUCT_BOLD)
    return googleProductBoldFont
  }

  fun googleProductRegularFont(assets: AssetManager): Typeface? {
    if (googleProductRegularFont == null)
      googleProductRegularFont = Typeface.createFromAsset(assets, FontPath.GOOGLE_PRODUCT_REGULAR)
    return googleProductRegularFont
  }

}