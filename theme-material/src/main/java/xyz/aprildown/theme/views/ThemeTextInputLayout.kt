package xyz.aprildown.theme.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout
import xyz.aprildown.theme.Theme.Companion.get

internal class ThemeTextInputLayout(
    context: Context,
    attrs: AttributeSet? = null
) : TextInputLayout(context, attrs) {

    init {
        defaultHintTextColor = ColorStateList.valueOf(get().colorAccent)
    }
}
