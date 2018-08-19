package app.submissions.dicoding.footballmatchschedule.adapters

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.exts.fontGoogleProductRegular
import app.submissions.dicoding.footballmatchschedule.models.Event
import com.bumptech.glide.Glide
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find

class ViewPagerAdapter(private val items: List<Event>) : PagerAdapter() {
  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    val view = LayoutInflater.from(container.context)
        .inflate(R.layout.viewpager_content, container, false)
    ItemHolder(view).bind(getItem(position))
    container.addView(view)
    return view
  }

  private fun getItem(position: Int): Event = items[position]

  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) =
      container.removeView(`object` as View?)

  override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

  override fun getCount(): Int = items.size

  class ItemHolder(val view: View) : AnkoLogger {
    private val tvTagLine: TextView = view.find(R.id.tvTagLine)
    private val imageView: ImageView = view.find(R.id.imageView)
    private val scaleAnimation = AnimationUtils.loadAnimation(view.context, R.anim.scale)

    fun bind(item: Event) {
      tvTagLine.fontGoogleProductRegular()
      tvTagLine.text = item.headline()
      item.winnerBanner { fanArtUrl: String ->
        Glide.with(view.context).load(fanArtUrl).into(imageView)
      }
      imageView.startAnimation(scaleAnimation)
    }
  }
}