@file:Suppress("unused")

package xyz.aprildown.theme.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.shape.MaterialShapeDrawable
import xyz.aprildown.theme.BuildConfig
import xyz.aprildown.theme.PREFS_NAME

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

internal fun Context.float(@DimenRes dimenRes: Int): Float =
    ResourcesCompat.getFloat(resources, dimenRes)

@ColorInt
internal fun Context.color(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

internal fun Context.colorStateList(@ColorRes color: Int): ColorStateList? {
    return AppCompatResources.getColorStateList(this, color)
}

internal fun Context.drawable(@DrawableRes drawable: Int): Drawable? {
    return AppCompatResources.getDrawable(this, drawable)
}

internal fun View.setMaterialBackgroundColor(@ColorInt color: Int) {
    val currentBackground = background
    if (currentBackground is MaterialShapeDrawable) {
        currentBackground.fillColor = color.toColorStateList()
    } else {
        setBackgroundColor(color)
    }
}

@ColorInt
internal fun View.getMaterialBackgroundColor(): Int? {
    return when (val background = background) {
        is ColorDrawable -> background.color
        is MaterialShapeDrawable -> background.fillColor?.defaultColor
        else -> null
    }
}

@ColorInt
internal fun Context.themeColor(@AttrRes attrRes: Int): Int {
    val a = obtainStyledAttributes(null, intArrayOf(attrRes))
    try {
        return a.getColor(0, Color.RED)
    } finally {
        a.recycle()
    }
}

@ColorRes
internal fun Context.findAttrFinalResourceId(@AttrRes attrRes: Int): Int {
    val a = obtainStyledAttributes(null, intArrayOf(attrRes))
    try {
        return a.getResourceId(0, -1)
    } finally {
        a.recycle()
    }
}

internal val Context.textColorPrimary: Int
    @ColorInt
    get() = themeColor(android.R.attr.textColorPrimary)

internal val Context.textColorPrimaryInverse: Int
    @ColorInt
    get() = themeColor(android.R.attr.textColorPrimaryInverse)

internal val Context.textColorSecondary: Int
    @ColorInt
    get() = themeColor(android.R.attr.textColorSecondary)

internal val Context.textColorSecondaryInverse: Int
    @ColorInt
    get() = themeColor(android.R.attr.textColorSecondaryInverse)

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
        if (BuildConfig.DEBUG) {
            Log.w(
                "AttrWizard",
                "Unable to get resource name for $resId"
            )
        }
        ""
    }
}

internal fun Drawable.tinted(@ColorInt color: Int): Drawable {
    val result = DrawableCompat.wrap(this.mutate())
    DrawableCompat.setTint(result, color)
    return result
}

internal fun Drawable.tinted(csl: ColorStateList): Drawable {
    val result = DrawableCompat.wrap(this.mutate())
    DrawableCompat.setTintList(result, csl)
    return result
}