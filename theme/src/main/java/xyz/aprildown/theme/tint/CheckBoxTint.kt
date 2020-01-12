package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.widget.CompoundButtonCompat
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.color.MaterialColors
import xyz.aprildown.theme.R
import xyz.aprildown.theme.utils.layer
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

/**
 * [MaterialCheckBox]
 * https://github.com/material-components/material-components-android/blob/master/docs/components/Checkbox.md
 */
internal class CheckBoxTint : BaseTint<AppCompatCheckBox>(
    attrs = R.styleable.Theme_CheckBox,
    defStyleAttr = android.R.attr.checkboxStyle,
    onTint = { helper ->
        val checkBox = helper.view
        if (checkBox is MaterialCheckBox) {
            helper.withColorOrResourceId(
                R.styleable.Theme_CheckBox_buttonTint,
                onColor = {
                    CompoundButtonCompat.setButtonTintList(checkBox, it.toColorStateList())
                },
                onResourceId = {
                },
                fallback = {
                    val context = checkBox.context
                    val colorControlActivated =
                        helper.findAttributeColor(R.attr.colorControlActivated)
                            ?: return@withColorOrResourceId
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
                    CompoundButtonCompat.setButtonTintList(checkBox, csl)
                }
            )
        }
    }
)
