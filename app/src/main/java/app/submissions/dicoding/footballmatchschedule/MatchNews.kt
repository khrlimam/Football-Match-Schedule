package app.submissions.dicoding.footballmatchschedule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import app.submissions.dicoding.footballmatchschedule.adapters.FragmentPagerAdapter
import app.submissions.dicoding.footballmatchschedule.exts.handleSafely
import app.submissions.dicoding.footballmatchschedule.fragments.NextMatch
import app.submissions.dicoding.footballmatchschedule.fragments.PreviousMatch
import app.submissions.dicoding.footballmatchschedule.models.Leagues
import app.submissions.dicoding.footballmatchschedule.requests.to.LeagueSchedule
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_schedules.*
import org.jetbrains.anko.startActivity

class MatchNews : AppCompatActivity() {

  private val leagueSubject = PublishSubject.create<Any>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_schedules)
    setSupportActionBar(toolbar)

    val previousMatch = PreviousMatch()
    val nextMatch = NextMatch()
    previousMatch.leagueSubject = leagueSubject
    nextMatch.leagueSubject = leagueSubject

    val adapter = FragmentPagerAdapter(supportFragmentManager, listOf(
        FragmentPagerAdapter.FragmentData("Past", previousMatch),
        FragmentPagerAdapter.FragmentData("Coming", nextMatch)
    ))

    LeagueSchedule.Request.get.leagues()
        .handleSafely()
        .subscribe { response ->
          response as Leagues
          val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, response.leagues.filter { it.strSport == "Soccer" })
          sLeagues.adapter = adapter
        }.isDisposed

    sLeagues.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        leagueSubject.onNext(position)
        Log.i(localClassName, "$position")
      }

      override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.i(localClassName, "nothing selected")
      }

    }
    tabContainer.adapter = adapter
    tabs.setupWithViewPager(tabContainer)
  }


  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return when (item?.itemId) {
      R.id.myFavorites -> {
        startActivity<Favorites>()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

}
