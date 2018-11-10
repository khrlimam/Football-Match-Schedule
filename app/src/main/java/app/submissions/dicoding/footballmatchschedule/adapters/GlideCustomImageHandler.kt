package app.submissions.dicoding.footballmatchschedule.adapters

import android.graphics.Bitmap
import android.util.Log
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class GlideCustomImageHandler(private val onReady: (Bitmap?) -> Unit) : RequestListener<Bitmap> {
  override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
    Log.i(javaClass.name, e?.localizedMessage)
    return false
  }

  override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
    onReady(resource)
    return true
  }
}