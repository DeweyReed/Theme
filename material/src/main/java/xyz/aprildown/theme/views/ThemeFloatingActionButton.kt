package xyz.aprildown.theme.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.*

internal class ThemeFloatingActionButton(
    context: Context,
    attrs: AttributeSet? = null
) : FloatingActionButton(context, attrs) {

    private val wizard = AttrWizard(context, attrs)
    private val backgroundColorValue = wizard.getRawValue(android.R.attr.background)
    private var iconColor: Int = 0

    init {
        invalidateColors(
            get().colorForAttrName(backgroundColorValue, get().colorAccent)!!
        )
    }

    override fun setImageDrawable(drawable: Drawable?) =
        super.setImageDrawable(drawable?.tint(iconColor))

    private fun invalidateColors(color: Int) {
        setTintSelector(color)
        iconColor = if (color.isColorLight()) BLACK else WHITE
        setImageDrawable(drawable)
    }

    private fun setTintSelector(color: Int) {
        val isColorLight = color.isColorLight()
        val rippleColor = defaultRippleColor(context, isColorLight)
        val pressed = color.shiftColor(1.1f)
        val textColor = context.color(
            if (isColorLight) R.color.ate_primary_text_light
            else R.color.ate_primary_text_dark
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
