package xyz.aprildown.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.content.res.TypedArray
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.appcompat.view.ContextThemeWrapper
import xyz.aprildown.theme.utils.getThemePrefs
import xyz.aprildown.theme.utils.isLightColor
import xyz.aprildown.theme.utils.setNavigationBarColorCompat
import xyz.aprildown.theme.utils.setStatusBarColorCompat
import xyz.aprildown.theme.utils.setTaskDescriptionColor
import xyz.aprildown.theme.utils.themeColor

class Theme private constructor(
    private val context: Context,
    @DrawableRes private val appIconRes: Int = 0
) {

    private var prefs: SharedPreferences = context.getThemePrefs()

    var enabled = true

    internal val delegates = mutableListOf<ThemeInflationDelegate>()

    // region colors

    val colorPrimary: Int
        @ColorInt
        get() = getDynamicColorOrDefault(
            colorKey = KEY_COLOR_PRIMARY,
            colorAttrId = R.attr.colorPrimary
        )

    val colorPrimaryVariant: Int
        @ColorInt
        get() = getDynamicColorOrDefault(
            colorKey = KEY_COLOR_PRIMARY_VARIANT,
            colorAttrId = R.attr.colorPrimaryVariant
        )

    val colorOnPrimary: Int
        @ColorInt
        get() = getDynamicColorOrDefault(
            colorKey = KEY_COLOR_ON_PRIMARY,
            colorAttrId = R.attr.colorOnPrimary
        )

    val colorSecondary: Int
        @ColorInt
        get() = getDynamicColorOrDefault(
            colorKey = KEY_COLOR_SECONDARY,
            colorAttrId = R.attr.colorSecondary
        )

    val colorSecondaryVariant: Int
        @ColorInt
        get() = getDynamicColorOrDefault(
            colorKey = KEY_COLOR_SECONDARY_VARIANT,
            colorAttrId = R.attr.colorSecondaryVariant
        )

    val colorOnSecondary: Int
        @ColorInt
        get() = getDynamicColorOrDefault(
            colorKey = KEY_COLOR_ON_SECONDARY,
            colorAttrId = R.attr.colorOnSecondary
        )

    val colorStatusBar: Int
        @ColorInt
        get() = getDynamicColorOrDefault(
            colorKey = KEY_STATUS_BAR_COLOR,
            colorAttrId = android.R.attr.statusBarColor
        )

    val colorNavigationBar: Int
        @ColorInt
        get() = getDynamicColorOrDefault(
            colorKey = KEY_NAV_BAR_COLOR,
            colorAttrId = android.R.attr.navigationBarColor
        )

    @ColorInt
    private fun getDynamicColorOrDefault(colorKey: String, @AttrRes colorAttrId: Int): Int {
        return if (enabled && prefs.contains(colorKey)) {
            prefs.getInt(colorKey, 0)
        } else {
            context.themeColor(colorAttrId)
        }
    }

    // endregion colors

    // region helpers

    val lightStatusByPrimary: Boolean
        get() = prefs.getBoolean(KEY_LIGHT_STATUS_BY_PRIMARY, false)

    // endregion helpers

    companion object {

        // We're holding an application context
        @SuppressLint("StaticFieldLeak")
        internal var instance: Theme? = null

        @JvmStatic
        fun get(): Theme = (instance ?: throw IllegalStateException("Requires Theme.init"))

        /**
         * @param themeRes A style where you define all your theme colors.
         * @param appIconRes Optional. To use a better way to tint the app icon in the recent apps screen.
         */
        @JvmOverloads
        @JvmStatic
        fun init(
            context: Context,
            @StyleRes themeRes: Int,
            @DrawableRes appIconRes: Int = 0,
            f: (ThemeEditor.() -> Unit)? = null
        ) {
            instance ?: (Theme(
                context = ContextThemeWrapper(context, themeRes),
                appIconRes = appIconRes
            ).also {
                instance = it
                val prefs = it.prefs
                if (f != null && prefs.getBoolean(KEY_FIRST_TIME, true)) {
                    prefs.edit().putBoolean(KEY_FIRST_TIME, false).apply()
                    val editor = ThemeEditor(context)
                    editor.f()
                    editor.save()
                }
            })
        }

        /**
         * Order matters.
         */
        @JvmStatic
        fun installDelegates(vararg delegates: ThemeInflationDelegate) {
            get().delegates.addAll(delegates)
        }

        @JvmStatic
        fun edit(context: Context, f: ThemeEditor.() -> Unit) {
            ThemeEditor(context).run {
                f()
                save()
            }
        }

        @JvmStatic
        fun tintSystemUi(activity: Activity) {
            get().run {
                if (!enabled) return@run
                activity.setTaskDescriptionColor(appIconRes, colorPrimary)
                activity.setStatusBarColorCompat(
                    colorStatusBar,
                    (if (lightStatusByPrimary) colorPrimary else colorStatusBar).isLightColor
                )
                if (prefs.contains(KEY_NAV_BAR_COLOR)) {
                    activity.setNavigationBarColorCompat(colorNavigationBar)
                }
            }
        }
    }
}

/**
 * The core of Theme.
 */
internal fun TypedArray.matchThemeColor(@StyleableRes index: Int): Int? = try {
    val resourceId = getResourceId(index, -1)
    if (resourceId != -1) {
        Theme.get().run {
            /**
             * In order to make this implementation work,
             * you have to define this way(I use primary color as the example):
             * <color name="colorPrimary">#FF0000</color>
             * Then in the styles.xml:
             * <style name="AppTheme" ...>
             *     <item name="colorPrimary">@color/colorPrimary</item>
             *     ...
             * </style>
             * The final color(#FF0000)'s name("colorPrimary")
             * must be identical to the values below, or the names here can't be found.
             */
            when (resources.getResourceEntryName(resourceId)) {
                "colorPrimary" -> colorPrimary
                "colorPrimaryVariant" -> colorPrimaryVariant
                "colorOnPrimary" -> colorOnPrimary
                "colorSecondary" -> colorSecondary
                "colorSecondaryVariant" -> colorSecondaryVariant
                "colorOnSecondary" -> colorOnSecondary
                else -> null
            }
        }
    } else {
        null
    }
} catch (e: Resources.NotFoundException) {
    null
}
