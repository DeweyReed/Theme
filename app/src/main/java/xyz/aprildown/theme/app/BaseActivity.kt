package xyz.aprildown.theme.app

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import xyz.aprildown.theme.Theme
import kotlin.random.Random

abstract class BaseActivity : AppCompatActivity() {

    private var themeValue: Int
        get() = safeSharedPreference.getInt(PREF_THEME, THEME_DEFAULT)
        set(value) = safeSharedPreference.edit { putInt(PREF_THEME, value) }

    private val spListener by lazy {
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == PREF_THEME) {
                ActivityCompat.recreate(this@BaseActivity)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (themeValue == THEME_NONE) {
            setTheme(R.style.AppTheme_WithoutTheme)
        } else {
            setTheme(R.style.AppTheme_WithTheme)
        }
        super.onCreate(savedInstanceState)
    }

    /**
     * We can set up Theme during setContentView instead of onCreate
     * because all we care is view creation.
     */
    override fun setContentView(view: View?) {
        super.setContentView(view)
        if (themeValue != THEME_NONE && !isDarkTheme) {
            Theme.tintSystemUi(this)
        }
        safeSharedPreference.registerOnSharedPreferenceChangeListener(spListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        safeSharedPreference.unregisterOnSharedPreferenceChangeListener(spListener)
    }

    private fun applyTheme(themeValue: Int) {
        when (themeValue) {
            THEME_COLOR_1 -> {
                Theme.edit(this) {
                    colorPrimaryRes = R.color.md_amber_500
                    colorPrimaryVariantRes = R.color.md_amber_800
                    colorStatusBar = colorPrimaryVariant
                    colorOnPrimary = on(colorPrimary)
                    colorSecondaryRes = R.color.md_blue_500
                    colorSecondaryVariantRes = R.color.md_blue_800
                    colorOnSecondary = on(colorSecondary)
                    lightStatusByPrimary = true
                }
            }
            THEME_DEFAULT -> {
                Theme.edit(this) {
                    colorPrimaryRes = R.color.colorPrimary
                    colorPrimaryVariantRes = R.color.colorPrimaryVariant
                    colorStatusBar = colorPrimaryVariant
                    colorOnPrimary = on(colorPrimary)
                    colorSecondaryRes = R.color.colorSecondary
                    colorSecondaryVariantRes = R.color.colorSecondaryVariant
                    colorOnSecondary = on(colorSecondary)
                }
            }
        }
    }

    protected fun shuffleTheme() {
        Theme.edit(this) {
            colorPrimary = randomColor
            colorPrimaryVariant = darker(colorPrimary)
            colorStatusBar = colorPrimaryVariant
            colorOnPrimary = on(colorPrimary)
            colorSecondary = randomColor
            colorSecondaryVariant = darker(colorSecondary)
            colorOnSecondary = on(colorSecondary)
            lightStatusByPrimary = true
            colorNavigationBar = if (Random.nextBoolean()) colorPrimary else null
        }
        themeValue = THEME_SHUFFLE + Random.nextInt()
    }

    protected fun pickTheme() {
        MaterialAlertDialogBuilder(this)
            .setSingleChoiceItems(
                arrayOf(
                    "Disable",
                    "Default",
                    "Amber + Blue"
                ),
                themeValue
            ) { dialog, which ->
                applyTheme(which)
                themeValue = which
                // It won't close on Lollipop devices so we force closing..
                dialog.dismiss()
            }
            .show()
    }

    protected fun toggleDayNight() {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme) {
                AppCompatDelegate.MODE_NIGHT_NO
            } else {
                AppCompatDelegate.MODE_NIGHT_YES
            }
        )
    }
}

const val PREF_THEME = "theme"

const val THEME_NONE = 0
const val THEME_DEFAULT = 1
const val THEME_COLOR_1 = 2
const val THEME_SHUFFLE = 3

val Context.isDarkTheme: Boolean
    get() =
        resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

private val randomColor: Int
    get() = Color.rgb(Random.nextInt(), Random.nextInt(), Random.nextInt())
