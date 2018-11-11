package app.submissions.dicoding.footballmatchschedule.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.TeamDetail
import app.submissions.dicoding.footballmatchschedule.adapters.InlineImageViewDataHolder
import app.submissions.dicoding.footballmatchschedule.adapters.InlineImageLabelRecyclerViewAdapter
import app.submissions.dicoding.footballmatchschedule.constants.Constants
import app.submissions.dicoding.footballmatchschedule.constants.League
import app.submissions.dicoding.footballmatchschedule.exts.gone
import app.submissions.dicoding.footballmatchschedule.exts.visible
import app.submissions.dicoding.footballmatchschedule.models.Team
import app.submissions.dicoding.footballmatchschedule.presenters.TeamsPresenter
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.TeamsBehavior
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.recycler_view.*
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity

class TeamsFragment : Fragment() {

  var leagueSubject: PublishSubject<Any>? = null
  private val teamDetailsData = mutableListOf<Team>()
  private val data = mutableListOf<InlineImageViewDataHolder>()
  private val adapter = InlineImageLabelRecyclerViewAdapter(data) {
    startActivity<TeamDetail>(Constants.TEAM_DATA to teamDetailsData[it])
  }
  private val presenter = TeamsPresenter(MyBehavior())

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
      inflater.inflate(R.layout.recycler_view, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    subscribeForSelectedLeague()
    presenter.getData(League.ENGLISH)
    rvRecyclerView.adapter = adapter
    rvRecyclerView.layoutManager = LinearLayoutManager(context)
  }

  private fun subscribeForSelectedLeague() {
    leagueSubject?.subscribe {
      Log.i(javaClass.name, it.toString())
      presenter.dispose()
      presenter.getData(it.toString())
    }?.isDisposed
  }

  inner class MyBehavior : TeamsBehavior() {

    override fun showData(teamDetails: List<Team>) {
      teamDetailsData.clear()
      teamDetailsData.addAll(teamDetails)

      data.clear()
      data.addAll(teamDetailsData.map { InlineImageViewDataHolder(it.strTeam ?: "", it.strTeamBadge ?: "") })

      adapter.notifyDataSetChanged()
    }

    override fun hideLoading() {
      shimmer?.stopShimmer()
      shimmer?.gone()
    }

    override fun showLoading() {
      shimmer?.startShimmer()
      shimmer?.visible()
    }

    override fun onError(msg: String) {
      alert(msg) { okButton { } }
    }
  }

}