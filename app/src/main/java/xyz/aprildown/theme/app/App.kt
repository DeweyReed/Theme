@file:Suppress("unused")

package xyz.aprildown.theme.app

import android.app.Application
import androidx.appcompat.view.ContextThemeWrapper
import xyz.aprildown.theme.Theme

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Theme.init(ContextThemeWrapper(this, R.style.AppTheme)) {
            colorPrimaryRes = R.color.colorPrimary
            colorPrimaryVariantRes = R.color.colorPrimaryVariant
            colorOnPrimary = calculateOnColor(colorPrimary)
            colorSecondaryRes = R.color.colorSecondary
            colorSecondaryVariantRes = R.color.colorSecondaryVariant
            colorOnSecondary = calculateOnColor(colorSecondary)
        }
    }
}
