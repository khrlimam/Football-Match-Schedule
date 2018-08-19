package app.submissions.dicoding.footballmatchschedule.models.holders

data class ItemHeaderHolder(val header: String) : DataType() {

  override fun getType(): Int = DataType.HEADER
}