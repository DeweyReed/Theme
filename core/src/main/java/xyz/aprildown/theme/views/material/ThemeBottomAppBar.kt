package xyz.aprildown.theme.views.material

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.google.android.material.bottomappbar.BottomAppBar
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.tint.ToolbarTint
import xyz.aprildown.theme.utils.colorForAttrName
import xyz.aprildown.theme.utils.tint

internal class ThemeBottomAppBar(
    context: Context,
    attrs: AttributeSet? = null
) : BottomAppBar(context, attrs) {

    private val menuIconColor: Int

    init {
        val theme = Theme.get()
        menuIconColor = theme.toolbarIconColor

        val wizard = AttrWizard(context, attrs)

        theme.colorForAttrName(
            wizard.getRawValue(android.R.attr.background),
            theme.colorPrimary
        )?.let {
            backgroundTint = ColorStateList.valueOf(it)
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

        // Check ThemeToolbar's implementation
        post {
            val toolbarIconColor = theme.toolbarIconColor
            ToolbarTint.setOverflowButtonColor(this, toolbarIconColor)
            ToolbarTint.tintMenu(menu, toolbarIconColor)
            navigationIcon = navigationIcon
        }
    }

    override fun setNavigationIcon(icon: Drawable?) {
        super.setNavigationIcon(icon.tint(menuIconColor))
    }
}