package xyz.aprildown.theme.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.shape.MaterialShapeDrawable
import xyz.aprildown.theme.PREFS_NAME

private fun Context.safeContext(): Context =
    takeIf { Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !isDeviceProtectedStorage }?.let {
        ContextCompat.createDeviceProtectedStorageContext(it) ?: it
    } ?: this

internal fun Context.getThemePrefs(): SharedPreferences =
    safeContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

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

// @ColorInt
// internal fun View.getMaterialBackgroundColor(): Int? {
//     return when (val background = background) {
//         is ColorDrawable -> background.color
//         is MaterialShapeDrawable -> background.fillColor?.defaultColor
//         else -> null
//     }
// }

@ColorInt
internal fun Context.themeColor(@AttrRes attrRes: Int): Int {
    val a = obtainStyledAttributes(null, intArrayOf(attrRes))
    try {
        return a.getColor(0, Color.RED)
    } finally {
        a.recycle()
    }
}

internal fun Context.themeFloat(@AttrRes attrRes: Int): Float {
    val a = obtainStyledAttributes(null, intArrayOf(attrRes))
    try {
        return a.getFloat(0, 0f)
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

// internal fun Drawable.tinted(@ColorInt color: Int): Drawable {
//     val result = DrawableCompat.wrap(this.mutate())
//     DrawableCompat.setTint(result, color)
//     return result
// }

internal fun Drawable.tinted(csl: ColorStateList): Drawable {
    val result = DrawableCompat.wrap(this.mutate())
    DrawableCompat.setTintList(result, csl)
    return result
}