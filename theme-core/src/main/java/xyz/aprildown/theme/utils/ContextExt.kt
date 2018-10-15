package xyz.aprildown.theme.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.*
import androidx.core.content.ContextCompat
import xyz.aprildown.theme.internal.PREFS_NAME
import java.lang.reflect.Array
import java.lang.reflect.Field

private fun Context.safeContext(): Context =
    takeIf { Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !isDeviceProtectedStorage }?.let {
        ContextCompat.createDeviceProtectedStorageContext(it) ?: it
    } ?: this

internal fun Context.getThemePrefs(): SharedPreferences =
    safeContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

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


private var mConstructorArgsField: Field? = null

/**
 * Gets around an issue existing before API 16. See
 * https://github.com/afollestad/aesthetic/issues/101.
 */
internal fun Context.fixedLayoutInflater(): LayoutInflater {
    val inflater = LayoutInflater.from(this)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) return inflater

    if (mConstructorArgsField == null) {
        //mConstructorArgs
        mConstructorArgsField = LayoutInflater::class.findField("mConstructorArgs")
    }
    val constructorArgs = mConstructorArgsField!!.get(inflater)
    Array.set(constructorArgs, 0, this)
    mConstructorArgsField!!.set(inflater, constructorArgs)

    return inflater
}
