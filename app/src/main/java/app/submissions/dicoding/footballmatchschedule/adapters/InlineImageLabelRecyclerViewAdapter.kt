package app.submissions.dicoding.footballmatchschedule.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.adapters.InlineImageLabelRecyclerViewAdapter.ViewHolder
import app.submissions.dicoding.footballmatchschedule.exts.fontGoogleProductRegular
import app.submissions.dicoding.footballmatchschedule.exts.loadWithGlide
import kotlinx.android.synthetic.main.linear_image_label.view.*

class InlineImageLabelRecyclerViewAdapter(private val data: List<InlineImageViewDataHolder>,
                                          private val onclick: (Int) -> Unit)
  : RecyclerView.Adapter<ViewHolder>() {
  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(data[position])
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.linear_image_label, parent, false), onclick)
  }

  class ViewHolder(view: View, val onclick: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
    fun bind(data: InlineImageViewDataHolder) {
      itemView.apply {
        ivLogo.loadWithGlide(data.imgUrl)
        tvTitle.text = data.title
        tvTitle.fontGoogleProductRegular()
        itemView.setOnClickListener { onclick(position) }
      }
    }
  }
}

data class InlineImageViewDataHolder(val title: String, val imgUrl: String)