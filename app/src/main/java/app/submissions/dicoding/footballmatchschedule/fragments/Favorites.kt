package app.submissions.dicoding.footballmatchschedule.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.submissions.dicoding.footballmatchschedule.NextMatchDetail
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.PreviousMatchDetail
import app.submissions.dicoding.footballmatchschedule.TeamDetail
import app.submissions.dicoding.footballmatchschedule.adapters.FavoritesAdapter
import app.submissions.dicoding.footballmatchschedule.constants.Constants
import app.submissions.dicoding.footballmatchschedule.db.tables.Favorites
import app.submissions.dicoding.footballmatchschedule.exts.database
import app.submissions.dicoding.footballmatchschedule.exts.gone
import kotlinx.android.synthetic.main.recycler_view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.intentFor

class Favorites : Fragment() {

  private var favorites = mutableListOf<Favorites>()

  private val adapter: FavoritesAdapter = FavoritesAdapter(favorites) {
    when (favorites[it].type) {
      Favorites.ItemType.PAST -> startActivityForResult(intentFor<PreviousMatchDetail>(Constants.FAVORITE_DATA to favorites[it]), FAVORITE_DETAIL)
      Favorites.ItemType.NEXT -> startActivityForResult(intentFor<NextMatchDetail>(Constants.FAVORITE_DATA to favorites[it]), FAVORITE_DETAIL)
      Favorites.ItemType.TEAM -> startActivityForResult(intentFor<TeamDetail>(Constants.FAVORITE_DATA to favorites[it]), FAVORITE_DETAIL)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.favorites, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    rvRecyclerView.adapter = adapter
    rvRecyclerView.layoutManager = LinearLayoutManager(context)
    shimmer.stopShimmer()
    shimmer.gone()
    database()?.use {
      val elements = select(Favorites.TABLE_NAME).parseList(classParser<Favorites>())
      favorites.clear()
      favorites.addAll(elements)
      adapter.notifyDataSetChanged()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (resultCode != Activity.RESULT_OK) return
    if (requestCode == FAVORITE_DETAIL) {
      val favoriteId = data?.getLongExtra(FAVORITE_ID, -1)
      val returnedObject = favorites.withIndex().firstOrNull { it.value.id == favoriteId }
      returnedObject?.let {
        favorites.removeAt(it.index)
        adapter.notifyDataSetChanged()
      }
    }
  }


  companion object {
    const val FAVORITE_DETAIL = 0
    const val FAVORITE_ID = "Processed Favorite ID"
  }

}