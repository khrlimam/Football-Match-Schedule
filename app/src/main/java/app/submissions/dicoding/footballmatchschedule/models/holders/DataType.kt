package app.submissions.dicoding.footballmatchschedule.models.holders

abstract class DataType {
  companion object {
    const val HEADER: Int = 0
    const val BODY: Int = 1
  }

  abstract fun getType(): Int

}