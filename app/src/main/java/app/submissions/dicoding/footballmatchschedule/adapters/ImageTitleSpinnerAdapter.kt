package app.submissions.dicoding.footballmatchschedule.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.exts.fontGoogleProductBold
import app.submissions.dicoding.footballmatchschedule.exts.loadWithGlide
import app.submissions.dicoding.footballmatchschedule.models.League

class ImageTitleSpinnerAdapter(private val leagues: List<League>) : BaseAdapter() {

  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val viewHolder: ViewHolder
    val view: View
    if (convertView == null) {
      view = LayoutInflater.from(parent?.context).inflate(R.layout.spinner_item_with_image, parent, false)
      viewHolder = ViewHolder(view)
      view.tag = viewHolder
    } else {
      view = convertView
      viewHolder = view.tag as ViewHolder
    }
    viewHolder.bind(getItem(position))
    return view
  }

  override fun getItem(position: Int): League = leagues[position]

  override fun getItemId(position: Int): Long = 0

  override fun getCount(): Int = leagues.size

  class ViewHolder(view: View) {
    private val image: ImageView = view.findViewById(R.id.ivDesc)
    private val text: TextView = view.findViewById(R.id.tvDesc)

    fun bind(league: League) {
      league.apply {
        text.text = strLeague
        text.fontGoogleProductBold()
        image.loadWithGlide(badge)
      }
    }

  }

}