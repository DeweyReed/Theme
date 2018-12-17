package xyz.aprildown.theme.material.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.material.utils.color
import xyz.aprildown.theme.material.utils.tint
import xyz.aprildown.theme.utils.ColorUtils
import xyz.aprildown.theme.utils.colorForAttrName

internal class ThemeFloatingActionButton(
    context: Context,
    attrs: AttributeSet? = null
) : FloatingActionButton(context, attrs) {

    private val wizard = AttrWizard(context, attrs)
    private val backgroundColorValue = wizard.getRawValue(android.R.attr.background)
    private var iconColor: Int = 0

    init {
        val theme = Theme.get()
        theme.colorForAttrName(backgroundColorValue, theme.colorAccent)?.let {
            invalidateColors(it)
        }
    }

    override fun setImageDrawable(drawable: Drawable?) =
        super.setImageDrawable(drawable?.tint(iconColor))

    private fun invalidateColors(color: Int) {
        setTintSelector(color)
        iconColor = if (ColorUtils.isLightColor(color)) BLACK else WHITE
        setImageDrawable(drawable)
    }

    @SuppressLint("PrivateResource")
    private fun setTintSelector(color: Int) {
        val isColorLight = ColorUtils.isLightColor(color)
        val rippleColor = context.color(
            if (isColorLight) R.color.ripple_material_light
            else R.color.ripple_material_dark
        )
        val pressed = ColorUtils.shiftColor(color, 1.1f)
        val textColor = context.color(
            if (isColorLight) R.color.theme_primary_text_light
            else R.color.theme_primary_text_dark
        )
        // FloatingActionButton doesn't support disabled state?
        val sl = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_pressed), intArrayOf(android.R.attr.state_pressed)
            ),
            intArrayOf(color, pressed)
        )

        this.rippleColor = rippleColor
        this.backgroundTintList = sl
        if (this.drawable != null) {
            setImageDrawable(drawable.tint(textColor))
        }
    }
}
