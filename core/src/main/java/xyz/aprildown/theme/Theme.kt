@file:Suppress("unused")

package xyz.aprildown.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.annotation.AttrRes
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import xyz.aprildown.theme.internal.*
import xyz.aprildown.theme.utils.*

class Theme private constructor(private var context: Context?) {

    private var _prefs: SharedPreferences? = null

    internal val safeContext
        @CheckResult
        get() = context
            ?: throw IllegalStateException("Accessing context when the app is in the background")

    private val safePrefs
        @CheckResult
        get() = _prefs
            ?: throw IllegalStateException("Accessing prefs when the app is in the background")

    // region colors

    val isDark: Boolean
        get() = safePrefs.getBoolean(KEY_IS_DARK, false)

    @ColorInt
    fun attribute(@AttrRes attrId: Int): Int {
        return safePrefs.getColorOrDefault(safeContext.attrKey(attrId)) {
            safeContext.colorAttr(attr = attrId)
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
            safeContext.colorAttr(attr = R.attr.colorPrimary)
        }

    val colorPrimaryDark: Int
        @ColorInt
        get() = safePrefs.getColorOrDefault(KEY_PRIMARY_DARK_COLOR) {
            safeContext.colorAttr(attr = R.attr.colorPrimaryDark)
        }

    val colorAccent: Int
        @ColorInt
        get() = safePrefs.getColorOrDefault(KEY_ACCENT_COLOR) {
            safeContext.colorAttr(attr = R.attr.colorAccent)
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
                safeContext.colorAttr(android.R.attr.navigationBarColor)
            else Color.BLACK
        }

    // endregion colors

    private fun initPrefs() {
        _prefs = safeContext.getThemePrefs()
    }

    private fun deInitPrefs() {
        _prefs = null
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var instance: Theme? = null

        private fun getInstance(): Theme =
            instance ?: throw IllegalStateException("App.init isn't called.")

        /**
         * Safe call version of [Theme.get())].
         */
        @JvmStatic
        fun get(context: Context): Theme = (instance
            ?: throw IllegalStateException("App.init isn't called.")).also {
            if (it.context == null) {
                it.context = context
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
        fun attach(c: Context, vararg delegates: InflationDelegate) {
            getInstance().run {
                context = c
                initPrefs()
            }
            (c as? AppCompatActivity)?.let {
                it.setInflaterFactory(it.layoutInflater, delegates)
            }
        }

        @JvmStatic
        fun resume(c: Context) {
            getInstance().run {
                context = c
                initPrefs()
                (c as? Activity)?.let {
                    it.setTaskDescriptionColor(colorPrimary)
                    invalidateStatusBar()
                    if (safePrefs.contains(KEY_NAV_BAR_COLOR)) {
                        val navColor = colorNavigationBar
                        it.setNavBarColorCompat(navColor)
                        it.setLightNavBarCompat(ColorUtils.isLightColor(navColor))
                    }
                }
            }
        }

        @JvmStatic
        fun pause(c: Context) {
            getInstance().run {
                if (c is Activity && c.isFinishing && context == c) {
                    deInitPrefs()
                    context = null
                }
            }
        }

        @JvmStatic
        fun init(c: Context, f: ThemeEditor.() -> Unit) {
            val applicationContext = c.applicationContext
            if (instance == null) {
                instance = Theme(applicationContext)
            }
            val theme = getInstance()
            theme.initPrefs()
            val prefs = theme.safePrefs
            if (prefs.getBoolean(KEY_FIRST_TIME, true)) {
                prefs.edit().putBoolean(KEY_FIRST_TIME, false).apply()
                val editor = ThemeEditor(applicationContext)
                editor.f()
                editor.save()
            }
        }
    }
}