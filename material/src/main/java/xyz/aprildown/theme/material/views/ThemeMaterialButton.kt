package xyz.aprildown.theme.material.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.material.R
import xyz.aprildown.theme.material.utils.color
import xyz.aprildown.theme.utils.colorForAttrName
import com.google.android.material.R as MaterialR

@SuppressLint("PrivateResource")
internal class ThemeMaterialButton(
    context: Context,
    attrs: AttributeSet? = null
) : MaterialButton(context, attrs) {

    init {
        val wizard = AttrWizard(context, attrs)

        val theme = Theme.get(context)

        var style = attrs?.styleAttribute
        if (style == 0) {
            style = MaterialR.style.Widget_MaterialComponents_Button
        }
        if (
            style == MaterialR.style.Widget_MaterialComponents_Button_TextButton ||
            style == MaterialR.style.Widget_MaterialComponents_Button_TextButton_Icon ||
            style == MaterialR.style.Widget_MaterialComponents_Button_OutlinedButton ||
            style == MaterialR.style.Widget_MaterialComponents_Button_OutlinedButton_Icon
        ) {
            val textColorValue = wizard.getRawValue(android.R.attr.textColor)
            theme.colorForAttrName(textColorValue, theme.colorAccent)?.let {
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
            theme.colorForAttrName(backgroundColorValue, theme.colorAccent)?.let {
                backgroundTintList = ColorStateList.valueOf(it)
            }
        }
    }
}
