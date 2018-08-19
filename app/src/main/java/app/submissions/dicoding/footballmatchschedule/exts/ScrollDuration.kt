package app.submissions.dicoding.footballmatchschedule.exts

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller

class ScrollDuration(context: Context, interpolator: Interpolator, private val myDuration: Int)
  : Scroller(context, interpolator) {

  override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
    super.startScroll(startX, startY, dx, dy, myDuration)
  }

}