package app.submissions.dicoding.footballmatchschedule

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import app.submissions.dicoding.footballmatchschedule.adapters.GlideCustomImageHandler
import app.submissions.dicoding.footballmatchschedule.constants.Constants
import app.submissions.dicoding.footballmatchschedule.db.tables.Favorites
import app.submissions.dicoding.footballmatchschedule.exts.*
import app.submissions.dicoding.footballmatchschedule.models.Event
import com.bumptech.glide.Glide
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.next_match_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.okButton
import java.util.*

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
      event = favoriteEvent.dataToObject() as Event?
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
            .listener(GlideCustomImageHandler { bitmap ->
              Blurry.with(ctx).async().from(bitmap).into(ivBackground)
              ivBackground.startScaleAnimation()
            })
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
            Favorites.DATA to event?.toJson(),
            Favorites.TYPE to Favorites.ItemType.NEXT,
            Favorites.CLASS_NAME to Event::class.java.name)

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
      R.id.add_to_calendar -> addToCalendar()
    }
    return super.onOptionsItemSelected(item)
  }

  private fun addToCalendar() {
    val beginCalendar = Calendar.getInstance()
    beginCalendar.time = event?.strDateTime()?.toDate()
    val endCalendar = Calendar.getInstance()
    endCalendar.time = event?.strDateTime()?.toDate()
    val beginTime = beginCalendar.timeInMillis
    endCalendar.add(Calendar.HOUR_OF_DAY, 1)
    val endTime = endCalendar.timeInMillis

    val intent = Intent(Intent.ACTION_EDIT)
        .setType("vnd.android.cursor.item/event")
    intent.putExtra(CalendarContract.Events.TITLE, event?.strEvent)
    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
    intent.putExtra(CalendarContract.Events.ALL_DAY, false)
    intent.putExtra(CalendarContract.Events.DESCRIPTION, "Watching Football match between ${event?.strEvent}")
    startActivity(intent)
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
    menuInflater.inflate(R.menu.next_match_menu, menu)
    this.menu = menu
    invalidateOptionsMenu()
    return super.onCreateOptionsMenu(menu)
  }

  override fun onBackPressed() {
    intent = Intent()
    intent.putExtra(app.submissions.dicoding.footballmatchschedule.fragments.Favorites.FAVORITE_ID, processedFavoriteId)
    setResult(Activity.RESULT_OK, intent)
    finish()
  }
}