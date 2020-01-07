package xyz.aprildown.theme.app

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageButton
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import xyz.aprildown.theme.Theme

class MainActivity : AppCompatActivity() {

    private var themeValue by bindSharedPreference(this, PREF_THEME, THEME_DEFAULT)

    override fun onCreate(savedInstanceState: Bundle?) {
        if (themeValue == THEME_NONE) {
            setTheme(R.style.AppTheme_WithoutTheme)
        } else {
            setTheme(R.style.AppTheme_WithTheme)
            applyTheme(themeValue)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViews()
    }

    private fun applyTheme(themeValue: Int) {
        when (themeValue) {
            THEME_COLOR_1 -> {
                Theme.edit(this) {
                    colorPrimaryRes = R.color.md_amber_500
                    colorPrimaryVariantRes = R.color.md_amber_800
                    colorOnPrimary = on(colorPrimary)
                    colorSecondaryRes = R.color.md_blue_500
                    colorSecondaryVariantRes = R.color.md_blue_800
                    colorOnSecondary = on(colorSecondary)
                }
            }
            else -> {
                Theme.edit(this) {
                    colorPrimaryRes = R.color.colorPrimary
                    colorPrimaryVariantRes = R.color.colorPrimaryVariant
                    colorOnPrimary = on(colorPrimary)
                    colorSecondaryRes = R.color.colorSecondary
                    colorSecondaryVariantRes = R.color.colorSecondaryVariant
                    colorOnSecondary = on(colorSecondary)
                }
            }
        }
    }

    private fun setUpViews() {
        btnMainPickTheme.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(
                    arrayOf(
                        "Disable",
                        "Default",
                        "Amber + Blue"
                    ),
                    themeValue
                ) { _, which ->
                    themeValue = which
                    ActivityCompat.recreate(this)
                }
                .show()
        }
        btnMainDayNight.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkTheme) {
                    AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
            )
        }
        if (isDarkTheme) {
            btnMainDayNight.setImageResource(R.drawable.ic_night)
            btnMainDayNight.tint(android.R.color.darker_gray)
        } else {
            btnMainDayNight.setImageResource(R.drawable.ic_day)
            btnMainDayNight.tint(android.R.color.holo_orange_dark)
        }
    }

    override fun onResume() {
        super.onResume()
        if (themeValue != THEME_NONE) {
            Theme.resume(this)
        }
    }
}

const val PREF_THEME = "theme"

const val THEME_NONE = 0
const val THEME_DEFAULT = 1
const val THEME_COLOR_1 = 2

val Context.isDarkTheme: Boolean
    get() =
        resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

private fun ImageButton.tint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ContextCompat.getColorStateList(context, colorRes)
    )
}
