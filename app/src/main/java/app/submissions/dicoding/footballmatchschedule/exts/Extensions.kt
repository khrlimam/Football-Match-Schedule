package app.submissions.dicoding.footballmatchschedule.exts

import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun View.invisible() {
  visibility = View.INVISIBLE
}

fun View.visible() {
  visibility = View.VISIBLE
}

fun View.gone() {
  visibility = View.GONE
}

fun Observable<*>.handleSafely(): Observable<*> {
  return subscribeOn(Schedulers.newThread())
      .observeOn(AndroidSchedulers.mainThread())
}

fun TextView.fontGoogleProductRegular() {
  val font = Typeface.createFromAsset(context.assets, "fonts/Product Sans Regular.ttf")
  typeface = font
}

fun TextView.fontGoogleProductBold() {
  val font = Typeface.createFromAsset(context.assets, "fonts/Product Sans Bold.ttf")
  typeface = font
}