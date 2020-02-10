package xyz.aprildown.theme.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import androidx.drawerlayout.widget.DrawerLayout

private fun Activity.setLightStatusBarCompat(lightMode: Boolean) {
    val view = window?.decorView ?: return
    if (isMOrLater()) {
        var flags = view.systemUiVisibility
        flags = if (lightMode) {
            flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        view.systemUiVisibility = flags
    }
}

internal fun Activity.setStatusBarColorCompat(@ColorInt colorStatusBar: Int, lightMode: Boolean) {
    val rootView: ViewGroup? = (findViewById<View>(android.R.id.content) as? ViewGroup)?.run {
        if (childCount > 0) getChildAt(0) as? ViewGroup else null
    }
    if (rootView is DrawerLayout) {
        // Color is set to DrawerLayout, Activity gets transparent status bar
        window?.statusBarColor = Color.TRANSPARENT
        rootView.setStatusBarBackgroundColor(colorStatusBar)
    } else {
        window?.statusBarColor = colorStatusBar
    }
    setLightStatusBarCompat(lightMode)
}

internal fun Activity.setLightNavigationBarCompat(lightMode: Boolean) {
    val view = window?.decorView ?: return
    if (isOOrLater()) {
        var flags = view.systemUiVisibility
        flags = if (lightMode) {
            flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
        view.systemUiVisibility = flags
    }
}

internal fun Activity.setTaskDescriptionColor(@DrawableRes appIconRes: Int, @ColorInt color: Int) {
    val targetColor = color.stripAlpha()
    if (isPOrLater() && appIconRes != 0) {
        setTaskDescription(
            ActivityManager.TaskDescription(title.toString(), appIconRes, targetColor)
        )
    } else {
        val icon: Bitmap? = if (isOOrLater()) {
            packageManager.getAppIcon(packageName)
        } else {
            applicationInfo.loadIcon(packageManager).toBitmap()
        }
        if (icon != null) {
            @Suppress("DEPRECATION")
            setTaskDescription(
                ActivityManager.TaskDescription(title.toString(), icon, targetColor)
            )
        }
    }
}

private fun PackageManager.getAppIcon(packageName: String): Bitmap? {
    try {
        val drawable = getApplicationIcon(packageName)
        when {
            drawable is BitmapDrawable -> {
                return drawable.bitmap
            }
            isOOrLater() && drawable is AdaptiveIconDrawable -> {
                val drr = arrayOf(drawable.background, drawable.foreground)
                val layerDrawable = LayerDrawable(drr)

                val width = layerDrawable.intrinsicWidth
                val height = layerDrawable.intrinsicHeight

                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)

                layerDrawable.setBounds(0, 0, canvas.width, canvas.height)
                layerDrawable.draw(canvas)

                return bitmap
            }
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return null
}
