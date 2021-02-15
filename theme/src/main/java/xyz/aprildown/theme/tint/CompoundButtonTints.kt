package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.view.ViewCompat
import androidx.core.widget.CompoundButtonCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.color.MaterialColors
import com.google.android.material.elevation.ElevationOverlayProvider
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.switchmaterial.SwitchMaterial
import xyz.aprildown.theme.R
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.getParentAbsoluteElevation
import xyz.aprildown.theme.utils.isNOrLater
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.themeFloat
import xyz.aprildown.theme.utils.toColorStateList

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/Checkbox.md
 * [R.style.Widget_AppCompat_CompoundButton_CheckBox]
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
 * https://github.com/material-components/material-components-android/blob/master/docs/components/RadioButton.md
 * [R.style.Widget_AppCompat_CompoundButton_RadioButton]
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
                    ViewCompat.setBackground(radioButton, createCompoundButtonBackground(it))
                },
                applyDefault = {
                    createCompoundButtonTint()?.let {
                        CompoundButtonCompat.setButtonTintList(radioButton, it)
                        ViewCompat.setBackground(radioButton, createCompoundButtonBackground())
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
            MaterialColors.layer(colorSurface, colorControlActivated, MaterialColors.ALPHA_FULL),
            MaterialColors.layer(colorSurface, colorOnSurface, MaterialColors.ALPHA_MEDIUM),
            MaterialColors.layer(colorSurface, colorOnSurface, MaterialColors.ALPHA_DISABLED),
            MaterialColors.layer(colorSurface, colorOnSurface, MaterialColors.ALPHA_DISABLED)
        )
    )
}

private fun ThemeHelper<*>.createCompoundButtonBackground(colorSecondary: Int? = null): Drawable? {
    val currentBackground = view.background
    if (currentBackground is RippleDrawable) {
        /**
         * R.drawable.control_background_40dp_material
         * [R.style.Widget_AppCompat_CompoundButton_CheckBox]
         * [R.style.Widget_AppCompat_CompoundButton_RadioButton]
         *
         * I can't find the background resource for Switch. However,
         * when I track its background at runtime, the background is the same as other
         * compound buttons except its ripple alpha. It seems like a typo and I decide to use
         * the same alpha.
         */
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

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/Switch.md
 * [R.style.Widget_AppCompat_CompoundButton_Switch]
 */
internal class SwitchMaterialTint : BaseTint<SwitchMaterial>(
    attrs = R.styleable.Theme_Switch,
    defStyleAttr = android.R.attr.switchStyle,
    onTint = {
        val switch = view
        withColorOrResourceId(
            R.styleable.Theme_Switch_thumbTint,
            applySolidColor = {
                switch.thumbTintList = it.toColorStateList()
                ViewCompat.setBackground(switch, createCompoundButtonBackground(it))
            },
            applyDefault = {
                switch.thumbTintList = createSwitchThumbTintList()
                ViewCompat.setBackground(switch, createCompoundButtonBackground())
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_Switch_trackTint,
            applySolidColor = {
                switch.trackTintList = it.toColorStateList()
            },
            applyDefault = {
                switch.trackTintList = createSwitchTrackTintList()
            }
        )
        matchThemeColor(R.styleable.Theme_CompoundButton_android_textColor)?.let {
            switch.setTextColor(it)
        }
    }
)

private fun ThemeHelper<*>.createSwitchThumbTintList(): ColorStateList? {
    val context = view.context
    val colorControlActivated = findAttributeColor(R.attr.colorControlActivated) ?: return null
    val colorSurface = context.themeColor(R.attr.colorSurface)
    var thumbElevation: Float = context.resources.getDimension(R.dimen.mtrl_switch_thumb_elevation)
    val elevationOverlayProvider = ElevationOverlayProvider(context)
    if (elevationOverlayProvider.isThemeElevationOverlayEnabled) {
        thumbElevation += getParentAbsoluteElevation(view)
    }
    val colorThumbOff =
        elevationOverlayProvider.compositeOverlayIfNeeded(colorSurface, thumbElevation)

    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_enabled, android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_enabled, -android.R.attr.state_checked)
        ),
        intArrayOf(
            MaterialColors.layer(colorSurface, colorControlActivated, MaterialColors.ALPHA_FULL),
            colorThumbOff,
            MaterialColors.layer(
                colorSurface,
                colorControlActivated,
                MaterialColors.ALPHA_DISABLED
            ),
            colorThumbOff
        )
    )
}

private fun ThemeHelper<*>.createSwitchTrackTintList(): ColorStateList? {
    val context = view.context
    val colorControlActivated = findAttributeColor(R.attr.colorControlActivated) ?: return null
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
            MaterialColors.layer(colorSurface, colorControlActivated, MaterialColors.ALPHA_MEDIUM),
            MaterialColors.layer(colorSurface, colorOnSurface, MaterialColors.ALPHA_LOW),
            MaterialColors.layer(colorSurface, colorControlActivated, MaterialColors.ALPHA_LOW),
            MaterialColors.layer(colorSurface, colorOnSurface, MaterialColors.ALPHA_DISABLED_LOW)
        )
    )
}

internal class CheckedTextViewTint : BaseTint<AppCompatCheckedTextView>(
    attrs = R.styleable.Theme_CheckedTextView,
    defStyleAttr = android.R.attr.checkedTextViewStyle,
    onTint = {
        val context = view.context
        val resources = view.resources
        val checkedTextView = view

        // This piece of code only tints CheckedTextView from MaterialAlertDialog for N or later.
        val drawableResId =
            typedArray.getResourceId(R.styleable.Theme_CheckedTextView_drawableStartCompat, -1)
        if (drawableResId != -1 &&
            TextViewCompat.getCompoundDrawableTintList(checkedTextView) == null
        ) {
            if (resources.getResourcePackageName(drawableResId) == "android" &&
                resources.getResourceTypeName(drawableResId) == "drawable" &&
                resources.getResourceEntryName(drawableResId).let {
                    it == "btn_radio_material_anim" || it == "btn_check_material_anim"
                } &&
                isNOrLater() // control_checkable_material is imported from N
            ) {
                /** [android.R.color.control_checkable_material] */
                val colorControlActivated = findAttributeColor(android.R.attr.colorControlActivated)
                if (colorControlActivated != null) {
                    val colorControlNormal = context.themeColor(android.R.attr.colorControlNormal)
                    TextViewCompat.setCompoundDrawableTintList(
                        checkedTextView,
                        ColorStateList(
                            arrayOf(
                                intArrayOf(-android.R.attr.state_enabled),
                                intArrayOf(android.R.attr.state_checked),
                                intArrayOf()
                            ),
                            intArrayOf(
                                colorControlNormal.adjustAlpha(context.themeFloat(android.R.attr.disabledAlpha)),
                                colorControlActivated,
                                colorControlNormal
                            )
                        )
                    )
                }
            }
        }

        matchThemeColor(R.styleable.Theme_CheckedTextView_android_checkMarkTint)?.let {
            checkedTextView.checkMarkTintList = it.toColorStateList()
        }
    }
)
