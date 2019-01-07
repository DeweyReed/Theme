@file:Suppress("SpellCheckingInspection")

package xyz.aprildown.theme.tint

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.ColorUtils
import xyz.aprildown.theme.utils.color
import xyz.aprildown.theme.utils.colorForAttrName
import com.google.android.material.R as MaterialR

@SuppressLint("PrivateResource")
internal fun MaterialButton.decorate(attrs: AttributeSet?) = apply {
    val wizard = AttrWizard(context, attrs)

    var style = attrs?.styleAttribute
    if (style == 0) {
        style = MaterialR.style.Widget_MaterialComponents_Button
    }
    val viewId = id
    if (
        style == MaterialR.style.Widget_MaterialComponents_Button ||
        style == MaterialR.style.Widget_MaterialComponents_Button_Icon ||
        style == MaterialR.style.Widget_MaterialComponents_Button_UnelevatedButton ||
        style == MaterialR.style.Widget_MaterialComponents_Button_UnelevatedButton_Icon
    ) {
        decorateNormalButton(wizard.getRawValue(R.attr.backgroundTint))
    } else if (
        style == MaterialR.style.Widget_MaterialComponents_Button_TextButton ||
        style == MaterialR.style.Widget_MaterialComponents_Button_TextButton_Icon ||
        style == MaterialR.style.Widget_MaterialComponents_Button_TextButton_Dialog ||
        style == MaterialR.style.Widget_MaterialComponents_Button_TextButton_Dialog_Icon ||
        style == MaterialR.style.Widget_MaterialComponents_Button_OutlinedButton ||
        style == MaterialR.style.Widget_MaterialComponents_Button_OutlinedButton_Icon ||
        viewId == android.R.id.button1 ||
        viewId == android.R.id.button2 ||
        viewId == android.R.id.button3
    ) {
        decorateTextButton(wizard.getRawValue(android.R.attr.textColor))
    }
}

@SuppressLint("PrivateResource")
internal fun MaterialButton.decorateNormalButton(backgroundTintName: String) {
    val theme = Theme.get()
    theme.colorForAttrName(backgroundTintName, theme.colorAccent)?.let {
        // mtrl_btn_bg_color_selector.xml
        backgroundTintList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf()
            ),
            intArrayOf(
                it,
                context.color(R.color.mtrl_btn_bg_color_disabled)
            )
        )
    }
}

@SuppressLint("PrivateResource")
internal fun MaterialButton.decorateTextButton(textColorName: String) {
    val theme = Theme.get()
    theme.colorForAttrName(textColorName, theme.colorAccent)?.let {
        // mtrl_text_btn_text_color_selector.xml
        val csl = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf()
            ),
            intArrayOf(
                it,
                context.color(R.color.mtrl_btn_text_color_disabled)
            )
        )

        setTextColor(csl)
        iconTint = csl

        // mtrl_btn_text_btn_ripple.color.xml
        // This doesn't work well if using materia:1.0.0 but this is a bug from MaterialButton
        // which has been fixed in later releases(https://github.com/material-components/material-components-android/commit/ba44d62af4e8201647dab5e887479ff9c3cb5ce0#diff-a613bdc9eafb809ab741177bbca088b0)
        // However, for later releases, something like colorOnPrimary is used.
        rippleColor = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_focused, android.R.attr.state_hovered),
                intArrayOf(android.R.attr.state_focused),
                intArrayOf(android.R.attr.state_hovered),
                intArrayOf()
            ),
            intArrayOf(
                ColorUtils.adjustAlpha(it, 0.16f),
                ColorUtils.adjustAlpha(it, 0.12f),
                ColorUtils.adjustAlpha(it, 0.12f),
                ColorUtils.adjustAlpha(it, 0.04f),
                // A thick color better than nothing
//                    ColorUtils.adjustAlpha(it, 0.00f)
                it
            )
        )
    }
}

