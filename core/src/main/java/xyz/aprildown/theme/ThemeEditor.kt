@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package xyz.aprildown.theme

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import xyz.aprildown.theme.internal.*
import xyz.aprildown.theme.utils.attrKey
import xyz.aprildown.theme.utils.color
import xyz.aprildown.theme.utils.darkenColor
import xyz.aprildown.theme.utils.getThemePrefs

class ThemeEditor(private val context: Context) {

    private val editor = context.getThemePrefs().edit()

    // region color setters

    fun setDark(isDark: Boolean) = apply {
        editor.putBoolean(KEY_IS_DARK, isDark)
    }

    fun setAttribute(@AttrRes attrId: Int, @ColorInt color: Int) = apply {
        editor.putInt(context.attrKey(attrId), color)
    }

    fun setAttributeRes(@AttrRes attrId: Int, @ColorRes colorRes: Int) = apply {
        setAttribute(attrId, context.color(colorRes))
    }

    fun setColorPrimary(@ColorInt color: Int, autoDarkAndStatusBar: Boolean = false) = apply {
        editor.putInt(KEY_PRIMARY_COLOR, color)
        if (autoDarkAndStatusBar) {
            val darkenColor = color.darkenColor()
            setColorPrimaryDark(darkenColor)
            setColorStatusBar(darkenColor)
        }
    }

    fun setColorPrimaryRes(@ColorRes colorRes: Int, autoDarkAndStatusBar: Boolean = false) = apply {
        setColorPrimary(context.color(colorRes), autoDarkAndStatusBar)
    }

    fun setColorPrimaryDark(@ColorInt color: Int, setColorStatusBar: Boolean = false) = apply {
        editor.putInt(KEY_PRIMARY_DARK_COLOR, color)
        if (setColorStatusBar) {
            setColorStatusBar(color)
        }
    }

    fun setColorPrimaryDarkRes(@ColorRes colorRes: Int, setColorStatusBar: Boolean = false) =
        apply {
            setColorPrimaryDark(context.color(colorRes), setColorStatusBar)
        }

    fun setColorAccent(@ColorInt color: Int) = apply {
        editor.putInt(KEY_ACCENT_COLOR, color)
    }

    fun setColorAccentRes(@ColorRes colorRes: Int) = apply {
        setColorAccent(context.color(colorRes))
    }

    fun setColorStatusBar(@ColorInt color: Int) = apply {
        editor.putInt(KEY_STATUS_BAR_COLOR, color)
    }

    fun setColorStatusBarRes(@ColorRes colorRes: Int) = apply {
        setColorStatusBar(context.color(colorRes))
    }

    fun setColorNavigationBar(@ColorInt color: Int) = apply {
        editor.putInt(KEY_NAV_BAR_COLOR, color)
    }

    fun setColorNavigationBarRes(@ColorRes colorRes: Int) = apply {
        setColorNavigationBar(context.color(colorRes))
    }

    // endregion color setters

    fun save(commit: Boolean = false) {
        if (commit) editor.commit() else editor.apply()
    }
}