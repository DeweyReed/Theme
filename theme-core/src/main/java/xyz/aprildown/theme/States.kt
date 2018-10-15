package xyz.aprildown.theme

import android.R.attr
import android.content.res.ColorStateList
import androidx.annotation.ColorInt

data class ActiveInactiveColors(
    @field:ColorInt val activeColor: Int,
    @field:ColorInt val inactiveColor: Int
) {
    fun toEnabledSl(): ColorStateList {
        return ColorStateList(
            arrayOf(
                intArrayOf(attr.state_enabled), intArrayOf(-attr.state_enabled)
            ),
            intArrayOf(activeColor, inactiveColor)
        )
    }
}

internal data class ColorIsDarkState(
    @field:ColorInt val color: Int,
    val isDark: Boolean
)
