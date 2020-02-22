@file:Suppress("unused")

package xyz.aprildown.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.Menu
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import xyz.aprildown.theme.tint.tintMenuWithHack
import xyz.aprildown.theme.utils.getColorOrDefault
import xyz.aprildown.theme.utils.getThemePrefs
import xyz.aprildown.theme.utils.isLightColor
import xyz.aprildown.theme.utils.setLightNavigationBarCompat
import xyz.aprildown.theme.utils.setStatusBarColorCompat
import xyz.aprildown.theme.utils.setTaskDescriptionColor
import xyz.aprildown.theme.utils.themeColor

class Theme private constructor(
    private val context: Context,
    @DrawableRes private val appIconRes: Int = 0
) {

    private var prefs: SharedPreferences = context.getThemePrefs()

    internal val delegates = mutableListOf<ThemeInflationDelegate>()

    // region colors

    val colorPrimary: Int
        @ColorInt
        get() = prefs.getColorOrDefault(KEY_COLOR_PRIMARY) {
            context.themeColor(R.attr.colorPrimary)
        }

    val colorPrimaryVariant: Int
        @ColorInt
        get() = prefs.getColorOrDefault(KEY_COLOR_PRIMARY_VARIANT) {
            context.themeColor(R.attr.colorPrimaryVariant)
        }

    val colorOnPrimary: Int
        @ColorInt
        get() = prefs.getColorOrDefault(KEY_COLOR_ON_PRIMARY) {
            context.themeColor(R.attr.colorOnPrimary)
        }

    val colorSecondary: Int
        @ColorInt
        get() = prefs.getColorOrDefault(KEY_COLOR_SECONDARY) {
            context.themeColor(R.attr.colorSecondary)
        }

    val colorSecondaryVariant: Int
        @ColorInt
        get() = prefs.getColorOrDefault(KEY_COLOR_SECONDARY_VARIANT) {
            context.themeColor(R.attr.colorSecondaryVariant)
        }

    val colorOnSecondary: Int
        @ColorInt
        get() = prefs.getColorOrDefault(KEY_COLOR_ON_SECONDARY) {
            context.themeColor(R.attr.colorOnSecondary)
        }

    val colorStatusBar: Int
        @ColorInt
        get() = prefs.getColorOrDefault(KEY_STATUS_BAR_COLOR) {
            colorPrimaryVariant
        }

    val colorNavigationBar: Int
        @ColorInt
        get() = prefs.getColorOrDefault(KEY_NAV_BAR_COLOR) {
            context.themeColor(android.R.attr.navigationBarColor)
        }

    // endregion colors

    // region helpers

    val isPrimaryLight: Boolean
        get() = prefs.getBoolean(KEY_IS_PRIMARY_LIGHT, true)

    // endregion helpers

    /**
     * We make menu nullable before override fun onCreateOptionsMenu(menu: Menu?): Boolean
     */
    fun tintMenu(menu: Menu?) {
        menu?.tintMenuWithHack(context)
    }

    companion object {

        // We're holding an application context
        @SuppressLint("StaticFieldLeak")
        private var instance: Theme? = null

        @JvmStatic
        fun get(): Theme = (instance ?: throw IllegalStateException("Requires Theme.init"))

        /**
         * @param appIconRes Optional. To use a better way to tint the app icon in the recent apps screen.
         */
        @JvmOverloads
        @JvmStatic
        fun init(
            context: Context,
            @DrawableRes appIconRes: Int = 0,
            f: (ThemeEditor.() -> Unit)? = null
        ) {
            instance ?: (Theme(context = context, appIconRes = appIconRes).also {
                instance = it
                if (f != null) {
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
        fun tintSystemUi(context: Context) {
            get().run {
                (context as? Activity)?.let { activity ->
                    activity.setTaskDescriptionColor(appIconRes, colorPrimary)
                    activity.setStatusBarColorCompat(
                        colorStatusBar = colorStatusBar,
                        // Make sure the toolbar text color and the status bar icon color are same.
                        lightMode = isPrimaryLight
                    )
                    if (prefs.contains(KEY_NAV_BAR_COLOR)) {
                        val navColor = colorNavigationBar
                        activity.window?.navigationBarColor = navColor
                        activity.setLightNavigationBarCompat(navColor.isLightColor)
                    }
                }
            }
        }
    }
}