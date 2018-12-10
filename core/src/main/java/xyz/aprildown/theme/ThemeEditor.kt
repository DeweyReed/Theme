@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package xyz.aprildown.theme

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import xyz.aprildown.theme.internal.*
import xyz.aprildown.theme.utils.ColorUtils
import xyz.aprildown.theme.utils.attrKey
import xyz.aprildown.theme.utils.color
import xyz.aprildown.theme.utils.getThemePrefs

class ThemeEditor(private val context: Context) {

    private val editor = context.getThemePrefs().edit()

    var isDark = false
        set(value) {
            field = value
            editor.putBoolean(KEY_IS_DARK, value)
        }

    @ColorInt
    var colorPrimary = 0
        set(value) {
            field = value
            editor.putInt(KEY_PRIMARY_COLOR, value)
        }
    @ColorRes
    var colorPrimaryRes = 0
        set(value) {
            colorPrimary = context.color(value)
        }

    @ColorInt
    var colorPrimaryDark = 0
        set(value) {
            field = value
            editor.putInt(KEY_PRIMARY_DARK_COLOR, value)
        }
    @ColorRes
    var colorPrimaryDarkRes = 0
        set(value) {
            colorPrimaryDark = context.color(value)
        }

    @ColorInt
    var colorAccent = 0
        set(value) {
            field = value
            editor.putInt(KEY_ACCENT_COLOR, value)
        }
    @ColorRes
    var colorAccentRes = 0
        set(value) {
            colorAccent = context.color(value)
        }

    @ColorInt
    var colorStatusBar = 0
        set(value) {
            field = value
            editor.putInt(KEY_STATUS_BAR_COLOR, value)
        }
    @ColorRes
    var colorStatusBarRes = 0
        set(value) {
            colorStatusBar = context.color(value)
        }

    @ColorInt
    var colorNavigationBar: Int? = null
        set(value) {
            field = value
            if (value != null) {
                editor.putInt(KEY_NAV_BAR_COLOR, value)
            } else {
                editor.remove(KEY_NAV_BAR_COLOR)
            }
        }
    @ColorRes
    var colorNavigationBarRes = 0
        set(value) {
            colorNavigationBar = context.color(value)
        }

    fun autoColorPrimaryDark() = apply {
        colorPrimary = ColorUtils.darker(colorPrimary)
    }

    fun autoColorStatusBar() = apply {
        colorStatusBar = ColorUtils.darker(colorPrimary)
    }

    fun autoPrimaryDarkAndStatusBar() = apply {
        val color = ColorUtils.darker(colorPrimary)
        colorPrimaryDark = color
        colorStatusBar = color
    }

    fun setAttribute(@AttrRes attrId: Int, @ColorInt color: Int) = apply {
        editor.putInt(context.attrKey(attrId), color)
    }

    fun setAttributeRes(@AttrRes attrId: Int, @ColorRes colorRes: Int) = apply {
        setAttribute(attrId, context.color(colorRes))
    }

    fun save(commit: Boolean = false) {
        if (commit) editor.commit() else editor.apply()
    }
}