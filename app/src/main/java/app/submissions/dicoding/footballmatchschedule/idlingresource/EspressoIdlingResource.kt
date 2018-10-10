package app.submissions.dicoding.footballmatchschedule.idlingresource

object EspressoIdlingResource {
  private const val RESOURCE: String = "GLOBAL"
  val mCountingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

  fun inc() {
    mCountingIdlingResource.increment()
  }

  fun dec() {
    mCountingIdlingResource.decrement()
  }

}