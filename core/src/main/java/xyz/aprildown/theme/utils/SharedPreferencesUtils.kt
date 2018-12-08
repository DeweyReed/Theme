@file:Suppress("unused")

package xyz.aprildown.theme.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import xyz.aprildown.theme.internal.PREFS_NAME

private fun Context.safeContext(): Context =
    takeIf { Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !isDeviceProtectedStorage }?.let {
        ContextCompat.createDeviceProtectedStorageContext(it) ?: it
    } ?: this

internal fun Context.getThemePrefs(): SharedPreferences =
    safeContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

@ColorInt
internal inline fun SharedPreferences.getColorOrDefault(key: String, or: () -> Int): Int {
    return if (contains(key)) getInt(key, 0) else or.invoke()
}