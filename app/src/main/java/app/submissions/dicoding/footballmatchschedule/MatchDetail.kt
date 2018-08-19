package app.submissions.dicoding.footballmatchschedule

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import app.submissions.dicoding.footballmatchschedule.exts.invisible
import app.submissions.dicoding.footballmatchschedule.exts.visible
import app.submissions.dicoding.footballmatchschedule.models.Event
import app.submissions.dicoding.footballmatchschedule.models.Teams
import app.submissions.dicoding.footballmatchschedule.models.holders.TimeLineHolder
import app.submissions.dicoding.footballmatchschedule.presenters.MatchDetailPresenter
import app.submissions.dicoding.footballmatchschedule.presenters.behavior.MatchDetailBehavior
import com.bumptech.glide.Glide
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.match_detail.*
import kotlinx.android.synthetic.main.timeline_item.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.okButton

class MatchDetail : AppCompatActivity(), MatchDetailBehavior {

  private lateinit var disposable: Disposable
  private lateinit var schedule: Event
  private val matchDetailPresenter: MatchDetailPresenter = MatchDetailPresenter(this)
  private val clubToImage: MutableMap<String, ImageView> = mutableMapOf()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.match_detail)
    supportActionBar?.subtitle = schedule.strEvent
    this.schedule = intent.getParcelableExtra(BuildConfig.EVENT_DATA)
    toolbar.title = "Match Detail"
    toolbar.subtitle = schedule.strEvent
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    clubToImage[schedule.idAwayTeam] = ivAway
    clubToImage[schedule.idHomeTeam] = ivHome
    hideDetailIfNextMatch()
    tvAway.text = schedule.strAwayTeam
    tvHome.text = schedule.strHomeTeam
    tvTime.text = schedule.getTime()
    tvDate.text = schedule.getFormattedDate()
    tvAwayScore.text = "${schedule.intAwayShots} shots with ${schedule.intAwayScore} goal"
    tvHomeScore.text = "${schedule.intHomeShots} shots with ${schedule.intHomeScore} goal"
    matchDetailPresenter.getTeamDetail(schedule.idHomeTeam)
    matchDetailPresenter.getTeamDetail(schedule.idAwayTeam)
    if (!schedule.isNext())
      matchDetailPresenter.mapScheduleToTimeline(schedule)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  private fun hideDetailIfNextMatch() {
    if (schedule.isNext()) {
      tvAwayScore.invisible()
      tvHomeScore.invisible()
      svTimeline.invisible()
      trTimeline.invisible()
    }
  }

  override fun showLoading() {
    pgLoading.visible()
  }

  override fun hideLoading() {
    pgLoading.invisible()
  }

  override fun showImage(teams: Teams) {
    val team = teams.teams[0]
    val v: ImageView? = this.clubToImage[team.teamId]
    v?.let { Glide.with(this).load(team.teamBadge).into(it) }
  }

  override fun setDisposable(disposable: Disposable) {
    this.disposable = disposable
  }

  override fun showError(message: String) {
    alert(message) {
      okButton {}
    }
  }

  @SuppressLint("InflateParams")
  override fun showTimeline(data: List<TimeLineHolder>) {
    data.forEachWithIndex { i, it ->
      val view = layoutInflater.inflate(R.layout.timeline_item, null)
      if (it.teamType == TimeLineHolder.AWAY)
        view.ivTeamTypeIndicator.setImageResource(R.drawable.circle_green)
      if (i == data.size - 1)
        view.divider.invisible()
      view.tvTime.text = it.time
      view.tvSubject.text = it.subject
      view.tvPredicate.text = it.predicate
      lScrollViewContainer.addView(view)
    }
  }

  override fun onPause() {
    this.disposable.dispose()
    super.onPause()
  }

  override fun onStop() {
    this.disposable.dispose()
    super.onStop()
  }

  override fun onBackPressed() {
    finish()
    super.onBackPressed()
  }

}