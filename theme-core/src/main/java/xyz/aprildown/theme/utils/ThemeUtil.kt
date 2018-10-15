package xyz.aprildown.theme.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.drawerlayout.widget.DrawerLayout
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.internal.KEY_ATTRIBUTE

internal val Theme.toolbarIconColor
    @ColorInt
    get() = if (colorPrimary.isColorLight()) Color.BLACK else Color.WHITE

internal val Theme.toolbarTitleColor
    @ColorInt
    get() = if (colorPrimary.isColorLight()) Color.BLACK else Color.WHITE

internal val Theme.toolbarSubtitleColor
    @ColorInt
    get() = toolbarTitleColor.adjustAlpha(.87f)

@CheckResult
internal fun Context.attrKey(@AttrRes attrId: Int): String {
    var name = resources.getResourceName(attrId)
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
internal fun Theme.colorForAttrName(
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