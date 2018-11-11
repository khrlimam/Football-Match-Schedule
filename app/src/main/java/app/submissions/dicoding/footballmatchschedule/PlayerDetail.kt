package app.submissions.dicoding.footballmatchschedule

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.view.MenuItem
import app.submissions.dicoding.footballmatchschedule.adapters.GlideCustomImageHandler
import app.submissions.dicoding.footballmatchschedule.constants.Constants
import app.submissions.dicoding.footballmatchschedule.models.Player
import com.bumptech.glide.Glide
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.see_player.*
import org.jetbrains.anko.ctx

class PlayerDetail : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

  override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
    if (Math.abs(verticalOffset) - app_bar.totalScrollRange == 0) toolbar_layout.title = "Profile"
    else toolbar_layout.title = ""
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.see_player)
    setSupportActionBar(toolbar)
    app_bar.addOnOffsetChangedListener(this)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    val player = intent.getParcelableExtra<Player>(Constants.PLAYER_DATA)
    player.apply {
      tvPlayerName.text = strPlayer
      tvPlayerPosition.text = strPosition
      tvHeight.text = strHeight?.split(" ")?.get(0)
      tvWeight.text = strWeight?.split(" ")?.get(0)
      tvDescription.text = strDescriptionEN
      Glide.with(ctx)
          .load(strCutout)
          .into(fbProfilePicture)
      Glide.with(ctx)
          .asBitmap()
          .load(strThumb
              ?: "https://images.pexels.com/photos/274506/pexels-photo-274506.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260")
          .listener(GlideCustomImageHandler {
            it?.let { bitmap ->
              Blurry.with(ctx).async().from(bitmap).into(ivImgHeader)
              Palette.from(bitmap).generate { palette ->
                val color = palette.getMutedColor(ContextCompat.getColor(ctx, R.color.colorAccent))
                toolbar_layout.setContentScrimColor(color)
              }
            }
          })
          .into(ivImgHeader)
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return super.onOptionsItemSelected(item)
  }
}