internal fun TextInputEditText.decorate(attrs: AttributeSet?) = apply {
    (this as AppCompatEditText).decorate(attrs)
    ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(Theme.get().colorAccent))
}

internal fun TextInputLayout.decorate() = apply {
    val accent = Theme.get().colorAccent
    boxStrokeColor = accent
    defaultHintTextColor = ColorStateList.valueOf(accent)
}

internal fun NavigationView.decorate() = apply {
    val theme = Theme.get()

    val selectedColor = theme.colorPrimary
    val isDark = theme.isDark

    val baseColor = if (isDark) Color.WHITE else Color.BLACK
    val unselectedIconColor = ColorUtils.adjustAlpha(baseColor, .54f)
    val unselectedTextColor = ColorUtils.adjustAlpha(baseColor, .87f)

    val selectedItemBgColor = context.color(
        if (isDark) R.color.theme_md_navigation_drawer_selected_dark
        else R.color.theme_md_navigation_drawer_selected_light
    )

    val iconSl = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_checked)
        ),
        intArrayOf(unselectedIconColor, selectedColor)
    )
    val textSl = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_checked)
        ),
        intArrayOf(unselectedTextColor, selectedColor)
    )

    itemTextColor = textSl
    itemIconTintList = iconSl

    val bgDrawable = StateListDrawable()
    bgDrawable.addState(
        intArrayOf(android.R.attr.state_checked),
        ColorDrawable(selectedItemBgColor)
    )
    itemBackground = bgDrawable
}

@SuppressLint("PrivateResource")
internal fun TabLayout.decorate(attrs: AttributeSet?) = apply {
    val theme = Theme.get()
    val accentColor = theme.colorAccent

    val style = attrs?.styleAttribute ?: 0
    if (
        style == MaterialR.style.Widget_MaterialComponents_TabLayout ||
        style == 0
    ) {
        setSelectedTabIndicatorColor(accentColor)

        // mtrl_tabs_icon_color_selector.xml
        val newIconCsl = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_selected),
                intArrayOf()
            ),
            intArrayOf(
                accentColor,
                Color.parseColor("#99000000")
            )
        )
        tabIconTint = newIconCsl
        tabTextColors = newIconCsl

        // mtrl_tabs_ripple_color.xml
        val unselectedColor = Color.parseColor("#000000")
        val newRippleCsl = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed, android.R.attr.state_selected),
                intArrayOf(
                    android.R.attr.state_focused,
                    android.R.attr.state_hovered,
                    android.R.attr.state_selected
                ),
                intArrayOf(android.R.attr.state_focused, android.R.attr.state_selected),
                intArrayOf(android.R.attr.state_hovered, android.R.attr.state_selected),
                intArrayOf(android.R.attr.state_selected),

                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_focused, android.R.attr.state_hovered),
                intArrayOf(android.R.attr.state_focused),
                intArrayOf(android.R.attr.state_hovered),
                intArrayOf()
            ),
            intArrayOf(
                ColorUtils.adjustAlpha(accentColor, 0.08f),
                ColorUtils.adjustAlpha(accentColor, 0.16f),
                ColorUtils.adjustAlpha(accentColor, 0.12f),
                ColorUtils.adjustAlpha(accentColor, 0.04f),
                ColorUtils.adjustAlpha(accentColor, 0.00f),

                ColorUtils.adjustAlpha(unselectedColor, 0.08f),
                ColorUtils.adjustAlpha(unselectedColor, 0.16f),
                ColorUtils.adjustAlpha(unselectedColor, 0.12f),
                ColorUtils.adjustAlpha(unselectedColor, 0.04f),
                ColorUtils.adjustAlpha(unselectedColor, 0.00f)
            )
        )
        tabRippleColor = newRippleCsl
    } else if (style == MaterialR.style.Widget_MaterialComponents_TabLayout_Colored) {
        setBackgroundColor(accentColor)
    }
}