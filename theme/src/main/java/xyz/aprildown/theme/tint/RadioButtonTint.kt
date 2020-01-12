package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.widget.CompoundButtonCompat
import com.google.android.material.color.MaterialColors
import com.google.android.material.radiobutton.MaterialRadioButton
import xyz.aprildown.theme.R
import xyz.aprildown.theme.utils.layer
import xyz.aprildown.theme.utils.themeColor

/**
 * [MaterialRadioButton]
 *https://github.com/material-components/material-components-android/blob/master/docs/components/RadioButton.md
 */
internal class RadioButtonTint : BaseTint<AppCompatRadioButton>(
    attrs = intArrayOf(),
    defStyleAttr = android.R.attr.radioButtonStyle,
    onTint = { helper ->
        val radioButton = helper.view
        if (radioButton is MaterialRadioButton) {
            val context = radioButton.context
            val colorControlActivated =
                helper.findAttributeColor(R.attr.colorControlActivated)
            if (colorControlActivated != null) {
                val colorSurface = context.themeColor(R.attr.colorSurface)
                val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
                val csl = ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked),
                        intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_checked),
                        intArrayOf(-android.R.attr.state_enabled, android.R.attr.state_checked),
                        intArrayOf(-android.R.attr.state_enabled, -android.R.attr.state_checked)
                    ),
                    intArrayOf(
                        layer(colorSurface, colorControlActivated, MaterialColors.ALPHA_FULL),
                        layer(colorSurface, colorOnSurface, MaterialColors.ALPHA_MEDIUM),
                        layer(colorSurface, colorOnSurface, MaterialColors.ALPHA_DISABLED),
                        layer(colorSurface, colorOnSurface, MaterialColors.ALPHA_DISABLED)
                    )
                )
                CompoundButtonCompat.setButtonTintList(radioButton, csl)
            }
        }
    }
)
