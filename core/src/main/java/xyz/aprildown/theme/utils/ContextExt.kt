@file:Suppress("unused")

package xyz.aprildown.theme.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.*
import androidx.core.content.ContextCompat
import xyz.aprildown.theme.BuildConfig
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

@ColorInt
internal fun Context.color(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

internal fun Context.drawable(@DrawableRes drawable: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawable)
}

@ColorInt
internal fun Context.colorAttr(@AttrRes attr: Int, @ColorInt fallback: Int = 0): Int {
    val a = theme.obtainStyledAttributes(intArrayOf(attr))
    return try {
        a.getColor(0, fallback)
    } catch (ignored: Throwable) {
        fallback
    } finally {
        a.recycle()
    }
}

internal val Context.textColorPrimary: Int
    @ColorInt
    get() = colorAttr(android.R.attr.textColorPrimary)

internal val Context.textColorPrimaryInverse: Int
    @ColorInt
    get() = colorAttr(android.R.attr.textColorPrimaryInverse)

internal val Context.textColorSecondary: Int
    @ColorInt
    get() = colorAttr(android.R.attr.textColorSecondary)

internal val Context.textColorSecondaryInverse: Int
    @ColorInt
    get() = colorAttr(android.R.attr.textColorSecondaryInverse)

@SuppressLint("Recycle")
@IdRes
internal fun Context.resId(
    attrs: AttributeSet? = null,
    @AttrRes attrId: Int,
    fallback: Int = 0
): Int {
    val typedArray = if (attrs != null) {
        obtainStyledAttributes(attrs, intArrayOf(attrId))
    } else {
        theme.obtainStyledAttributes(intArrayOf(attrId))
    }
    try {
        return typedArray.getResourceId(0, fallback)
    } finally {
        typedArray.recycle()
    }
}

@CheckResult
internal fun Resources.safeResourceName(resId: Int): String {
    if (resId == 0) {
        return ""
    }
    return try {
        getResourceName(resId)
    } catch (_: Resources.NotFoundException) {
        if (BuildConfig.DEBUG) Log.w("AttrWizard", "Unable to get resource name for $resId")
        ""
    }
}
