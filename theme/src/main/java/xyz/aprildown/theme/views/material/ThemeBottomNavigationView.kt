package xyz.aprildown.theme.views.material

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.util.AttributeSet
import androidx.annotation.ColorInt
import com.google.android.material.bottomnavigation.BottomNavigationView
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.ColorUtils
import xyz.aprildown.theme.utils.color

internal class ThemeBottomNavigationView(
    context: Context,
    attrs: AttributeSet? = null
) : BottomNavigationView(context, attrs) {

    private var lastTextIconColor: Int = 0
    private var backgroundColor: Int? = null

    init {
        onState()
    }

    private fun invalidateIconTextColor(
        backgroundColor: Int,
        selectedColor: Int
    ) {
        val baseColor = context.color(
            if (ColorUtils.isLightColor(backgroundColor)) R.color.theme_icon_light
            else R.color.theme_icon_dark
        )
        val unselectedIconTextColor = ColorUtils.adjustAlpha(baseColor, .87f)
        val iconColor = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ),
            intArrayOf(unselectedIconTextColor, selectedColor)
        )
        val textColor = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ),
            intArrayOf(unselectedIconTextColor, selectedColor)
        )
        itemIconTintList = iconColor
        itemTextColor = textColor
    }

    override fun setBackgroundColor(@ColorInt color: Int) {
        super.setBackgroundColor(color)
        this.backgroundColor = color
        this.itemBackground = null
        if (lastTextIconColor == Color.TRANSPARENT) {
            lastTextIconColor = if (ColorUtils.isLightColor(color)) BLACK else WHITE
        }
        invalidateIconTextColor(color, lastTextIconColor)
    }

    private fun onState() {
        val theme = Theme.get()
        lastTextIconColor = theme.colorAccent
        invalidateWithBackgroundColor()

        setBackgroundColor(
            context.color(
                if (theme.isDark) R.color.theme_md_bottom_nav_default_dark_bg
                else R.color.theme_md_bottom_nav_default_light_bg
            )
        )
    }

    private fun invalidateWithBackgroundColor() {
        backgroundColor?.let {
            setBackgroundColor(it)
        }
    }
}
