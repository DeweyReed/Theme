package xyz.aprildown.theme.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.drawerlayout.widget.DrawerLayout
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.internal.KEY_ATTRIBUTE

// region derived colors

val Theme.toolbarIconColor
    @ColorInt
    get() = safeContext.color(
        if (colorPrimary.isColorLight()) R.color.ate_icon_light
        else R.color.ate_icon_dark
    )

val Theme.toolbarTitleColor
    @ColorInt
    get() = toolbarIconColor

val Theme.toolbarSubtitleColor
    @ColorInt
    get() = toolbarTitleColor.adjustAlpha(.87f)

// endregion derived colors

@CheckResult
internal fun Context.attrKey(@AttrRes attrId: Int): String {
    var name = resources.safeResourceName(attrId)
    if (!name.startsWith("android")) {
        name = name.substring(name.indexOf(':') + 1)
    }
    return attrKey(name)
}

@CheckResult
internal fun attrKey(name: String) = String.format(KEY_ATTRIBUTE, name)

internal fun Theme.invalidateStatusBar() {
    with(safeContext as? Activity ?: return) {
        val color = colorStatusBar

        val rootView = getRootView()
        if (rootView is DrawerLayout) {
            // Color is set to DrawerLayout, Activity gets transparent status bar
            setLightStatusBarCompat(false)
            setStatusBarColorCompat(Color.TRANSPARENT)
            rootView.setStatusBarBackgroundColor(color)
        } else {
            setStatusBarColorCompat(color)
        }
        setLightStatusBarCompat(color.isColorLight())
    }
}

@CheckResult
fun Theme.colorForAttrName(
    name: String,
    fallback: Int? = null
): Int? {
    if (name.isNotEmpty() && !name.startsWith('?')) {
        // Don't override the hardcoded or resource value that is set.
        return null
    }
    return when (name) {
        "" -> fallback

        "?attr/colorPrimary", "?android:attr/colorPrimary" -> colorPrimary
        "?attr/colorPrimaryDark", "?android:attr/colorPrimaryDark" -> colorPrimaryDark
        "?attr/colorAccent", "?android:attr/colorAccent" -> colorAccent

        "?android:attr/statusBarColor" -> colorStatusBar
        "?android:attr/navigationBarColor" -> colorNavigationBar

        else -> fallback ?: attribute(name.substring(1))
    }
}