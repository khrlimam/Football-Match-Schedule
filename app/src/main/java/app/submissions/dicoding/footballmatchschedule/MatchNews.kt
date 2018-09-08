package app.submissions.dicoding.footballmatchschedule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import app.submissions.dicoding.footballmatchschedule.adapters.FragmentPagerAdapter
import app.submissions.dicoding.footballmatchschedule.fragments.NextMatch
import app.submissions.dicoding.footballmatchschedule.fragments.PreviousMatch
import kotlinx.android.synthetic.main.activity_schedules.*
import org.jetbrains.anko.startActivity

class MatchNews : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_schedules)
    setSupportActionBar(toolbar)
    val adapter = FragmentPagerAdapter(supportFragmentManager, listOf(
        FragmentPagerAdapter.FragmentData("Past", PreviousMatch()),
        FragmentPagerAdapter.FragmentData("Coming", NextMatch())
    ))
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
