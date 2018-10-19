package app.submissions.dicoding.footballmatchschedule.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.adapters.InlineImageLabelRecyclerViewAdapter
import app.submissions.dicoding.footballmatchschedule.constants.League
import app.submissions.dicoding.footballmatchschedule.exts.gone
import app.submissions.dicoding.footballmatchschedule.exts.visible
import app.submissions.dicoding.footballmatchschedule.models.TeamDetail
import app.submissions.dicoding.footballmatchschedule.presenters.TeamsPresenter
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.TeamsBehavior
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.recycler_view.*
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert

class TeamsFragment : Fragment() {

  var leagueSubject: PublishSubject<Any>? = null
  private val teamDetailsData = mutableListOf<TeamDetail>()
  private val adapter = InlineImageLabelRecyclerViewAdapter(teamDetailsData)
  private val presenter = TeamsPresenter(MyBehavior())

  private var myView: View? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    if (myView == null)
      myView = inflater.inflate(R.layout.teams_fragment, container, false)
    return myView
  }

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

    override fun showData(teamDetails: List<TeamDetail>) {
      teamDetailsData.clear()
      teamDetailsData.addAll(teamDetails)
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