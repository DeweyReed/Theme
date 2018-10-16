package xyz.aprildown.theme.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.view.menu.ActionMenuItemView
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.tint
import xyz.aprildown.theme.utils.toolbarIconColor

@SuppressLint("RestrictedApi")
internal class ThemeActionMenuItemView(
    context: Context,
    attrs: AttributeSet? = null
) : ActionMenuItemView(context, attrs) {

    companion object {
        const val UNFOCUSED_ALPHA = 0.5f
    }

    private var icon: Drawable? = null

    init {
        invalidateColors(get().toolbarIconColor)
    }

    private fun invalidateColors(color: Int) {
        val sl = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_selected),
                intArrayOf(android.R.attr.state_selected)
            ),
            intArrayOf(
                color.adjustAlpha(UNFOCUSED_ALPHA),
                color
            )
        )
        icon?.let {
            setIcon(it, sl)
        }
        setTextColor(color)
    }

    override fun setIcon(icon: Drawable) {
        super.setIcon(icon)
        // We need to retrieve the color again here.
        // For some reason, without this, a transparent color is used and the icon disappears
        // when the overflow menu opens.
        invalidateColors(get().toolbarIconColor)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun setIcon(
        icon: Drawable,
        colors: ColorStateList
    ) {
        this.icon = icon
        super.setIcon(icon.tint(colors))
    }
}
