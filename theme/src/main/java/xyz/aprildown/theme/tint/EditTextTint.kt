package xyz.aprildown.theme.tint

import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.isQOrLater
import xyz.aprildown.theme.utils.toColorStateList

/**
 * I don't recommend using EditText directly because it doesn't provide many customization.
 */
internal class EditTextTint : BaseTint<AppCompatEditText>(
    attrs = R.styleable.Theme_EditText,
    defStyleAttr = R.attr.editTextStyle,
    onTint = {
        val editText = view
        matchThemeColor(R.styleable.Theme_EditText_android_textColor)?.let {
            editText.setTextColor(it)
        }
        matchThemeColor(R.styleable.Theme_EditText_android_textColorHint)?.let {
            editText.setHintTextColor(it)
        }

        fun tintEditTextBackground(@ColorInt color: Int) {
            editText.highlightColor = color
            // This tint all EditTexts on the screen. It's a problem.
            ViewCompat.setBackgroundTintList(editText, color.toColorStateList())
            if (isQOrLater()) {
                editText.textCursorDrawable?.setTint(color)
                editText.textSelectHandle?.setTint(color)
            }
        }

        withColorOrResourceId(
            R.styleable.Theme_EditText_backgroundTint,
            applySolidColor = {
                tintEditTextBackground(it)
            },
            applyResource = {
            },
            applyDefault = {
                tintEditTextBackground(Theme.get().colorSecondary)
            }
        )
    }
)
