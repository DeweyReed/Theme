package xyz.aprildown.theme.utils

import android.R.attr
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import java.lang.reflect.Field

@Suppress("DEPRECATION")
internal fun View.setBackgroundCompat(drawable: Drawable?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        background = drawable
    } else {
        setBackgroundDrawable(drawable)
    }
}

fun Toolbar.setOverflowButtonColor(@ColorInt color: Int) {
    val overflowDrawable = overflowIcon
    if (overflowDrawable != null) {
        overflowIcon = overflowDrawable.tint(color)
    }
}

fun Toolbar.tintMenu(
    menu: Menu,
    activeColor: Int,
    inactiveColor: Int
) {
    val colors = ColorStateList(
        arrayOf(
            intArrayOf(attr.state_enabled), intArrayOf(-attr.state_enabled)
        ),
        intArrayOf(activeColor, inactiveColor)
    )

    // The collapse icon displays when action views are expanded (e.g. SearchView)
    try {
        val field = Toolbar::class.findField("mCollapseIcon")
        val collapseIcon = field.get(this) as? Drawable
        if (collapseIcon != null) {
            field.set(this, collapseIcon.tint(colors))
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    // Theme menu action views
    for (i in 0 until menu.size()) {
        val menuItem = menu.getItem(i)
        val actionView = menuItem.actionView
        if (actionView is SearchView) {
            actionView.setColors(activeColor, inactiveColor)
        }
        if (menu.getItem(i).icon != null) {
            menuItem.icon = menuItem.icon.tint(colors)
        }
    }
}

internal fun SearchView.setColors(
    activeColor: Int,
    inactiveColor: Int
) {
    val tintColors = ColorStateList(
        arrayOf(
            intArrayOf(attr.state_enabled), intArrayOf(-attr.state_enabled)
        ),
        intArrayOf(activeColor, inactiveColor)
    )

    try {
        val mSearchSrcTextViewField = this::class.findField("mSearchSrcTextView")
        mSearchSrcTextViewField.isAccessible = true
        val mSearchSrcTextView = mSearchSrcTextViewField.get(this) as EditText
        mSearchSrcTextView.setTextColor(activeColor)
        mSearchSrcTextView.setHintTextColor(inactiveColor)
        mSearchSrcTextView.setCursorTint(activeColor)

        var field = this::class.findField("mSearchButton")
        tintImageView(this, field, tintColors)
        field = this::class.findField("mGoButton")
        tintImageView(this, field, tintColors)
        field = this::class.findField("mCloseButton")
        tintImageView(this, field, tintColors)
        field = this::class.findField("mVoiceButton")
        tintImageView(this, field, tintColors)

        field = this::class.findField("mSearchPlate")
        (field.get(this) as View).apply {
            setTintAuto(
                color = activeColor,
                requestBackground = true,
                isDark = ColorUtils.isDarkColor(activeColor)
            )
        }

        field = this::class.findField("mSearchHintIcon")

        (field.get(this) as Drawable).apply {
            field.set(this@setColors, this@apply.tint(tintColors))
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@Throws(Exception::class)
internal fun tintImageView(
    target: Any,
    field: Field,
    colors: ColorStateList
) {
    field.isAccessible = true
    val imageView = field.get(target) as ImageView
    if (imageView.drawable != null) {
        imageView.setImageDrawable(imageView.drawable.tint(colors))
    }
}
