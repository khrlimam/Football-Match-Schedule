package app.submissions.dicoding.footballmatchschedule.adapters

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.exts.fontGoogleProductBold
import app.submissions.dicoding.footballmatchschedule.exts.handleSafely
import app.submissions.dicoding.footballmatchschedule.models.holders.MatchNewsHolder
import io.reactivex.Observable
import kotlinx.android.synthetic.main.viewpager_item.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onPageChangeListener
import java.util.concurrent.TimeUnit

class RecyclerViewAdapterWithItemViewPager(private val data: List<MatchNewsHolder>) :
    RecyclerView.Adapter<RecyclerViewAdapterWithItemViewPager.ViewPagerHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.viewpager_item, parent, false)
    return ViewPagerHolder(view)
  }

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
    holder.bind(data[position])
  }

  class ViewPagerHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

    private val pg: ObjectAnimator = AnimatorInflater.loadAnimator(view.context, R.animator.bg_progress) as ObjectAnimator

    @SuppressLint("SetTextI18n")
    fun bind(newsHolder: MatchNewsHolder) {
      pg.target = itemView.progressBar
      pg.start()
      itemView.tvFooter.fontGoogleProductBold()
      itemView.tvFooter.text = newsHolder.date
      itemView.viewPager.adapter = ViewPagerAdapter(newsHolder.news)
      itemView.viewPager.setPageTransformer(false, SlowingBackgroundMovement())
      itemView.textView3.text = "1 of ${newsHolder.news.size}"
      itemView.viewPager.onPageChangeListener {
        onPageSelected { itemView.textView3.text = "${it + 1} of ${newsHolder.news.size}" }
      }
      Observable.interval(INTERVAL_DURATION, TimeUnit.MILLISECONDS)
          .handleSafely()
          .subscribe {
            if (itemView.viewPager.currentItem < newsHolder.news.size - 1)
              ++itemView.viewPager.currentItem
            else
              itemView.viewPager.currentItem = 0
          }
    }

    companion object {
      private const val INTERVAL_DURATION: Long = 10000L
    }

    inner class SlowingBackgroundMovement : ViewPager.PageTransformer {
      override fun transformPage(page: View, position: Float) {
        val image = page.find<ImageView>(R.id.imageView)
        if (position <= 1) image.translationX = -position * (page.width / 2)
      }
    }
  }
}