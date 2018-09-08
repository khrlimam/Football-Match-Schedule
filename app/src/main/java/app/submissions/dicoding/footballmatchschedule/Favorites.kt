package app.submissions.dicoding.footballmatchschedule

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import app.submissions.dicoding.footballmatchschedule.adapters.FavoritesAdapter
import app.submissions.dicoding.footballmatchschedule.constants.Constants
import app.submissions.dicoding.footballmatchschedule.db.tables.Favorites
import app.submissions.dicoding.footballmatchschedule.exts.database
import app.submissions.dicoding.footballmatchschedule.exts.gone
import app.submissions.dicoding.footballmatchschedule.models.holders.ItemType
import kotlinx.android.synthetic.main.favorites.*
import kotlinx.android.synthetic.main.recycler_view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor

class Favorites : AppCompatActivity(), AnkoLogger {

  private var favorites = mutableListOf<Favorites>()

  private val adapter: FavoritesAdapter = FavoritesAdapter(favorites) {
    when (it.dataToObject().itemType.field) {
      ItemType.PAST -> startActivityForResult(intentFor<SeeDetail>(Constants.FAVORITE_DATA to it), FAVORITE_DETAIL)
      ItemType.NEXT -> startActivityForResult(intentFor<NextMatchDetail>(Constants.FAVORITE_DATA to it), FAVORITE_DETAIL)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.favorites)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    rvRecyclerView.adapter = adapter
    rvRecyclerView.layoutManager = LinearLayoutManager(this)
    shimmer.stopShimmer()
    shimmer.gone()
    database().use {
      val elements = select(Favorites.TABLE_NAME).parseList(classParser<Favorites>())
      favorites.clear()
      favorites.addAll(elements)
      adapter.notifyDataSetChanged()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (resultCode != Activity.RESULT_OK) {
      info("result is not ok")
      return
    }
    if (requestCode == FAVORITE_DETAIL) {
      val favoriteId = data?.getLongExtra(FAVORITE_ID, -1)
      val returnedObject = favorites.withIndex().firstOrNull { it.value.id == favoriteId }
      returnedObject?.let {
        favorites.removeAt(it.index)
        adapter.notifyDataSetChanged()
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home)
      onBackPressed()
    return super.onOptionsItemSelected(item)
  }

  companion object {
    const val FAVORITE_DETAIL = 0
    const val FAVORITE_ID = "Processed Favorite ID"
  }

}