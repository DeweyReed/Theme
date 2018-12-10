package xyz.aprildown.theme.material.tint

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import androidx.core.view.ViewCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.material.R
import xyz.aprildown.theme.material.utils.color
import xyz.aprildown.theme.utils.ColorUtils
import xyz.aprildown.theme.utils.colorForAttrName
import com.google.android.material.R as MaterialR

@Suppress("SpellCheckingInspection")
@SuppressLint("PrivateResource")
internal fun MaterialButton.decorate(attrs: AttributeSet?) = apply {
    val wizard = AttrWizard(context, attrs)

    val theme = Theme.get(context)
    val accentColor = theme.colorAccent

    var style = attrs?.styleAttribute
    if (style == 0) {
        style = MaterialR.style.Widget_MaterialComponents_Button
    }
    val viewId = id
    if (
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
        val textColorValue = wizard.getRawValue(android.R.attr.textColor)
        theme.colorForAttrName(textColorValue, accentColor)?.let {
            // mtrl_text_btn_text_color_selector
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
            rippleColor = ColorStateList.valueOf(it)
        }
    } else if (
        style == MaterialR.style.Widget_MaterialComponents_Button ||
        style == MaterialR.style.Widget_MaterialComponents_Button_Icon ||
        style == MaterialR.style.Widget_MaterialComponents_Button_UnelevatedButton ||
        style == MaterialR.style.Widget_MaterialComponents_Button_UnelevatedButton_Icon
    ) {
        val backgroundColorValue = wizard.getRawValue(android.R.attr.background)
        theme.colorForAttrName(backgroundColorValue, accentColor)?.let {
            backgroundTintList = ColorStateList.valueOf(it)
        }
    }
}

internal fun TextInputEditText.decorate() = apply {
    ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(Theme.get(context).colorAccent))
}

internal fun TextInputLayout.decorate() = apply {
    defaultHintTextColor = ColorStateList.valueOf(Theme.get(context).colorAccent)
}

internal fun NavigationView.decorate() = apply {
    val theme = Theme.get(context)

    val selectedColor = theme.colorPrimary
    val isDark = theme.isDark

    val baseColor = if (isDark) Color.WHITE else Color.BLACK
    val unselectedIconColor = ColorUtils.adjustAlpha(baseColor, .54f)
    val unselectedTextColor = ColorUtils.adjustAlpha(baseColor, .87f)

    val selectedItemBgColor = context.color(
        if (isDark) R.color.ate_navigation_drawer_selected_dark
        else R.color.ate_navigation_drawer_selected_light
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

internal fun SwipeRefreshLayout.decorate() = apply {
    setColorSchemeColors(Theme.get(context).colorAccent)
}