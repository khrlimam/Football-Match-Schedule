package app.submissions.dicoding.footballmatchschedule.fabric

import com.google.gson.Gson

class GsonFabric private constructor() {

  private object GsonBuilder {
    val GSON: Gson = Gson()
  }

  companion object {
    val build by lazy { GsonBuilder.GSON }
  }

}