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

    private var _prefs: SharedPreferences? = null

    private val safePrefs: SharedPreferences
        get() = _prefs
            ?: throw IllegalStateException("Accessing prefs when the app is in the background")

    // region colors

    val isDark: Boolean
        get() = safePrefs.getBoolean(KEY_IS_DARK, false)

    @ColorInt
    fun attribute(@AttrRes attrId: Int): Int {
        return safePrefs.getColorOrDefault(context.attrKey(attrId)) {
            context.colorAttr(attr = attrId)
        }
    }

    @ColorInt
    fun attribute(name: String): Int? {
        val key = attrKey(name)
        return if (safePrefs.contains(key)) safePrefs.getInt(key, 0) else null
    }

    val colorPrimary: Int
        @ColorInt
        get() = safePrefs.getColorOrDefault(KEY_PRIMARY_COLOR) {
            context.colorAttr(attr = R.attr.colorPrimary)
        }

    val colorPrimaryDark: Int
        @ColorInt
        get() = safePrefs.getColorOrDefault(KEY_PRIMARY_DARK_COLOR) {
            context.colorAttr(attr = R.attr.colorPrimaryDark)
        }

    val colorAccent: Int
        @ColorInt
        get() = safePrefs.getColorOrDefault(KEY_ACCENT_COLOR) {
            context.colorAttr(attr = R.attr.colorAccent)
        }

    val colorStatusBar: Int
        @ColorInt
        get() = safePrefs.getColorOrDefault(KEY_STATUS_BAR_COLOR) {
            colorPrimaryDark
        }

    val colorNavigationBar: Int
        @ColorInt
        get() = safePrefs.getColorOrDefault(KEY_NAV_BAR_COLOR) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                context.colorAttr(android.R.attr.navigationBarColor)
            else Color.BLACK
        }

    // endregion colors

    val isPrimaryLight: Boolean
        get() = safePrefs.getBoolean(KEY_IS_PRIMARY_LIGHT, true)

    /**
     * When using default action bar(without adding Toolbar to xml), you don't need this method.
     * Otherwise, you needs to call this method **after** menu inflation in the onCreateOptionsMenu.
     *
     * @param menu onCreateOptionsMenu's parameter
     */
    fun tintMenu(menu: Menu) {
        ToolbarTint.tintMenu(menu, toolbarIconColor)
    }

    private fun initPrefs() {
        _prefs = context.getThemePrefs()
    }

    private fun deInitPrefs() {
        _prefs = null
    }

    companion object {

        // We're holding an application context
        @SuppressLint("StaticFieldLeak")
        private var instance: Theme? = null

        @JvmStatic
        fun get(): Theme = (instance ?: throw IllegalStateException("Requires Theme.init")).also {
            if (it._prefs == null) {
                it.initPrefs()
            }
        }

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
                    if (safePrefs.contains(KEY_NAV_BAR_COLOR)) {
                        val navColor = colorNavigationBar
                        activity.setNavigationBarColorCompat(navColor)
                        activity.setLightNavigationBarCompat(ColorUtils.isLightColor(navColor))
                    }
                }
            }
        }

        @JvmStatic
        fun pause() {
            instance?.deInitPrefs()
        }

        @JvmStatic
        fun init(app: Application, f: ThemeEditor.() -> Unit) {
            (instance ?: Theme(app).also { instance = it }).run {
                initPrefs()
                val prefs = safePrefs
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