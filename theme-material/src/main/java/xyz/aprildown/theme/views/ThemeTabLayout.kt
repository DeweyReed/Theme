package xyz.aprildown.theme.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.ColorInt
import com.google.android.material.tabs.TabLayout
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.tint
import xyz.aprildown.theme.utils.toolbarIconColor
import xyz.aprildown.theme.utils.toolbarTitleColor

internal class ThemeTabLayout(
    context: Context,
    attrs: AttributeSet? = null
) : TabLayout(context, attrs) {

    companion object {
        const val UNFOCUSED_ALPHA = 0.5f
    }

    init {
        setIconsColor(get().toolbarIconColor)

        val toolbarTitleColor = get().toolbarTitleColor
        setTabTextColors(toolbarTitleColor.adjustAlpha(UNFOCUSED_ALPHA), toolbarTitleColor)

        setBackgroundColor(get().colorPrimary)

        setSelectedTabIndicatorColor(get().colorAccent)
    }

    @SuppressLint("CheckResult")
    override fun setBackgroundColor(@ColorInt color: Int) {
        super.setBackgroundColor(color)

        setIconsColor(get().toolbarIconColor)

        val toolbarTitleColor = get().toolbarTitleColor
        setTabTextColors(toolbarTitleColor.adjustAlpha(UNFOCUSED_ALPHA), toolbarTitleColor)
    }

    private fun setIconsColor(color: Int) {
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
        for (i in 0 until tabCount) {
            val tab = getTabAt(i)
            if (tab != null && tab.icon != null) {
                tab.icon = tab.icon.tint(sl)
            }
        }
    }
}
