package xyz.aprildown.theme.views.material

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.ColorUtils
import xyz.aprildown.theme.utils.colorForAttrName
import xyz.aprildown.theme.utils.tint

internal class ThemeFloatingActionButton(
    context: Context,
    attrs: AttributeSet? = null
) : FloatingActionButton(context, attrs) {

    private val wizard = AttrWizard(context, attrs)
    private val backgroundColorValue = wizard.getRawValue(android.R.attr.background)
    private var colorOnFab: Int = 0

    init {
        val theme = Theme.get()
        theme.colorForAttrName(backgroundColorValue, theme.colorAccent)?.let {
            colorOnFab = if (ColorUtils.isLightColor(it)) Color.BLACK else Color.WHITE
            setImageDrawable(drawable)

            backgroundTintList = ColorStateList.valueOf(it)
            rippleColor = colorOnFab
        }
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable?.tint(colorOnFab))
    }

}
