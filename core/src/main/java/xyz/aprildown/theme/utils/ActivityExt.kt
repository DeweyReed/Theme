package xyz.aprildown.theme.utils

import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityManager
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.view.View
import androidx.annotation.ColorInt

internal fun Activity.setStatusBarColorCompat(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = color
    }
}

internal fun Activity?.setNavBarColorCompat(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this?.window?.navigationBarColor = color
    }
}

internal fun Activity?.setLightStatusBarCompat(lightMode: Boolean) {
    val view = this?.window?.decorView ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = view.systemUiVisibility
        flags = if (lightMode) {
            flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        view.systemUiVisibility = flags
    }
}

internal fun Activity?.setLightNavBarCompat(lightMode: Boolean) {
    val view = this?.window?.decorView ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        var flags = view.systemUiVisibility
        flags = if (lightMode) {
            flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
        view.systemUiVisibility = flags
    }
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
internal fun Activity?.setTaskDescriptionColor(@ColorInt requestedColor: Int) {
    if (this == null || Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) return
    var color = requestedColor

    // Task description requires fully opaque color
    color = ColorUtils.stripAlpha(color)
    // Default is app's launcher icon
    val icon: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        packageManager.getAppIcon(packageName)
    } else {
        (applicationInfo.loadIcon(packageManager) as BitmapDrawable)
            .bitmap
    }
    if (icon != null) {
        // Sets color of entry in the system recent page
        @Suppress("DEPRECATION")
        val td = ActivityManager.TaskDescription(title as String, icon, color)
        setTaskDescription(td)
    }
}

private fun PackageManager.getAppIcon(packageName: String): Bitmap? {
    try {
        val drawable = getApplicationIcon(packageName)
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            && drawable is AdaptiveIconDrawable
        ) {
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
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return null
}
