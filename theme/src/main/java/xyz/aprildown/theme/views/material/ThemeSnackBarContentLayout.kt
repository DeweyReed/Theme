@file:Suppress("unused")

package xyz.aprildown.theme.views.material

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.SnackbarContentLayout
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.tint.decorateTextButton

@SuppressLint("RestrictedApi")
internal class ThemeSnackBarContentLayout(
    context: Context,
    attrs: AttributeSet? = null
) : SnackbarContentLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        val actionBtn = actionView
        if (actionBtn is MaterialButton) {
            actionBtn.decorateTextButton("?attr/colorAccent")
        } else {
            actionBtn.setTextColor(Theme.get().colorAccent)
        }
    }
}
