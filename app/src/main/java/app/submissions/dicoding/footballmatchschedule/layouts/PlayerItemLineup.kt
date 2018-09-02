package app.submissions.dicoding.footballmatchschedule.layouts

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.layouts.PlayerItemLineup.WhichPlayer.*
import org.jetbrains.anko.*

class PlayerItemLineup(private val playerName: String, private val which: WhichPlayer) : AnkoComponent<Context> {
  override fun createView(ui: AnkoContext<Context>): View = with(ui) {
    return verticalLayout {
      lparams(width = dip(0), height = wrapContent, weight = 1f)
      view {
        background = when (which) {
          HOME -> ContextCompat.getDrawable(ctx, R.drawable.home_player_circle)
          AWAY -> ContextCompat.getDrawable(ctx, R.drawable.away_player_circle)
        }
      }.lparams(width = dip(30), height = dip(30)) {
        gravity = Gravity.CENTER_HORIZONTAL
      }

      textView(playerName) {
        textAlignment = LinearLayout.TEXT_ALIGNMENT_CENTER
        textColor = ContextCompat.getColor(context, android.R.color.white)
      }.lparams(width = wrapContent, height = wrapContent) {
        gravity = Gravity.CENTER_HORIZONTAL
        topMargin = dip(5)
      }
    }
  }


  enum class WhichPlayer {
    HOME, AWAY
  }

}