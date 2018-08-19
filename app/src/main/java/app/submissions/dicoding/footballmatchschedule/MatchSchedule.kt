package app.submissions.dicoding.footballmatchschedule

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import app.submissions.dicoding.footballmatchschedule.fragments.Favorites
import kotlinx.android.synthetic.main.activity_match_schedule.*
import kotlinx.android.synthetic.main.activity_schedules.*
import org.jetbrains.anko.AnkoLogger

class MatchSchedule : AppCompatActivity(), AnkoLogger {

  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.match_news -> {
//        changeContainer(MatchNews())
        return@OnNavigationItemSelectedListener true
      }
      R.id.favorites -> {
        changeContainer(Favorites())
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  private fun changeContainer(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.contentContainer, fragment)
        .commit()
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_match_schedule)
    setSupportActionBar(toolbar)
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    navigation.selectedItemId = R.id.match_news

  }
}
