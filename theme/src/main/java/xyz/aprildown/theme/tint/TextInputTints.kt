package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.view.View
import com.google.android.material.textfield.TextInputLayout
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

internal class TextInputLayoutTint : BaseTint<TextInputLayout>(
    attrs = R.styleable.Theme_TextInputLayout,
    defStyleAttr = R.attr.textInputStyle,
    onTint = {
        // R.style.Widget_MaterialComponents_TextInputLayout_FilledBox
        // R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox
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
        matchThemeColor(R.styleable.Theme_TextInputLayout_errorIconTint)?.let {
            textInputLayout.setErrorIconTintList(it.toColorStateList())
        }
        matchThemeColor(R.styleable.Theme_TextInputLayout_counterTextColor)?.let {
            textInputLayout.counterTextColor = it.toColorStateList()
        }
        matchThemeColor(R.styleable.Theme_TextInputLayout_counterOverflowTextColor)?.let {
            textInputLayout.counterOverflowTextColor = it.toColorStateList()
        }
        withColorOrResourceId(
            R.styleable.Theme_TextInputLayout_startIconTint,
            applySolidColor = {
                textInputLayout.setStartIconTintList(it.toColorStateList())
            },
            applyResource = {
                withStartIconTint(it, textInputLayout)
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_TextInputLayout_endIconTint,
            applySolidColor = {
                textInputLayout.setEndIconTintList(it.toColorStateList())
            },
            applyResource = {
                withEndIconTint(it, textInputLayout)
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_TextInputLayout_boxStrokeColor,
            applySolidColor = {
                textInputLayout.boxStrokeColor = it
            },
            applyResource = {
                withStrokeColor(it, textInputLayout)
            }
        )

        matchThemeColor(R.styleable.Theme_TextInputLayout_boxBackgroundColor)?.let {
            textInputLayout.boxBackgroundColor = it
        }
    }
)

private fun withStartIconTint(resourceId: Int, view: TextInputLayout) {
    when (resourceId) {
        R.color.mtrl_filled_icon_tint -> {
            view.mtrl_filled_icon_tint()
        }
        R.color.mtrl_outlined_icon_tint -> {
            view.mtrl_outlined_icon_tint()
        }
        else -> null
    }?.let {
        view.setStartIconTintList(it)
    }
}

private fun withEndIconTint(resourceId: Int, view: TextInputLayout) {
    when (resourceId) {
        R.color.mtrl_filled_icon_tint -> {
            view.mtrl_filled_icon_tint()
        }
        R.color.mtrl_outlined_icon_tint -> {
            view.mtrl_outlined_icon_tint()
        }
        else -> null
    }?.let {
        view.setEndIconTintList(it)
    }
}

// R.color.mtrl_filled_icon_tint
private fun View.mtrl_filled_icon_tint(): ColorStateList {
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

// R.color.mtrl_outlined_icon_tint
private fun View.mtrl_outlined_icon_tint(): ColorStateList {
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

private fun withStrokeColor(resourceId: Int, view: TextInputLayout) {

    if (resourceId == R.color.mtrl_filled_stroke_color ||
        resourceId == R.color.mtrl_outlined_stroke_color
    ) {
        val colorPrimary = Theme.get().colorPrimary
        // 1. Check setBoxStrokeColor implementation
        // 2. Search "TextInputLayout_boxStrokeColor" in TextInputLayout
        view.boxStrokeColor = colorPrimary

        view.addOnEditTextAttachedListener(object : TextInputLayout.OnEditTextAttachedListener {
            override fun onEditTextAttached(textInputLayout: TextInputLayout) {
                textInputLayout.removeOnEditTextAttachedListener(this)
                textInputLayout.editText?.tintCursorAndSelectHandle(colorPrimary)
            }
        })
    }
}
