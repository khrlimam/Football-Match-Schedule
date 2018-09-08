package app.submissions.dicoding.footballmatchschedule

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.view.Menu
import android.view.MenuItem
import app.submissions.dicoding.footballmatchschedule.adapters.FragmentPagerAdapter
import app.submissions.dicoding.footballmatchschedule.constants.Constants
import app.submissions.dicoding.footballmatchschedule.db.tables.Favorites
import app.submissions.dicoding.footballmatchschedule.exts.*
import app.submissions.dicoding.footballmatchschedule.fragments.Lineups
import app.submissions.dicoding.footballmatchschedule.fragments.Timeline
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.models.holders.EItemType
import app.submissions.dicoding.footballmatchschedule.models.holders.FavoriteData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.see_detail.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.alert
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.okButton

class SeeDetail : AppCompatActivity(), AnkoLogger, AppBarLayout.OnOffsetChangedListener {

  private var collapsedMenu: Menu? = null
  private var isAppBarExpanded: Boolean = false
  private var isFavorite: Boolean = false
  private lateinit var id: String
  private var event: Event? = null
  private var processedFavoriteId: Long = -1L

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.see_detail)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    app_bar.addOnOffsetChangedListener(this)
    fab.setOnClickListener { toggleFavorite() }
    event = intent.getParcelableExtra(Constants.EVENT_DATA)

    val favoriteEvent = intent.getParcelableExtra<Favorites>(Constants.FAVORITE_DATA)
    if (favoriteEvent != null) {
      isFavorite = true
      id = favoriteEvent.id.toString()
      event = favoriteEvent.dataToObject().event
      toggleFabFavoriteIcon()
    }

    showData()
    setupTabs()
  }

  private fun setupTabs() {
    val timeline = Timeline()
    val lineups = Lineups()
    val bundle = Bundle()
    bundle.putParcelable(Constants.EVENT_DATA, event)
    timeline.arguments = bundle
    lineups.arguments = bundle
    val adapter = FragmentPagerAdapter(supportFragmentManager, listOf(
        FragmentPagerAdapter.FragmentData("Timeline", timeline),
        FragmentPagerAdapter.FragmentData("Lineup", lineups)
    ))
    tabContainer.adapter = adapter
    tabs.setupWithViewPager(tabContainer)
  }

  @SuppressLint("SetTextI18n")
  private fun showData() {
    event?.apply {

      winnerBanner {
        Glide.with(ctx)
            .asBitmap()
            .load(it)
            .listener(OnImageLoaded())
            .into(ivImgHeader)
      }

      tvAway.text = strAwayTeam
      tvAway.fontGoogleProductRegular()
      teamAwayBadge { ivAway.loadWithGlide(it) }

      tvHome.text = strHomeTeam
      tvHome.fontGoogleProductRegular()
      teamHomeBadge { ivHome.loadWithGlide(it) }

      tvTime.text = getTime()
      tvTime.fontGoogleProductRegular()

      tvDate.text = getFormattedDate()
      tvDate.fontGoogleProductRegular()

      tvLeague.text = strLeague
      tvLeague.fontGoogleProductRegular()

      tvScoreResult.text = "$intHomeScore : $intAwayScore"
      tvScoreResult.fontGoogleProductBold()

      ivImgHeader.startScaleAnimation()
    }
  }

  private fun toggleFavorite() {
    if (isFavorite)
      removeFromFavorite()
    else
      addToFavorite()
    toggleFabFavoriteIcon()
  }

  private fun toggleFabFavoriteIcon() {
    if (isFavorite)
      fab.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.un_favorite_border_color))
    else
      fab.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.add_favorite_border_color))
  }


  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
    if (!isAppBarExpanded && collapsedMenu?.size() != 1)
      collapsedMenu?.apply {
        if (!isFavorite)
          this.add(ADD_TO_FAVORITE)
              ?.setIcon(R.drawable.add_favorite_border_color)
              ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        else
          this.add(UN_FAVORITE)
              ?.setIcon(R.drawable.un_favorite_border_color)
              ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
      }
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
        try {
          addToFavorite()
          toggleFabFavoriteIcon()
        } catch (e: Exception) {
          alert(e.localizedMessage) {
            okButton { }
          }.show()
        }
        true
      }
      UN_FAVORITE -> {
        removeFromFavorite()
        toggleFabFavoriteIcon()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun addToFavorite() {
    try {
      database().use {
        val returnedId = insert(Favorites.TABLE_NAME,
            Favorites.DATA to event?.let { FavoriteData(it, EItemType.PAST).toJson() })
        id = returnedId.toString()
      }
      isFavorite = true
      snackbar(tabContainer, "Favorited!").show()
      processedFavoriteId = -1L
    } catch (e: Exception) {
      snackbar(tabContainer, e.localizedMessage).show()
    }

  }

  private fun removeFromFavorite() {
    try {
      database().use {
        delete(Favorites.TABLE_NAME, "(${Favorites.ID} = {id})",
            "id" to id)
      }
      isFavorite = false
      processedFavoriteId = id.toLong()
      snackbar(tabContainer, "Removed from favorite!")
    } catch (e: Exception) {
      snackbar(tabContainer, e.localizedMessage).show()
    }
  }

  override fun onBackPressed() {
    intent = Intent()
    intent.putExtra(app.submissions.dicoding.footballmatchschedule.Favorites.FAVORITE_ID, processedFavoriteId)
    setResult(Activity.RESULT_OK, intent)
    finish()
  }

  override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
    if (Math.abs(verticalOffset) - app_bar.totalScrollRange == 0) {
      isAppBarExpanded = false
      toolbar_layout.title = event?.simpleWinnerDescription()
      invalidateOptionsMenu()
    } else {
      toolbar_layout.title = ""
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
        Blurry.with(ctx).async().from(resource).into(ivImgHeader)
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
    const val UN_FAVORITE: String = "Un favorite"
  }

}
