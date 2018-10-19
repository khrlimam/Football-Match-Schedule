package app.submissions.dicoding.footballmatchschedule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.AdapterView
import app.submissions.dicoding.footballmatchschedule.adapters.FragmentPagerAdapter
import app.submissions.dicoding.footballmatchschedule.adapters.ImageTitleSpinnerAdapter
import app.submissions.dicoding.footballmatchschedule.fragments.NextMatch
import app.submissions.dicoding.footballmatchschedule.fragments.PreviousMatch
import app.submissions.dicoding.footballmatchschedule.fragments.TeamsFragment
import app.submissions.dicoding.footballmatchschedule.models.League
import app.submissions.dicoding.footballmatchschedule.presenters.MatchNewsPresenter
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.MatchNewsBehavior
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_schedules.*

class MatchNews : Fragment(), MatchNewsBehavior {

  private val leagueSubject = PublishSubject.create<Any>()
  private val leagues: MutableList<League> = mutableListOf()
  private val presenter = MatchNewsPresenter(this)
  private val spinnerAdapter = ImageTitleSpinnerAdapter(leagues)

  override fun onCreate(savedInstanceState: Bundle?) {
    setHasOptionsMenu(true)
    super.onCreate(savedInstanceState)
  }

  private var cachedView: View? = null
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    if (cachedView == null)
      cachedView = inflater.inflate(R.layout.activity_schedules, container, false)
    return cachedView
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val previousMatchFragment = PreviousMatch()
    val nextMatchFragment = NextMatch()
    val teamsFragment = TeamsFragment()
    previousMatchFragment.leagueSubject = leagueSubject
    nextMatchFragment.leagueSubject = leagueSubject
    teamsFragment.leagueSubject = leagueSubject

    val adapter = FragmentPagerAdapter(childFragmentManager, listOf(
        FragmentPagerAdapter.FragmentData("Past", previousMatchFragment),
        FragmentPagerAdapter.FragmentData("Coming", nextMatchFragment),
        FragmentPagerAdapter.FragmentData("Teams", teamsFragment)
    ))

    presenter.getData()

    sLeagues.onItemSelectedListener = publishSelectedItem()
    tabContainer.adapter = adapter
    tabContainer.offscreenPageLimit = 3
    sLeagues.adapter = spinnerAdapter
    tabs.setupWithViewPager(tabContainer)
  }

  private fun publishSelectedItem(): AdapterView.OnItemSelectedListener? =
      object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
          leagueSubject.onNext(leagues[position].idLeague)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
          Log.i(javaClass.name, "nothing selected")
        }
      }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater?.inflate(R.menu.menu_main, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onPause() {
    presenter.dispose()
    super.onPause()
  }

  override fun onStop() {
    presenter.dispose()
    super.onStop()
  }

  override fun showData(leagues: List<League>) {
    this.leagues.clear()
    this.leagues.addAll(leagues)
    spinnerAdapter.notifyDataSetChanged()
  }
}
