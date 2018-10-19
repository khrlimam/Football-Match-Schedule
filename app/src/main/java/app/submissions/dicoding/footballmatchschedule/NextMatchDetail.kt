package app.submissions.dicoding.footballmatchschedule

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import app.submissions.dicoding.footballmatchschedule.constants.Constants
import app.submissions.dicoding.footballmatchschedule.db.tables.Favorites
import app.submissions.dicoding.footballmatchschedule.exts.*
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.models.holders.EItemType
import app.submissions.dicoding.footballmatchschedule.models.holders.FavoriteData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.next_match_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.okButton

class NextMatchDetail : AppCompatActivity() {

  var event: Event? = null

  private var isFavorite: Boolean = false
  private lateinit var id: String
  private var processedFavoriteId: Long = -1L

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.next_match_detail)

    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.title = ""

    event = intent.getParcelableExtra(Constants.EVENT_DATA)
    val favoriteEvent = intent.getParcelableExtra<Favorites>(Constants.FAVORITE_DATA)
    if (favoriteEvent != null) {
      id = favoriteEvent.id.toString()
      event = favoriteEvent.dataToObject().event
      isFavorite = true
    }
    viewContent()
  }

  override fun invalidateOptionsMenu() {
    toggleIcon()
    super.invalidateOptionsMenu()
  }

  private fun toggleIcon() {
    if (isFavorite)
      menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.un_favorite_border_color)
    else
      menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.add_favorite_border_color)
  }

  private fun viewContent() {
    tvTime.fontGoogleProductRegular()
    tvLeague.fontGoogleProductRegular()
    tvScoreResult.fontGoogleProductBold()
    tvAway.fontGoogleProductRegular()
    tvHome.fontGoogleProductRegular()

    event?.apply {
      winnerBanner {
        Glide.with(ctx)
            .asBitmap()
            .load(it)
            .listener(OnImageLoaded())
            .into(ivBackground)
      }
      teamAwayBadge { ivAway.loadWithGlide(it) }
      teamHomeBadge { ivHome.loadWithGlide(it) }
      tvTime.text = localTime()
      tvDate.text = localDateWithDayName()
      tvLeague.text = strLeague
      tvHome.text = strHomeTeam
      tvAway.text = strAwayTeam
    }
  }

  private fun addToFavorite() {
    try {
      database().use {
        val returnedId = insert(Favorites.TABLE_NAME,
            Favorites.DATA to event?.let { FavoriteData(it, EItemType.NEXT).toJson() })
        id = returnedId.toString()
      }
      isFavorite = true
      snackbar(clContainer, "Favorited!").show()
      processedFavoriteId = -1L
    } catch (e: Exception) {
      snackbar(clContainer, e.localizedMessage).show()
    }

  }

  private fun removeFromFavorite() {
    try {
      database().use {
        delete(Favorites.TABLE_NAME, "(${Favorites.ID} = {id})",
            "id" to id)
      }
      isFavorite = false
      snackbar(clContainer, "Removed from favorite!")
      processedFavoriteId = id.toLong()
    } catch (e: Exception) {
      snackbar(clContainer, e.localizedMessage).show()
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.favorite_item -> toggleFavorite()
      android.R.id.home -> onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  private fun toggleFavorite() {
    try {
      if (isFavorite) removeFromFavorite() else addToFavorite()
    } catch (e: java.lang.Exception) {
      alert(e.localizedMessage).okButton { }
    }
  }

  private var menu: Menu? = null

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.favorite_menu, menu)
    this.menu = menu
    invalidateOptionsMenu()
    return super.onCreateOptionsMenu(menu)
  }

  override fun onBackPressed() {
    intent = Intent()
    intent.putExtra(app.submissions.dicoding.footballmatchschedule.Favorites.FAVORITE_ID, processedFavoriteId)
    setResult(Activity.RESULT_OK, intent)
    finish()
  }

  inner class OnImageLoaded : RequestListener<Bitmap> {
    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
      return false
    }

    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
      resource?.let { bitmap ->
        Blurry.with(ctx).async().from(bitmap).into(ivBackground)
        ivBackground.startScaleAnimation()
      }
      return false
    }

  }
}