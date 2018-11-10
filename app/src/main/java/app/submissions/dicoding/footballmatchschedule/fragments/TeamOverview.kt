package app.submissions.dicoding.footballmatchschedule.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.constants.Constants
import app.submissions.dicoding.footballmatchschedule.models.Team
import kotlinx.android.synthetic.main.fragment_team_overview.*

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamOverview : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_team_overview, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val team = arguments?.getParcelable<Team>(Constants.TEAM_DATA)
    tvTeamOverview.text = team?.strDescriptionEN
  }


}
