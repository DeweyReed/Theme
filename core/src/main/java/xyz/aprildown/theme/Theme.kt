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

    private var prefs: SharedPreferences? = null
    private var isResumed = false

    internal val delegates = mutableListOf<InflationDelegate>()

    internal val safeContext
        @CheckResult
        get() = context ?: throw IllegalStateException("Not attached. Theme.context is null.")

    private val safePrefs
        @CheckResult
        get() = prefs ?: throw IllegalStateException("Not attached. Theme.prefs is null.")

    // region colors

    val isDark: Boolean
        get() = safePrefs.getBoolean(KEY_IS_DARK, false)

    @ColorInt
    fun attribute(@AttrRes attrId: Int): Int {
        return safePrefs.getInt(
            safeContext.attrKey(attrId),
            safeContext.colorAttr(attr = attrId)
        )
    }

    @ColorInt
    fun attribute(name: String): Int? {
        val key = attrKey(name)
        return if (safePrefs.contains(key)) safePrefs.getInt(key, 0) else null
    }

    val colorPrimary: Int
        @ColorInt
        get() = safePrefs.getInt(
            KEY_PRIMARY_COLOR,
            safeContext.colorAttr(attr = R.attr.colorPrimary)
        )

    val colorPrimaryDark: Int
        @ColorInt
        get() = safePrefs.getInt(
            KEY_PRIMARY_DARK_COLOR,
            safeContext.colorAttr(attr = R.attr.colorPrimaryDark)
        )

    val colorAccent: Int
        @ColorInt
        get() = safePrefs.getInt(
            KEY_ACCENT_COLOR,
            safeContext.colorAttr(attr = R.attr.colorAccent)
        )

    val colorStatusBar: Int
        @ColorInt
        get() = safePrefs.getInt(
            KEY_STATUS_BAR_COLOR,
            colorPrimaryDark
        )

    val colorNavigationBar: Int
        @ColorInt
        get() = safePrefs.getInt(
            KEY_NAV_BAR_COLOR,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                safeContext.colorAttr(android.R.attr.navigationBarColor)
            else Color.BLACK
        )

    // endregion colors

    fun addDelegate(vararg ds: InflationDelegate) {
        delegates.addAll(ds)
    }

    private fun initPrefs() {
        prefs = safeContext.getThemePrefs()
    }

    private fun deInitPrefs() {
        prefs = null
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var instance: Theme? = null

        @JvmStatic
        fun get(): Theme = instance ?: throw IllegalStateException("Theme.init not called")

        /**
         * Peek some color values when the app is not in the foreground.
         * [Theme.addDelegate] won't work in this way.
         */
        @JvmStatic
        fun peek(context: Context, f: Theme.() -> Unit) {
            val localInstance = Theme(context)
            localInstance.initPrefs()
            localInstance.f()
            localInstance.deInitPrefs()
        }

        @JvmStatic
        fun edit(context: Context? = null, f: ThemeEditor.() -> Unit) {
            val c: Context = context ?: get().safeContext
            val editor = ThemeEditor(c)
            editor.f()
            editor.save()
        }

        @JvmStatic
        fun attach(c: Context) {
            get().run {
                isResumed = false
                context = c
                initPrefs()
            }
            (c as? AppCompatActivity)?.let { it.setInflaterFactory(it.layoutInflater) }
        }

        @JvmStatic
        fun resume(c: Context) {
            get().run {
                if (isResumed) throw IllegalStateException("Theme already resumed")
                isResumed = true
                context = c
                initPrefs()
                (c as? Activity)?.let {
                    it.setTaskDescriptionColor(colorPrimary)
                    invalidateStatusBar()
                    if (safePrefs.contains(KEY_NAV_BAR_COLOR)) {
                        val navColor = colorNavigationBar
                        it.setNavBarColorCompat(navColor)
                        it.setLightNavBarCompat(navColor.isColorLight())
                    }
                }
            }
        }

        @JvmStatic
        fun pause(c: Context) {
            get().run {
                isResumed = false
                if (c is Activity && c.isFinishing && context == c) {
                    deInitPrefs()
                    context = null
                }
            }
        }

        @JvmStatic
        fun init(c: Context, f: ThemeEditor.() -> Unit) {
            if (instance == null) {
                instance = Theme(c)
            }
            val prefs = c.getThemePrefs()
            if (prefs.getBoolean(KEY_FIRST_TIME, true)) {
                prefs.edit().putBoolean(KEY_FIRST_TIME, false).apply()
                val editor = ThemeEditor(c)
                editor.f()
                editor.save()
            }
        }
    }
}