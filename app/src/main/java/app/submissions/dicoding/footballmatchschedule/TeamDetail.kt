package app.submissions.dicoding.footballmatchschedule

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
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
import app.submissions.dicoding.footballmatchschedule.fragments.TeamMatches
import app.submissions.dicoding.footballmatchschedule.fragments.TeamOverview
import app.submissions.dicoding.footballmatchschedule.fragments.TeamPlayers
import app.submissions.dicoding.footballmatchschedule.models.Team
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.team_detail.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.design.snackbar

class TeamDetail : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

  private var collapsedMenu: Menu? = null
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
    teamDetail = intent.getParcelableExtra(Constants.TEAM_DATA)

    val favoriteTeam = intent.getParcelableExtra<Favorites>(Constants.FAVORITE_DATA)
    if (favoriteTeam != null) {
      isFavorite = true
      id = favoriteTeam.id.toString()
      teamDetail = favoriteTeam.dataToObject() as Team
    }

    showData()
    setupTabs()
  }

  @SuppressLint("SetTextI18n")
  private fun showData() {
    ivImgHeader.loadImageUrlAsBitmap(teamDetail?.strTeamFanart2) {
      it?.let { bitmap ->
        Blurry.with(ctx).async().from(bitmap).into(ivImgHeader)
        Palette.from(bitmap).generate { palette ->
          val color = palette.getMutedColor(ContextCompat.getColor(ctx, R.color.colorAccent))
          toolbar_layout.setContentScrimColor(color)
          ivImgHeader.startScaleAnimation()
        }
      }
    }

    teamDetail?.apply {
      ivTeamBadge.loadWithGlide(strTeamBadge)
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
        FragmentPagerAdapter.FragmentData(getString(R.string.overview), teamOverview),
        FragmentPagerAdapter.FragmentData(getString(R.string.players), teamPlayers),
        FragmentPagerAdapter.FragmentData(getString(R.string.matches), teamMatches)
    ))

    tabContainer.adapter = adapter
    tabContainer.offscreenPageLimit = 3
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
      collapsedMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.un_favorite_border_color)
    } else {
      collapsedMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.add_favorite_border_color)
    }
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
      snackbar(tabContainer, getString(R.string.favorited)).show()
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
      snackbar(tabContainer, getString(R.string.removed_from_favorite))
    } catch (e: Exception) {
      snackbar(tabContainer, e.localizedMessage).show()
    }
  }

  override fun onBackPressed() {
    intent = Intent()
    intent.putExtra(app.submissions.dicoding.footballmatchschedule.fragments.Favorites.FAVORITE_ID, processedFavoriteId)
    setResult(Activity.RESULT_OK, intent)
    finish()
  }

  override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
    if (Math.abs(verticalOffset) - app_bar.totalScrollRange == 0) {
      toolbar_layout.title = teamDetail?.strTeam
      invalidateOptionsMenu()
    } else {
      toolbar_layout.title = ""
      invalidateOptionsMenu()
    }
  }

}