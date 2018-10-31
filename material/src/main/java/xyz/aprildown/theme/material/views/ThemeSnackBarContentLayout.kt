@file:Suppress("unused")

package xyz.aprildown.theme.material.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.snackbar.SnackbarContentLayout
import xyz.aprildown.theme.Theme.Companion.get

@SuppressLint("RestrictedApi")
internal class ThemeSnackBarContentLayout(
    context: Context,
    attrs: AttributeSet? = null
) : SnackbarContentLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()
//        messageView.setTextColor(0)
        actionView.setTextColor(get().colorAccent)
    }

//    private fun invalidateBgColors(color: Int) {
//        setBackgroundColor(color)
//        val parent = this.parent
//        if (parent is Snackbar.SnackbarLayout) {
//            val background = parent.background
//            if (background != null) {
//                parent.background = background.tint(color)
//            } else {
//                parent.setBackgroundColor(color)
//            }
//        }
//    }
}
