package xyz.aprildown.theme.tint

import android.content.Context
import android.content.res.ColorStateList
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/TextField.md
 * [R.style.Widget_MaterialComponents_TextInputLayout_FilledBox]
 * [R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox]
 */
internal class TextInputLayoutTint : BaseTint<TextInputLayout>(
    attrs = R.styleable.Theme_TextInputLayout,
    defStyleAttr = R.attr.textInputStyle,
    onTint = {
        val textInputLayout = view
        matchThemeColor(R.styleable.Theme_TextInputLayout_android_textColorHint)?.let {
            textInputLayout.defaultHintTextColor = it.toColorStateList()
        }
        matchThemeColor(R.styleable.Theme_TextInputLayout_hintTextColor)?.let {
            textInputLayout.hintTextColor = it.toColorStateList()
        }
        matchThemeColor(R.styleable.Theme_TextInputLayout_helperTextTextColor)?.let {
            textInputLayout.setHelperTextColor(it.toColorStateList())
        }
        matchThemeColor(R.styleable.Theme_TextInputLayout_errorTextColor)?.let {
            textInputLayout.setErrorTextColor(it.toColorStateList())
        }
        matchThemeColor(R.styleable.Theme_TextInputLayout_counterTextColor)?.let {
            textInputLayout.counterTextColor = it.toColorStateList()
        }
        matchThemeColor(R.styleable.Theme_TextInputLayout_counterOverflowTextColor)?.let {
            textInputLayout.counterOverflowTextColor = it.toColorStateList()
        }
        matchThemeColor(R.styleable.Theme_TextInputLayout_prefixTextColor)?.let {
            textInputLayout.setPrefixTextColor(it.toColorStateList())
        }
        matchThemeColor(R.styleable.Theme_TextInputLayout_suffixTextColor)?.let {
            textInputLayout.setSuffixTextColor(it.toColorStateList())
        }
        withColorOrResourceId(
            R.styleable.Theme_TextInputLayout_startIconTint,
            applySolidColor = {
                textInputLayout.setStartIconTintList(it.toColorStateList())
            },
            applyResource = {
                textInputLayout.withStartIconTint(it)
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_TextInputLayout_endIconTint,
            applySolidColor = {
                textInputLayout.setEndIconTintList(it.toColorStateList())
            },
            applyResource = {
                textInputLayout.withEndIconTint(it)
            }
        )
        matchThemeColor(R.styleable.Theme_TextInputLayout_errorIconTint)?.let {
            textInputLayout.setErrorIconTintList(it.toColorStateList())
        }
        withColorOrResourceId(
            R.styleable.Theme_TextInputLayout_boxStrokeColor,
            applySolidColor = {
                textInputLayout.boxStrokeColor = it
            },
            applyResource = {
                textInputLayout.withBoxStrokeColor(it)
            }
        )
        matchThemeColor(R.styleable.Theme_TextInputLayout_boxStrokeErrorColor)?.let {
            textInputLayout.boxStrokeErrorColor = it.toColorStateList()
        }

        matchThemeColor(R.styleable.Theme_TextInputLayout_boxBackgroundColor)?.let {
            textInputLayout.boxBackgroundColor = it
        }
        matchThemeColor(R.styleable.Theme_TextInputLayout_placeholderTextColor)?.let {
            textInputLayout.placeholderTextColor = it.toColorStateList()
        }
    }
)

private fun TextInputLayout.withStartIconTint(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_filled_icon_tint -> {
            mtrl_filled_icon_tint(context)
        }
        R.color.mtrl_outlined_icon_tint -> {
            mtrl_outlined_icon_tint(context)
        }
        else -> null
    }?.let {
        setStartIconTintList(it)
    }
}

private fun TextInputLayout.withEndIconTint(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_filled_icon_tint -> {
            mtrl_filled_icon_tint(context)
        }
        R.color.mtrl_outlined_icon_tint -> {
            mtrl_outlined_icon_tint(context)
        }
        else -> null
    }?.let {
        setEndIconTintList(it)
    }
}

/** [R.color.mtrl_filled_icon_tint] */
private fun mtrl_filled_icon_tint(context: Context): ColorStateList {
    val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_activated),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorPrimary,
            colorOnSurface.adjustAlpha(0.38f),
            colorOnSurface.adjustAlpha(0.54f)
        )
    )
}

/** [R.color.mtrl_outlined_icon_tint] */
private fun mtrl_outlined_icon_tint(context: Context): ColorStateList {
    val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_activated),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorPrimary,
            colorOnSurface.adjustAlpha(0.38f),
            colorOnSurface.adjustAlpha(0.6f)
        )
    )
}

private fun TextInputLayout.withBoxStrokeColor(resourceId: Int) {
    when (resourceId) {
        R.color.mtrl_filled_stroke_color -> {
            mtrl_filled_stroke_color(context)
        }
        R.color.mtrl_outlined_stroke_color -> {
            mtrl_outlined_stroke_color(context)
        }
        else -> null
    }?.let {
        setBoxStrokeColorStateList(it)
    }
}

/** [R.color.mtrl_filled_stroke_color] */
private fun mtrl_filled_stroke_color(context: Context): ColorStateList {
    val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorPrimary,
            colorOnSurface.adjustAlpha(0.46f),
            colorOnSurface.adjustAlpha(0.38f),
            colorOnSurface.adjustAlpha(0.42f)
        )
    )
}

/** [R.color.mtrl_outlined_stroke_color] */
private fun mtrl_outlined_stroke_color(context: Context): ColorStateList {
    val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorPrimary,
            colorOnSurface.adjustAlpha(0.87f),
            colorOnSurface.adjustAlpha(0.12f),
            colorOnSurface.adjustAlpha(0.38f)
        )
    )
}

internal class TextInputEditTextTint : BaseTint<TextInputEditText>(
    attrs = R.styleable.Theme_TextInputEditText,
    defStyleAttr = R.attr.editTextStyle,
    onTint = {
        val editText = view
        matchThemeColor(R.styleable.Theme_TextInputEditText_android_textColor)?.let {
            editText.setTextColor(it)
        }
    }
)
