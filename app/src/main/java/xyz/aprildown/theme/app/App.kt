package xyz.aprildown.theme.app

import android.app.Application
import xyz.aprildown.theme.Theme

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Theme.init(
            context = this,
            themeRes = R.style.AppTheme,
            appIconRes = R.mipmap.ic_launcher
        )
        Theme.installDelegates(AppComponentsDelegate())
    }
}
