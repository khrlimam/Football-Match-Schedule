package app.submissions.dicoding.footballmatchschedule.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.submissions.dicoding.footballmatchschedule.NextMatchDetail
import app.submissions.dicoding.footballmatchschedule.PreviousMatchDetail
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.adapters.FavoritesAdapter
import app.submissions.dicoding.footballmatchschedule.constants.Constants
import app.submissions.dicoding.footballmatchschedule.db.tables.Favorites
import app.submissions.dicoding.footballmatchschedule.exts.gone
import app.submissions.dicoding.footballmatchschedule.exts.visible
import app.submissions.dicoding.footballmatchschedule.models.Team
import app.submissions.dicoding.footballmatchschedule.presenters.TeamMatchesPresenter
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.TeamMatchesBehavior
import kotlinx.android.synthetic.main.recycler_view_with_context_bg.*
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity

class TeamMatches : Fragment() {

  val data: MutableList<Favorites> = mutableListOf()
  private val adapter: FavoritesAdapter = FavoritesAdapter(data) {
    when (data[it].type) {
      Favorites.ItemType.PAST -> startActivity<PreviousMatchDetail>(Constants.EVENT_DATA to data[it].dataToObject())
      Favorites.ItemType.NEXT -> startActivity<NextMatchDetail>(Constants.EVENT_DATA to data[it].dataToObject())
    }
  }
  private val presenter: TeamMatchesPresenter = TeamMatchesPresenter(MyBehavior())

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.recycler_view_with_context_bg, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    rvRecyclerView.adapter = adapter
    rvRecyclerView.layoutManager = LinearLayoutManager(context)
    val team = arguments?.getParcelable<Team>(Constants.TEAM_DATA)
    presenter.getData(team?.idTeam ?: "")
  }

  override fun onPause() {
    presenter.dispose()
    super.onPause()
  }

  override fun onStop() {
    presenter.dispose()
    super.onStop()
  }

  inner class MyBehavior : TeamMatchesBehavior() {
    override fun showData(results: List<Favorites>) {
      data.clear()
      data.addAll(results)
      adapter.notifyDataSetChanged()
    }

    override fun onError(msg: String) {
      alert(msg) { okButton { } }
    }

    override fun hideLoading() {
      shimmer.gone()
    }

    override fun showLoading() {
      shimmer.visible()
    }

  }


}
