package xyz.aprildown.theme.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.drawerlayout.widget.DrawerLayout
import xyz.aprildown.theme.Theme

internal fun Activity.refreshStatusBar(@ColorInt colorStatusBar: Int, lightMode: Boolean) {
    val rootView: ViewGroup? = (findViewById<View>(android.R.id.content) as? ViewGroup)?.run {
        if (childCount > 0) getChildAt(0) as? ViewGroup else null
    }
    if (rootView is DrawerLayout) {
        // Color is set to DrawerLayout, Activity gets transparent status bar
        setStatusBarColorCompat(Color.TRANSPARENT)
        setLightStatusBarCompat(false)
        rootView.setStatusBarBackgroundColor(colorStatusBar)
    } else {
        setStatusBarColorCompat(colorStatusBar)
        // Use colorPrimary to avoid
        // the situation where the toolbar text color and status bar icon color are different
        setLightStatusBarCompat(lightMode)
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