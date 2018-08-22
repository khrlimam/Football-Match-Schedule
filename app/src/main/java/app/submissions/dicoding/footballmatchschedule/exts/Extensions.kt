package app.submissions.dicoding.footballmatchschedule.exts

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.fabric.FontProducer
import com.bumptech.glide.Glide
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
  val font = FontProducer.googleProductRegularFont(context.assets)
  typeface = font
}

fun TextView.fontGoogleProductBold() {
  val font = FontProducer.googleProductBoldFont(context.assets)
  typeface = font
}

fun ImageView.loadWithGlide(url: String) {
  Glide.with(this)
      .load(url)
      .thumbnail(.1f)
      .into(this)
}

fun ImageView.startScaleAnimation() {
  startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale))
}