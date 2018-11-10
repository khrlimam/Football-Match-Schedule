package app.submissions.dicoding.footballmatchschedule.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.SeePlayerDetail
import app.submissions.dicoding.footballmatchschedule.adapters.InlineImageLabelRecyclerViewAdapter
import app.submissions.dicoding.footballmatchschedule.adapters.InlineImageViewDataHolder
import app.submissions.dicoding.footballmatchschedule.constants.Constants
import app.submissions.dicoding.footballmatchschedule.exts.gone
import app.submissions.dicoding.footballmatchschedule.exts.visible
import app.submissions.dicoding.footballmatchschedule.models.Player
import app.submissions.dicoding.footballmatchschedule.models.Team
import app.submissions.dicoding.footballmatchschedule.presenters.TeamPlayersPresenter
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.TeamPlayersBehavior
import kotlinx.android.synthetic.main.recycler_view.*
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity

class TeamPlayers : Fragment() {

  private val presenter: TeamPlayersPresenter = TeamPlayersPresenter(MyBehavior())
  private val teamsData: MutableList<Player> = mutableListOf()
  private val data: MutableList<InlineImageViewDataHolder> = mutableListOf()
  private val adapter = InlineImageLabelRecyclerViewAdapter(data) {
    startActivity<SeePlayerDetail>()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.recycler_view, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val team = arguments?.getParcelable<Team>(Constants.TEAM_DATA)
    team?.idTeam?.let { presenter.getData(it) }
    rvRecyclerView.adapter = adapter
    rvRecyclerView.layoutManager = LinearLayoutManager(context)
  }

  inner class MyBehavior : TeamPlayersBehavior() {

    override fun showData(teams: List<Player>) {
      teamsData.clear()
      teamsData.addAll(teams)

      data.clear()
      data.addAll(teamsData.map {
        InlineImageViewDataHolder(it.strPlayer ?: "", it.strCutout ?: "")
      })
      adapter.notifyDataSetChanged()
    }

    override fun onError(msg: String) {
      alert(msg) {
        okButton { }
      }
    }

    override fun showLoading() {
      shimmer.visible()
      shimmer.startShimmer()
    }

    override fun hideLoading() {
      shimmer.stopShimmer()
      shimmer.gone()
    }
  }

}
