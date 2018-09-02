package app.submissions.dicoding.footballmatchschedule

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.view.Menu
import android.view.MenuItem
import app.submissions.dicoding.footballmatchschedule.adapters.FragmentPagerAdapter
import app.submissions.dicoding.footballmatchschedule.exts.fontGoogleProductBold
import app.submissions.dicoding.footballmatchschedule.exts.fontGoogleProductRegular
import app.submissions.dicoding.footballmatchschedule.exts.loadWithGlide
import app.submissions.dicoding.footballmatchschedule.exts.startScaleAnimation
import app.submissions.dicoding.footballmatchschedule.fragments.Lineups
import app.submissions.dicoding.footballmatchschedule.fragments.Timeline
import app.submissions.dicoding.footballmatchschedule.models.Event
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.seed_detail.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.ctx
import org.jetbrains.anko.info

class SeeDetail : AppCompatActivity(), AnkoLogger, AppBarLayout.OnOffsetChangedListener {

  private var collapsedMenu: Menu? = null
  private var isAppBarExpanded: Boolean = false
  private lateinit var event: Event

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.seed_detail)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    app_bar.addOnOffsetChangedListener(this)
    fab.setOnClickListener { toggleFavorite() }
    event = intent.getParcelableExtra(BuildConfig.EVENT_DATA)
    showData()
    setupTabs()
  }

  private fun setupTabs() {
    val timeline = Timeline()
    val lineups = Lineups()
    val bundle = Bundle()
    bundle.putParcelable(BuildConfig.EVENT_DATA, event)
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
    event.winnerBanner {
      Glide.with(this)
          .asBitmap()
          .load(it)
          .listener(OnImageLoaded())
          .into(ivImgHeader)
    }

    tvAway.text = event.strAwayTeam
    tvAway.fontGoogleProductRegular()
    event.teamAwayBadge { ivAway.loadWithGlide(it) }

    tvHome.text = event.strHomeTeam
    tvHome.fontGoogleProductRegular()
    event.teamHomeBadge { ivHome.loadWithGlide(it) }

    tvTime.text = event.getTime()
    tvTime.fontGoogleProductRegular()

    tvDate.text = event.getFormattedDate()
    tvDate.fontGoogleProductRegular()

    tvLeague.text = event.strLeague
    tvLeague.fontGoogleProductRegular()

    tvScoreResult.text = "${event.intHomeScore} : ${event.intAwayScore}"
    tvScoreResult.fontGoogleProductBold()

    ivImgHeader.startScaleAnimation()
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
      UN_FAVORITE -> {
        info(UN_FAVORITE)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
    if (Math.abs(verticalOffset) - app_bar.totalScrollRange == 0) {
      isAppBarExpanded = false
      toolbar_layout.title = event.simpleWinnerDestiption()
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
