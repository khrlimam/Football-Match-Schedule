package app.submissions.dicoding.footballmatchschedule.layouts

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.widget.EditText

class EditTextCoordinatorLayoutBehavior : CoordinatorLayout.Behavior<EditText>() {
  override fun layoutDependsOn(parent: CoordinatorLayout?, child: EditText?, dependency: View?): Boolean {
    return dependency is AppBarLayout
  }
}