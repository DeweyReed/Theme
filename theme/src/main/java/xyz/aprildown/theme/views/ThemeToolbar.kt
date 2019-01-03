package xyz.aprildown.theme.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.tint.ToolbarTint
import xyz.aprildown.theme.utils.colorForAttrName
import xyz.aprildown.theme.utils.tint

internal class ThemeToolbar(
    context: Context,
    attrs: AttributeSet? = null
) : Toolbar(context, attrs) {

    private val menuIconColor: Int

    init {
        val theme = Theme.get()
        menuIconColor = theme.toolbarIconColor

        val wizard = AttrWizard(context, attrs)

        theme.colorForAttrName(
            wizard.getRawValue(android.R.attr.background),
            theme.colorPrimary
        )?.let {
            setBackgroundColor(it)
        }

        theme.colorForAttrName(
            wizard.getRawValue(R.attr.titleTextColor),
            theme.toolbarTitleColor
        )?.let {
            setTitleTextColor(it)
        }

        theme.colorForAttrName(
            wizard.getRawValue(R.attr.subtitleTextColor),
            theme.toolbarSubtitleColor
        )?.let {
            setSubtitleTextColor(it)
        }

        // Sometimes overflow icon won't be tinted. Use post to fix it.
        post {
            val toolbarIconColor = theme.toolbarIconColor
            ToolbarTint.setOverflowButtonColor(this, toolbarIconColor)
            ToolbarTint.tintMenu(menu, toolbarIconColor)
            // Trigger setNavigationIcon
            navigationIcon = navigationIcon
        }
    }

    override fun setNavigationIcon(icon: Drawable?) {
        super.setNavigationIcon(icon.tint(menuIconColor))
    }
}
