@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package xyz.aprildown.theme

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import xyz.aprildown.theme.internal.KEY_COLOR_ON_PRIMARY
import xyz.aprildown.theme.internal.KEY_COLOR_ON_SECONDARY
import xyz.aprildown.theme.internal.KEY_COLOR_PRIMARY
import xyz.aprildown.theme.internal.KEY_COLOR_PRIMARY_VARIANT
import xyz.aprildown.theme.internal.KEY_COLOR_SECONDARY
import xyz.aprildown.theme.internal.KEY_COLOR_SECONDARY_VARIANT
import xyz.aprildown.theme.internal.KEY_IS_PRIMARY_LIGHT
import xyz.aprildown.theme.internal.KEY_NAV_BAR_COLOR
import xyz.aprildown.theme.internal.KEY_STATUS_BAR_COLOR
import xyz.aprildown.theme.utils.ThemeColorUtils
import xyz.aprildown.theme.utils.color
import xyz.aprildown.theme.utils.getThemePrefs

class ThemeEditor(private val context: Context) {

    private val editor = context.getThemePrefs().edit()

    @ColorInt
    var colorPrimary = 0
        set(value) {
            field = value
            editor.putInt(KEY_COLOR_PRIMARY, value)
            editor.putBoolean(KEY_IS_PRIMARY_LIGHT, ThemeColorUtils.isLightColor(value))
        }
    @ColorRes
    var colorPrimaryRes = 0
        set(value) {
            colorPrimary = context.color(value)
        }

    @ColorInt
    var colorPrimaryVariant = 0
        set(value) {
            field = value
            editor.putInt(KEY_COLOR_PRIMARY_VARIANT, value)
        }
    @ColorRes
    var colorPrimaryVariantRes = 0
        set(value) {
            colorPrimaryVariant = context.color(value)
        }

    @ColorInt
    var colorOnPrimary = 0
        set(value) {
            field = value
            editor.putInt(KEY_COLOR_ON_PRIMARY, value)
        }
    @ColorRes
    var colorOnPrimaryRes = 0
        set(value) {
            colorOnPrimary = context.color(value)
        }

    @ColorInt
    var colorSecondary = 0
        set(value) {
            field = value
            editor.putInt(KEY_COLOR_SECONDARY, value)
        }
    @ColorRes
    var colorSecondaryRes = 0
        set(value) {
            colorSecondary = context.color(value)
        }

    @ColorInt
    var colorSecondaryVariant = 0
        set(value) {
            field = value
            editor.putInt(KEY_COLOR_SECONDARY_VARIANT, value)
        }
    @ColorRes
    var colorSecondaryVariantRes = 0
        set(value) {
            colorSecondaryVariant = context.color(value)
        }

    @ColorInt
    var colorOnSecondary = 0
        set(value) {
            field = value
            editor.putInt(KEY_COLOR_ON_SECONDARY, value)
        }
    @ColorRes
    var colorOnSecondaryRes = 0
        set(value) {
            colorOnSecondary = context.color(value)
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

    fun darker(@ColorInt color: Int): Int = ThemeColorUtils.darker(color)

    fun lighter(@ColorInt color: Int): Int = ThemeColorUtils.lighter(color)

    fun calculateOnColor(@ColorInt color: Int) = if (ThemeColorUtils.isLightColor(color)) {
        Color.BLACK
    } else {
        Color.WHITE
    }

    fun clear() {
        editor.remove(KEY_COLOR_PRIMARY)
            .remove(KEY_COLOR_PRIMARY_VARIANT)
            .remove(KEY_COLOR_ON_PRIMARY)
            .remove(KEY_COLOR_SECONDARY)
            .remove(KEY_COLOR_SECONDARY_VARIANT)
            .remove(KEY_COLOR_ON_SECONDARY)
            .remove(KEY_STATUS_BAR_COLOR)
            .remove(KEY_NAV_BAR_COLOR)
            .remove(KEY_IS_PRIMARY_LIGHT)
    }

    fun save(commit: Boolean = false) {
        if (commit) editor.commit() else editor.apply()
    }
}