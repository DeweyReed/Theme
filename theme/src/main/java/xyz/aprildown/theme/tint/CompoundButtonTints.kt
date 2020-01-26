package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.view.ViewCompat
import androidx.core.widget.CompoundButtonCompat
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.color.MaterialColors
import com.google.android.material.radiobutton.MaterialRadioButton
import xyz.aprildown.theme.R
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.layer
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

/**
 * [MaterialCheckBox]
 * https://github.com/material-components/material-components-android/blob/master/docs/components/Checkbox.md
 */
internal class CheckBoxTint : BaseTint<AppCompatCheckBox>(
    attrs = R.styleable.Theme_CompoundButton,
    defStyleAttr = android.R.attr.checkboxStyle,
    onTint = {
        val checkBox = view
        if (checkBox is MaterialCheckBox) {
            withColorOrResourceId(
                R.styleable.Theme_CompoundButton_buttonTint,
                applySolidColor = {
                    CompoundButtonCompat.setButtonTintList(checkBox, it.toColorStateList())
                    ViewCompat.setBackground(checkBox, createCompoundButtonBackground(it))
                },
                applyDefault = {
                    createCompoundButtonTint()?.let {
                        CompoundButtonCompat.setButtonTintList(checkBox, it)
                    }
                    ViewCompat.setBackground(checkBox, createCompoundButtonBackground())
                }
            )
        }
        matchThemeColor(R.styleable.Theme_CompoundButton_android_textColor)?.let {
            checkBox.setTextColor(it)
        }
    }
)

/**
 * [MaterialRadioButton]
 *https://github.com/material-components/material-components-android/blob/master/docs/components/RadioButton.md
 */
internal class RadioButtonTint : BaseTint<AppCompatRadioButton>(
    attrs = R.styleable.Theme_CompoundButton,
    defStyleAttr = android.R.attr.radioButtonStyle,
    onTint = {
        val radioButton = view
        if (radioButton is MaterialRadioButton) {
            withColorOrResourceId(
                R.styleable.Theme_CompoundButton_buttonTint,
                applySolidColor = {
                    CompoundButtonCompat.setButtonTintList(radioButton, it.toColorStateList())
                },
                applyDefault = {
                    createCompoundButtonTint()?.let {
                        CompoundButtonCompat.setButtonTintList(radioButton, it)
                    }
                }
            )
        }
        matchThemeColor(R.styleable.Theme_CompoundButton_android_textColor)?.let {
            radioButton.setTextColor(it)
        }
    }
)

private fun ThemeHelper<*>.createCompoundButtonTint(): ColorStateList? {
    val context = view.context
    val colorControlActivated =
        findAttributeColor(R.attr.colorControlActivated)
            ?: return null
    val colorSurface = context.themeColor(R.attr.colorSurface)
    val colorOnSurface = context.themeColor(R.attr.colorOnSurface)
    return ColorStateList(
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
}

private fun ThemeHelper<*>.createCompoundButtonBackground(colorSecondary: Int? = null): Drawable {
    val currentBackground = view.background
    if (currentBackground is RippleDrawable) {
        // @style/Widget.Material.CompoundButton.CheckBox
        // R.drawable.control_background_40dp_material
        val context = view.context
        val colorControlActivated = colorSecondary
            ?: findAttributeColor(R.attr.colorControlActivated)
            ?: context.themeColor(R.attr.colorControlActivated)
        val colorControlHighlight = findAttributeColor(R.attr.colorControlHighlight)
            ?: context.themeColor(R.attr.colorControlHighlight)
        currentBackground.setColor(
            ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked, android.R.attr.state_enabled),
                    intArrayOf()
                ),
                intArrayOf(
                    // Use the hard-coded value 0.1f instead of the resource id
                    // because the resource id is redefined by the appcompat.
                    colorControlActivated.adjustAlpha(0.1f),
                    colorControlHighlight
                )
            )
        )
    }
    return currentBackground
}
