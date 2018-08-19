package app.submissions.dicoding.footballmatchschedule.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.models.holders.DataType
import app.submissions.dicoding.footballmatchschedule.models.holders.ItemBodyHolder
import app.submissions.dicoding.footballmatchschedule.models.holders.ItemHeaderHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.android.synthetic.main.next_match_item.view.*
import org.jetbrains.anko.AnkoLogger
import java.util.*

class HeaderBodyRecyclerViewAdapter(private val matches: List<DataType>,
                                    private val listener: (Event) -> Unit) :
    RecyclerView.Adapter<HeaderBodyRecyclerViewAdapter.BodyHeaderViewHolder>(), AnkoLogger {

  private lateinit var holder: BodyHeaderViewHolder

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodyHeaderViewHolder {
    val inflater: LayoutInflater = LayoutInflater.from(parent.context)
    when (viewType) {
      DataType.HEADER -> {
        holder = HeaderViewHolder(inflater.inflate(R.layout.header_item, parent, false))
      }
      DataType.BODY -> {
        holder = BodyViewHolder(inflater.inflate(R.layout.next_match_item, parent, false), listener)
      }
    }
    return holder
  }

  override fun getItemViewType(position: Int): Int = matches[position].getType()

  override fun getItemCount(): Int = matches.size

  private fun getItem(position: Int): DataType = matches[position]

  override fun onBindViewHolder(holder: BodyHeaderViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  class HeaderViewHolder(view: View) : BodyHeaderViewHolder(view), AnkoLogger {

    override fun bind(data: DataType) {
      val item: ItemHeaderHolder = data as ItemHeaderHolder
      itemView.tvDate.text = item.header
    }
  }

  class BodyViewHolder(view: View, val listener: (Event) -> Unit) : BodyHeaderViewHolder(view), AnkoLogger {

    override fun bind(data: DataType) {
      data as ItemBodyHolder
      val body: Event = data.body
      itemView.tvTime.text = body.getTime()
      itemView.tvHome?.text = body.strHomeTeam
      itemView.tvAway?.text = body.strAwayTeam
      body.teamHomeBadge { Glide.with(itemView.context).load(it).into(itemView.ivHome) }
      body.teamAwayBadge { Glide.with(itemView.context).load(it).into(itemView.ivAway) }

      itemView.setOnClickListener { listener(data.body) }
    }

    @SuppressLint("Recycle")
    private fun randomizeColor(): Int {
      val colors = itemView.resources.obtainTypedArray(R.array.rygbCircle)
      val bound: Int = colors.length()
      return colors.getResourceId(Random().nextInt(bound), 0)
    }
  }

  abstract class BodyHeaderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(data: DataType)
  }

}