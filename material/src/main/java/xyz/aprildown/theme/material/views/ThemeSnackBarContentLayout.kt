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
        actionView.setTextColor(get(context).colorAccent)
    }
}
