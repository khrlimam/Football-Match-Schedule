package app.submissions.dicoding.footballmatchschedule.fabric

import com.google.gson.Gson

class GsonFabric private constructor() {

  private object Builder {
    val GSON: Gson = Gson()
  }

  companion object {
    val build by lazy { Builder.GSON }
  }

}