package xyz.aprildown.theme.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.tint.setOverflowButtonColor
import xyz.aprildown.theme.tint.tintMenu
import xyz.aprildown.theme.utils.*

internal class ThemeToolbar(
    context: Context,
    attrs: AttributeSet? = null
) : Toolbar(context, attrs) {

    private var menuIconColor: Int? = null

    private val wizard = AttrWizard(context, attrs)
    private val backgroundColorValue = wizard.getRawValue(android.R.attr.background)
    private val titleTextColorValue = wizard.getRawValue(R.attr.titleTextColor)
    private val subtitleTextColorValue = wizard.getRawValue(R.attr.subtitleTextColor)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val theme = get(context)
        theme.colorForAttrName(backgroundColorValue, theme.colorPrimary)?.let {
            setBackgroundColor(it)
        }

        invalidateColors(theme.toolbarIconColor)

        theme.colorForAttrName(titleTextColorValue, theme.toolbarTitleColor)?.let {
            setTitleTextColor(it)
        }

        theme.colorForAttrName(subtitleTextColorValue, theme.toolbarSubtitleColor)?.let {
            setSubtitleTextColor(it)
        }
    }

    override fun setNavigationIcon(icon: Drawable?) {
        if (menuIconColor == null) {
            super.setNavigationIcon(icon)
            return
        }
        super.setNavigationIcon(icon.tint(menuIconColor!!))
    }

    private fun invalidateColors(color: Int) {
        this.menuIconColor = color
        setOverflowButtonColor(color)
        tintMenu(menu, color, ColorUtils.darker(color))
        if (navigationIcon != null) {
            this.navigationIcon = navigationIcon
        }
    }
}
