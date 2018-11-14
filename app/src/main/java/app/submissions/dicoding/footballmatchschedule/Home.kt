package app.submissions.dicoding.footballmatchschedule

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import app.submissions.dicoding.footballmatchschedule.exts.database
import app.submissions.dicoding.footballmatchschedule.fragments.Favorites
import app.submissions.dicoding.footballmatchschedule.fragments.MatchNews
import app.submissions.dicoding.footballmatchschedule.fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

  private val mOnNavigationItemSelectedListener = { bundle: Bundle? ->
    BottomNavigationView.OnNavigationItemSelectedListener { item ->
      when (item.itemId) {
        R.id.navigation_home -> {
          changeFrameContent(MatchNews(), bundle)
          return@OnNavigationItemSelectedListener true
        }
        R.id.myFavorites -> {
          changeFrameContent(Favorites(), bundle)
          return@OnNavigationItemSelectedListener true
        }
        R.id.navigation_search -> {
          changeFrameContent(SearchFragment(), bundle)
          return@OnNavigationItemSelectedListener true
        }
      }
      false
    }
  }

  var bundle: Bundle? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener(savedInstanceState))
    navigation.selectedItemId = R.id.navigation_home
    initDb()
  }

  private fun initDb() {
    database().use {
      database().onCreate(this)
    }
  }

  private fun changeFrameContent(fragment: Fragment, bundle: Bundle?) {
    if (bundle == null)
      supportFragmentManager
          .beginTransaction()
          .replace(R.id.flContentContainer, fragment)
          .commit()
  }

}
