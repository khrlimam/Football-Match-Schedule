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
import app.submissions.dicoding.footballmatchschedule.exts.fontGoogleProductRegular
import app.submissions.dicoding.footballmatchschedule.exts.handleSafely
import app.submissions.dicoding.footballmatchschedule.exts.loadWithGlide
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.models.holders.MatchNewsHolder
import io.reactivex.Observable
import kotlinx.android.synthetic.main.single_news_item.view.*
import kotlinx.android.synthetic.main.viewpager_item.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onPageChangeListener
import java.util.concurrent.TimeUnit

class RecyclerViewAdapterWithItemViewPager(private val data: List<MatchNewsHolder>,
                                           private val onPagerItemClick: (Event) -> Unit)
  : RecyclerView.Adapter<RecyclerViewAdapterWithItemViewPager.NewsViewBinder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewBinder {
    val inflater = LayoutInflater.from(parent.context)
    if (viewType > 1) {
      return ViewPagerHolder(
          inflater
              .inflate(R.layout.viewpager_item, parent, false),
          onPagerItemClick)
    }
    return SingleNewsViewHolder(
        inflater
            .inflate(R.layout.single_news_item, parent, false),
        onPagerItemClick)
  }

  override fun getItemViewType(position: Int): Int = data[position].news.size

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(holder: NewsViewBinder, position: Int) {
    holder.bind(data[position])
  }

  class SingleNewsViewHolder(view: View, private val onNewsClicked: (Event) -> Unit) : NewsViewBinder(view) {
    override fun bind(data: MatchNewsHolder) {
      val singleNews = data.news[0]
      singleNews.winnerBanner { itemView.ivImageView.loadWithGlide(it) }
      itemView.tvTitle.fontGoogleProductBold()
      itemView.tvDate.fontGoogleProductRegular()
      itemView.tvTitle.text = singleNews.headline()
      itemView.tvDate.text = singleNews.getFormattedDate()
      itemView.setOnClickListener { onNewsClicked(singleNews) }
      itemView.ivImageView.setOnClickListener { onNewsClicked(singleNews) }
    }
  }

  class ViewPagerHolder(view: View, private val onPagerItemClick: (Event) -> Unit) : NewsViewBinder(view), AnkoLogger {

    private val pg: ObjectAnimator = AnimatorInflater.loadAnimator(view.context, R.animator.bg_progress) as ObjectAnimator

    @SuppressLint("SetTextI18n")
    override fun bind(data: MatchNewsHolder) {
      pg.target = itemView.progressBar
      pg.start()
      itemView.tvFooter.fontGoogleProductBold()
      itemView.tvFooter.text = data.date
      itemView.viewPager.adapter = ViewPagerAdapter(data.news) {
        onPagerItemClick(it)
      }
      itemView.viewPager.setPageTransformer(false, SlowingBackgroundMovement())
      itemView.textView3.text = "1 of ${data.news.size}"
      itemView.viewPager.onPageChangeListener {
        onPageSelected { itemView.textView3.text = "${it + 1} of ${data.news.size}" }
      }
      Observable.interval(INTERVAL_DURATION, TimeUnit.MILLISECONDS)
          .handleSafely()
          .subscribe {
            if (itemView.viewPager.currentItem < data.news.size - 1)
              ++itemView.viewPager.currentItem
            else
              itemView.viewPager.currentItem = 0
          }.isDisposed
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

  abstract class NewsViewBinder(val view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(data: MatchNewsHolder)
  }

}