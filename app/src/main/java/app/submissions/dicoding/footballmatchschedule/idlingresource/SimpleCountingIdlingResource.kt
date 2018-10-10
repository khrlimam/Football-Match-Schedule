package app.submissions.dicoding.footballmatchschedule.idlingresource

import android.support.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger

class SimpleCountingIdlingResource(private val resName: String) : IdlingResource {

  private val counter: AtomicInteger = AtomicInteger(0)

  @Volatile
  private var resourceCallback: IdlingResource.ResourceCallback? = null

  override fun getName(): String {
    return resName
  }

  override fun isIdleNow(): Boolean {
    return counter.get() == 0
  }

  override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
    this.resourceCallback = callback
  }

  fun increment() {
    counter.getAndIncrement()
  }

  fun decrement() {
    val counterVal = counter.decrementAndGet()
    if (counterVal == 0) {
      // we've gone from non-zero to zero. That means we're idle now! Tell espresso.
      resourceCallback?.onTransitionToIdle()
    }

    if (counterVal < 0) {
      throw IllegalArgumentException("Counter has been corrupted!")
    }
  }

}