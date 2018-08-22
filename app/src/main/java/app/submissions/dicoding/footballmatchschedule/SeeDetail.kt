package app.submissions.dicoding.footballmatchschedule

import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.view.Menu
import android.view.MenuItem
import app.submissions.dicoding.footballmatchschedule.exts.startScaleAnimation
import app.submissions.dicoding.footballmatchschedule.models.Event
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.seed_detail.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.ctx
import org.jetbrains.anko.info

class SeeDetail : AppCompatActivity(), AnkoLogger, AppBarLayout.OnOffsetChangedListener {

  private var collapsedMenu: Menu? = null
  private var isAppBarExpanded: Boolean = false
  private lateinit var schedule: Event

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.seed_detail)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    schedule = intent.getParcelableExtra(BuildConfig.EVENT_DATA)
    schedule.winnerBanner {
      Glide.with(this)
          .asBitmap()
          .load(it)
          .listener(OnImageLoaded())
          .thumbnail(.1f)
          .into(ivImgHeader)
    }
    ivImgHeader.startScaleAnimation()
    toolbar_layout.title = schedule.strEvent

    app_bar.addOnOffsetChangedListener(this)
    fab.setOnClickListener { toggleFavorite() }
  }

  private fun toggleFavorite() {
    info("Toggling favorite")
  }

  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
    if (!isAppBarExpanded && collapsedMenu?.size() != 1)
      collapsedMenu
          ?.add(ADD_TO_FAVORITE)
          ?.setIcon(R.drawable.add_favorite_border_color)
          ?.setOnMenuItemClickListener {
            info(it.title)
            return@setOnMenuItemClickListener (true)
          }
          ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
    return super.onPrepareOptionsMenu(menu)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_scrolling, menu)
    collapsedMenu = menu
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home)
      onBackPressed()

    return when (item?.title) {
      ADD_TO_FAVORITE -> {
        info(ADD_TO_FAVORITE)
        true
      }
      UNFAVORITE -> {
        info(UNFAVORITE)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
    if (Math.abs(verticalOffset) - app_bar.totalScrollRange == 0) {
      isAppBarExpanded = false
      invalidateOptionsMenu()
    } else {
      isAppBarExpanded = true
      invalidateOptionsMenu()
    }
  }

  inner class OnImageLoaded : RequestListener<Bitmap> {
    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
      return false
    }

    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
      resource?.let { bitmap ->
        Palette.from(bitmap).generate {
          val color = it.getMutedColor(ContextCompat.getColor(ctx, R.color.colorAccent))
          toolbar_layout.setContentScrimColor(color)
        }
      }
      return false
    }

  }

  companion object {
    const val ADD_TO_FAVORITE: String = "Add to favorite"
    const val UNFAVORITE: String = "Unfavorite"

  }

}
