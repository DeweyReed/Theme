package xyz.aprildown.theme.views.material

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.ColorInt
import com.google.android.material.tabs.TabLayout
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.ColorUtils
import xyz.aprildown.theme.utils.tint

internal class ThemeTabLayout(
    context: Context,
    attrs: AttributeSet? = null
) : TabLayout(context, attrs) {

    companion object {
        private const val UNFOCUSED_ALPHA = 0.5f
    }

    init {
        val theme = Theme.get()

        setIconsColor(theme.toolbarIconColor)

        val toolbarTitleColor = theme.toolbarTitleColor
        setTabTextColors(
            ColorUtils.adjustAlpha(toolbarTitleColor, UNFOCUSED_ALPHA),
            toolbarTitleColor
        )

        setBackgroundColor(theme.colorPrimary)

        setSelectedTabIndicatorColor(theme.colorAccent)
    }

    override fun setBackgroundColor(@ColorInt color: Int) {
        super.setBackgroundColor(color)

        val theme = Theme.get()

        setIconsColor(theme.toolbarIconColor)

        val toolbarTitleColor = theme.toolbarTitleColor
        setTabTextColors(
            ColorUtils.adjustAlpha(toolbarTitleColor, UNFOCUSED_ALPHA),
            toolbarTitleColor
        )
    }

    private fun setIconsColor(color: Int) {
        val sl = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_selected),
                intArrayOf(android.R.attr.state_selected)
            ),
            intArrayOf(
                ColorUtils.adjustAlpha(color, UNFOCUSED_ALPHA),
                color
            )
        )
        for (i in 0 until tabCount) {
            val tab = getTabAt(i)
            if (tab != null && tab.icon != null) {
                tab.icon = tab.icon.tint(sl)
            }
        }
    }
}
