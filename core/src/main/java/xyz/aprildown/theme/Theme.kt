@file:Suppress("unused")

package xyz.aprildown.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.view.Menu
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import xyz.aprildown.theme.internal.*
import xyz.aprildown.theme.tint.ToolbarTint
import xyz.aprildown.theme.utils.*

class Theme private constructor(internal val context: Context) {

    private var prefs: SharedPreferences = context.getThemePrefs()

    // region colors

    val isDark: Boolean
        get() = prefs.getBoolean(KEY_IS_DARK, false)

    @ColorInt
    fun attribute(@AttrRes attrId: Int): Int {
        return prefs.getColorOrDefault(context.attrKey(attrId)) {
            context.colorAttr(attr = attrId)
        }
    }

    @ColorInt
    fun attribute(name: String): Int? {
        val key = attrKey(name)
        return if (prefs.contains(key)) prefs.getInt(key, 0) else null
    }

    val colorPrimary: Int
        @ColorInt
        get() = prefs.getColorOrDefault(KEY_PRIMARY_COLOR) {
            context.colorAttr(attr = R.attr.colorPrimary)
        }

    val colorPrimaryDark: Int
        @ColorInt
        get() = prefs.getColorOrDefault(KEY_PRIMARY_DARK_COLOR) {
            context.colorAttr(attr = R.attr.colorPrimaryDark)
        }

    val colorAccent: Int
        @ColorInt
        get() = prefs.getColorOrDefault(KEY_ACCENT_COLOR) {
            context.colorAttr(attr = R.attr.colorAccent)
        }

    val colorStatusBar: Int
        @ColorInt
        get() = prefs.getColorOrDefault(KEY_STATUS_BAR_COLOR) {
            colorPrimaryDark
        }

    val colorNavigationBar: Int
        @ColorInt
        get() = prefs.getColorOrDefault(KEY_NAV_BAR_COLOR) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                context.colorAttr(android.R.attr.navigationBarColor)
            else Color.BLACK
        }

    // endregion colors

    // region helpers

    val isPrimaryLight: Boolean
        get() = prefs.getBoolean(KEY_IS_PRIMARY_LIGHT, true)

    val toolbarIconColor: Int
        @ColorInt
        get() = context.color(if (isPrimaryLight) R.color.theme_icon_light else R.color.theme_icon_dark)

    val toolbarTitleColor: Int
        @ColorInt
        get() = toolbarIconColor

    val toolbarSubtitleColor: Int
        @ColorInt
        get() = ColorUtils.adjustAlpha(toolbarTitleColor, .87f)

    // endregion helpers

    /**
     * When using default action bar(without adding Toolbar to xml), you don't need this method.
     * Otherwise, you needs to call this method **after** menu inflation in the onCreateOptionsMenu.
     *
     * @param menu onCreateOptionsMenu's parameter
     */
    fun tintMenu(menu: Menu) {
        ToolbarTint.tintMenu(menu, toolbarIconColor)
    }

    companion object {

        // We're holding an application context
        @SuppressLint("StaticFieldLeak")
        private var instance: Theme? = null

        @JvmStatic
        fun get(): Theme = (instance ?: throw IllegalStateException("Requires Theme.init"))

        @JvmStatic
        fun edit(context: Context, f: ThemeEditor.() -> Unit) {
            ThemeEditor(context).run {
                f()
                save()
            }
        }

        @JvmStatic
        fun attach(context: Context, vararg delegates: InflationDelegate) {
            (context as? AppCompatActivity)?.let { activity ->
                LayoutInflaterCompat.setFactory2(
                    activity.layoutInflater,
                    InflationInterceptor(delegates)
                )
            }
        }

        @JvmStatic
        fun resume(context: Context) {
            get().run {
                (context as? Activity)?.let { activity ->
                    activity.setTaskDescriptionColor(colorPrimary)
                    activity.refreshStatusBar(
                        colorStatusBar = colorStatusBar,
                        lightMode = isPrimaryLight
                    )
                    if (prefs.contains(KEY_NAV_BAR_COLOR)) {
                        val navColor = colorNavigationBar
                        activity.setNavigationBarColorCompat(navColor)
                        activity.setLightNavigationBarCompat(ColorUtils.isLightColor(navColor))
                    }
                }
            }
        }

        @JvmStatic
        fun init(app: Application, f: ThemeEditor.() -> Unit) {
            (instance ?: Theme(app).also { instance = it }).run {
                val prefs = prefs
                if (prefs.getBoolean(KEY_FIRST_TIME, true)) {
                    prefs.edit().putBoolean(KEY_FIRST_TIME, false).apply()
                    val editor = ThemeEditor(app)
                    editor.f()
                    editor.save()
                }
            }
        }
    }
}