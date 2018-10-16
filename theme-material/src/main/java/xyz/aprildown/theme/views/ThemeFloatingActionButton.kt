package xyz.aprildown.theme.views

import android.content.Context
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.colorForAttrName
import xyz.aprildown.theme.utils.isColorLight
import xyz.aprildown.theme.utils.setTintSelector
import xyz.aprildown.theme.utils.tint

internal class ThemeFloatingActionButton(
    context: Context,
    attrs: AttributeSet? = null
) : FloatingActionButton(context, attrs) {

    private val wizard = AttrWizard(context, attrs)
    private val backgroundColorValue = wizard.getRawValue(android.R.attr.background)
    private var iconColor: Int = 0

    init {
        invalidateColors(
            get().colorForAttrName(backgroundColorValue, get().colorAccent)!!,
            get().isDark
        )
    }

    private fun invalidateColors(color: Int, isDark: Boolean) {
        setTintSelector(color, false, isDark)
        iconColor = if (color.isColorLight()) BLACK else WHITE
        setImageDrawable(drawable)
    }

    override fun setImageDrawable(drawable: Drawable?) =
        super.setImageDrawable(drawable?.tint(iconColor))
}
