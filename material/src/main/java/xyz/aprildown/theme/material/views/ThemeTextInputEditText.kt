package xyz.aprildown.theme.material.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import xyz.aprildown.theme.Theme.Companion.get

@SuppressLint("RestrictedApi")
internal class ThemeTextInputEditText(
    context: Context,
    attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    init {
        supportBackgroundTintList = ColorStateList.valueOf(get().colorAccent)
    }
}
