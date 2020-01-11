@file:Suppress("unused")

package xyz.aprildown.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.Menu
import androidx.annotation.ColorInt
import xyz.aprildown.theme.internal.KEY_COLOR_ON_PRIMARY
import xyz.aprildown.theme.internal.KEY_COLOR_ON_SECONDARY
import xyz.aprildown.theme.internal.KEY_COLOR_PRIMARY
import xyz.aprildown.theme.internal.KEY_COLOR_PRIMARY_VARIANT
import xyz.aprildown.theme.internal.KEY_COLOR_SECONDARY
import xyz.aprildown.theme.internal.KEY_COLOR_SECONDARY_VARIANT
import xyz.aprildown.theme.internal.KEY_IS_PRIMARY_LIGHT
import xyz.aprildown.theme.internal.KEY_NAV_BAR_COLOR
import xyz.aprildown.theme.internal.KEY_STATUS_BAR_COLOR
import xyz.aprildown.theme.tint.ToolbarTint
import xyz.aprildown.theme.utils.getColorOrDefault
import xyz.aprildown.theme.utils.getThemePrefs
import xyz.aprildown.theme.utils.isLightColor
import xyz.aprildown.theme.utils.setLightNavigationBarCompat
import xyz.aprildown.theme.utils.setStatusBarColorCompat
import xyz.aprildown.theme.utils.setTaskDescriptionColor
import xyz.aprildown.theme.utils.themeColor

class Theme private constructor(private val context: Context) {

    private var prefs: SharedPreferences = context.getThemePrefs()

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
     * When using default action bar(without adding Toolbar to xml), you don't need this method.
     * Otherwise, you needs to call this method **after** menu inflation in the onCreateOptionsMenu.
     *
     * @param menu onCreateOptionsMenu's parameter
     */
    fun tintMenu(menu: Menu) {
        ToolbarTint.tintMenu(menu, colorOnPrimary)
    }

    companion object {

        // We're holding an application context
        @SuppressLint("StaticFieldLeak")
        private var instance: Theme? = null

        @JvmStatic
        fun get(): Theme = (instance ?: throw IllegalStateException("Requires Theme.init"))

        @JvmStatic
        fun init(context: Context, f: (ThemeEditor.() -> Unit)? = null) {
            instance ?: (Theme(context).also {
                instance = it
                if (f != null) {
                    val editor = ThemeEditor(context)
                    editor.f()
                    editor.save()
                }
            })
        }

        @JvmStatic
        fun edit(context: Context, f: ThemeEditor.() -> Unit) {
            ThemeEditor(context).run {
                f()
                save()
            }
        }

        @JvmStatic
        fun resume(context: Context) {
            get().run {
                (context as? Activity)?.let { activity ->
                    activity.setTaskDescriptionColor(colorPrimary)
                    activity.setStatusBarColorCompat(
                        colorStatusBar = colorStatusBar,
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