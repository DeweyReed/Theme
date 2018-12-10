@file:Suppress("unused")

package xyz.aprildown.theme.app

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import xyz.aprildown.theme.Theme

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        Theme.init(this) {
            colorPrimaryRes = R.color.colorPrimary
            colorPrimaryDarkRes = R.color.colorPrimaryDark
            colorAccentRes = R.color.colorAccent
            colorStatusBarRes = R.color.colorPrimaryDark
        }

    }
}