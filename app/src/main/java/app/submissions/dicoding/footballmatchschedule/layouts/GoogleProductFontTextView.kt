package app.submissions.dicoding.footballmatchschedule.layouts

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView


class GoogleProductFontTextView(ctx: Context) : TextView(ctx) {
  init {
    val font = Typeface.createFromAsset(context.assets, "fonts/Product Sans Regular.ttf")
    setTypeface(font, Typeface.NORMAL)
  }
}