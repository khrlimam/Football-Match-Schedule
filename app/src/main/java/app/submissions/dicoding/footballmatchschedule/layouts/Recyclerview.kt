package app.submissions.dicoding.footballmatchschedule.layouts

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.verticalLayout

class Recyclerview(private val adapter_: RecyclerView.Adapter<*>) : AnkoComponent<ViewGroup?> {
  override fun createView(ui: AnkoContext<ViewGroup?>): View = with(ui) {
    return verticalLayout {
      lparams(width = matchParent, height = matchParent)
      recyclerView {
        lparams(width = matchParent, height = matchParent)
        adapter = adapter_
        layoutManager = LinearLayoutManager(ctx)
      }
    }
  }

}