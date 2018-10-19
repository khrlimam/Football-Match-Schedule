package app.submissions.dicoding.footballmatchschedule.exts

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import app.submissions.dicoding.footballmatchschedule.R
import app.submissions.dicoding.footballmatchschedule.db.MyDatabaseOpenHelper
import app.submissions.dicoding.footballmatchschedule.fabric.FontProducer
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

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

fun String.abbreviatedName(): String {
  val nameSplitted = this.split(" ")
  if (nameSplitted.size > 1)
    return "${nameSplitted[0][0]}. ${nameSplitted.last()}"
  return nameSplitted[0]
}

fun String.splitCommaSeparatedToList(): List<String> {
  return this.split(";").asSequence().map { it.trim() }.filter { it.trim().isNotEmpty() }.toList()
}

fun Map<*, *>.getOrAnother(key: String, default: String): String {
  if (get(key) == null)
    return default
  return get(key).toString()
}

fun Context.database(): MyDatabaseOpenHelper = MyDatabaseOpenHelper.getInstance(this)

fun Fragment.database(): MyDatabaseOpenHelper? = context?.let { MyDatabaseOpenHelper.getInstance(it) }

@SuppressLint("SimpleDateFormat")
fun Date.toLocalDateWithDayName(): String = SimpleDateFormat("EEE, dd MMM yyy").format(this)

@SuppressLint("SimpleDateFormat")
fun Date.toLocalTime(): String = SimpleDateFormat("HH:mm").format(this)

@SuppressLint("SimpleDateFormat")
fun String.toDate(): Date {
  val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ssZZ")
  // set timezone to gmt+8
  formatter.timeZone = TimeZone.getTimeZone("Asia/Makassar")
  return formatter.parse(this)
}