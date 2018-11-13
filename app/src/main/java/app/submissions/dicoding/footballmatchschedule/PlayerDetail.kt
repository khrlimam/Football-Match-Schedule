package app.submissions.dicoding.footballmatchschedule

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.view.MenuItem
import android.widget.ImageView
import app.submissions.dicoding.footballmatchschedule.constants.Constants
import app.submissions.dicoding.footballmatchschedule.exts.loadImageUrlAsBitmap
import app.submissions.dicoding.footballmatchschedule.exts.loadWithGlide
import app.submissions.dicoding.footballmatchschedule.models.Player
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.see_player.*
import org.jetbrains.anko.ctx

class PlayerDetail : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

  private var player: Player? = null

  override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
    if (Math.abs(verticalOffset) - app_bar.totalScrollRange == 0) toolbar_layout.title = player?.strPlayer ?: getString(R.string.profile)
    else toolbar_layout.title = ""
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.see_player)
    setSupportActionBar(toolbar)
    app_bar.addOnOffsetChangedListener(this)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    player = intent.getParcelableExtra(Constants.PLAYER_DATA)
    player?.apply {
      tvPlayerName.text = strPlayer
      tvPlayerPosition.text = strPosition
      tvHeight.text = strHeight?.split(" ")?.get(0)
      tvWeight.text = strWeight?.split(" ")?.get(0)
      tvDescription.text = strDescriptionEN
      fbProfilePicture as ImageView
      fbProfilePicture.loadWithGlide(strCutout)
      ivImgHeader.loadImageUrlAsBitmap(strThumb) {
        it?.let { bitmap ->
          Blurry.with(ctx).async().from(bitmap).into(ivImgHeader)
          Palette.from(bitmap).generate { palette ->
            val color = palette.getMutedColor(ContextCompat.getColor(ctx, R.color.colorAccent))
            toolbar_layout.setContentScrimColor(color)
          }
        }
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return super.onOptionsItemSelected(item)
  }
}