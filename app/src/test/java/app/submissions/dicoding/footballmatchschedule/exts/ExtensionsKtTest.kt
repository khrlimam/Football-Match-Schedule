package app.submissions.dicoding.footballmatchschedule.exts

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat

class ExtensionsKtTest {

  @Test
  fun toSimpleString() {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = dateFormat.parse("24/09/2018")
    assertEquals("Mon, 24 Sep 2018", toSimpleString(date))
  }
}