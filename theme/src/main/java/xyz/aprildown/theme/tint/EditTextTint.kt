package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.isQOrLater
import xyz.aprildown.theme.utils.themeColor

/**
 * I don't recommend using EditText directly because it doesn't provide many customization.
 */
internal class EditTextTint : BaseTint<AppCompatEditText>(
    attrs = R.styleable.Theme_EditText,
    defStyleAttr = R.attr.editTextStyle,
    onTint = {
        // R.style.Widget_AppCompat_EditText
        val editText = view

        decorateTextView()

        withColorOrResourceId(
            R.styleable.Theme_EditText_backgroundTint,
            applySolidColor = {
                editText.tintBackground(it)
            },
            applyDefault = {
                editText.tintBackground(Theme.get().colorSecondary)
            }
        )
    }
)

private fun EditText.tintBackground(@ColorInt color: Int) {
    highlightColor = color
    // This implementation is dumb and doesn't have any reference although it looks fine.
    // Use TextInputLayout instead.
    ViewCompat.setBackgroundTintList(
        this, ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_focused),
                intArrayOf()
            ),
            intArrayOf(
                color,
                context.themeColor(R.attr.colorControlNormal)
            )
        )
    )
    tintCursorAndSelectHandle(color)
}

internal fun EditText.tintCursorAndSelectHandle(@ColorInt color: Int) {
    if (isQOrLater()) {
        textCursorDrawable?.setTint(color)
        textSelectHandle?.setTint(color)
        textSelectHandleLeft?.setTint(color)
        textSelectHandleRight?.setTint(color)
    }
    // I don't want to use reflection to tint on Pre-O devices.
}
