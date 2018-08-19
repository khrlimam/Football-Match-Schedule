package app.submissions.dicoding.footballmatchschedule.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class FragmentPagerAdapter(fm: FragmentManager?, private val fragments: List<FragmentData>) : FragmentPagerAdapter(fm) {
  override fun getItem(position: Int): Fragment = fragments[position].fragment
  override fun getPageTitle(position: Int): CharSequence? = fragments[position].title
  override fun getCount(): Int = fragments.size

  data class FragmentData(val title: String, val fragment: Fragment)
}