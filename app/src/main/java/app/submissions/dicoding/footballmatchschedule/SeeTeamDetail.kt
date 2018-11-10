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
import app.submissions.dicoding.footballmatchschedule.adapters.GlideCustomImageHandler
import app.submissions.dicoding.footballmatchschedule.constants.Constants
import app.submissions.dicoding.footballmatchschedule.db.tables.Favorites
import app.submissions.dicoding.footballmatchschedule.exts.database
import app.submissions.dicoding.footballmatchschedule.exts.fontGoogleProductRegular
import app.submissions.dicoding.footballmatchschedule.exts.loadWithGlide
import app.submissions.dicoding.footballmatchschedule.exts.startScaleAnimation
import app.submissions.dicoding.footballmatchschedule.fabric.GsonFabric
import app.submissions.dicoding.footballmatchschedule.fragments.TeamMatches
import app.submissions.dicoding.footballmatchschedule.fragments.TeamOverview
import app.submissions.dicoding.footballmatchschedule.fragments.TeamPlayers
import app.submissions.dicoding.footballmatchschedule.models.Team
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.team_detail.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.design.snackbar

class SeeTeamDetail : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

  private var collapsedMenu: Menu? = null
  private var isAppBarExpanded: Boolean = false
  private var isFavorite: Boolean = false
  private lateinit var id: String
  private var teamDetail: Team? = null
  private var processedFavoriteId: Long = -1L

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.team_detail)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    app_bar.addOnOffsetChangedListener(this)
    fab.setOnClickListener { toggleFavorite() }
    teamDetail = intent.getParcelableExtra(Constants.TEAM_DATA)

    val favoriteEvent = intent.getParcelableExtra<Favorites>(Constants.FAVORITE_DATA)
    if (favoriteEvent != null) {
      isFavorite = true
      id = favoriteEvent.id.toString()
      teamDetail = GsonFabric.build.fromJson<Team>(favoriteEvent.data, Class.forName(favoriteEvent.className))
    }

    showData()
    setupTabs()
  }

  @SuppressLint("SetTextI18n")
  private fun showData() {
    Glide.with(this)
        .asBitmap()
        .load(teamDetail?.strTeamFanart2
            ?: "https://images.pexels.com/photos/274506/pexels-photo-274506.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260")
        .listener(GlideCustomImageHandler {
          it?.let { bitmap ->
            Blurry.with(ctx).async().from(bitmap).into(ivImgHeader)
            Palette.from(bitmap).generate { palette ->
              val color = palette.getMutedColor(ContextCompat.getColor(ctx, R.color.colorAccent))
              toolbar_layout.setContentScrimColor(color)
              ivImgHeader.startScaleAnimation()
            }
          }
        }).into(ivImgHeader)

    teamDetail?.apply {
      ivTeamBadge.loadWithGlide(strTeamBadge ?: "")
      tvClubName.text = strTeam
      tvSince.text = "Since $intFormedYear"

      tvClubName.fontGoogleProductRegular()
      tvSince.fontGoogleProductRegular()
    }
  }

  private fun setupTabs() {
    val teamMatches = TeamMatches()
    val teamPlayers = TeamPlayers()
    val teamOverview = TeamOverview()
    val bundle = Bundle()
    bundle.putParcelable(Constants.TEAM_DATA, teamDetail)
    teamMatches.arguments = bundle
    teamPlayers.arguments = bundle
    teamOverview.arguments = bundle
    val adapter = FragmentPagerAdapter(supportFragmentManager, listOf(
        FragmentPagerAdapter.FragmentData("Overview", teamOverview),
        FragmentPagerAdapter.FragmentData("Players", teamPlayers),
        FragmentPagerAdapter.FragmentData("Matches", teamMatches)
    ))
    tabContainer.adapter = adapter
    tabs.setupWithViewPager(tabContainer)
  }

  private fun toggleFavorite() {
    if (isFavorite)
      removeFromFavorite()
    else
      addToFavorite()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.favorite_menu, menu)
    collapsedMenu = menu
    toggleFavoriteIcon()
    return super.onCreateOptionsMenu(menu)
  }

  private fun toggleFavoriteIcon() {
    if (isFavorite) {
      fab.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.un_favorite_border_color))
      collapsedMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.un_favorite_border_color)
    } else {
      fab.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.add_favorite_border_color))
      collapsedMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.add_favorite_border_color)
    }
  }

  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
    collapsedMenu?.getItem(0)?.isVisible = !isAppBarExpanded
    return super.onPrepareOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.favorite_item -> toggleFavorite()
      android.R.id.home -> onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  private fun addToFavorite() {
    try {
      database().use {
        val returnedId = insert(Favorites.TABLE_NAME,
            Favorites.DATA to teamDetail?.toJson(),
            Favorites.TYPE to Favorites.ItemType.TEAM,
            Favorites.CLASS_NAME to Team::class.java.name)
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
      toolbar_layout.title = teamDetail?.strTeam
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

}
