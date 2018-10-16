@file:Suppress("unused")

package xyz.aprildown.theme.app

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import xyz.aprildown.theme.MaterialInflationDelegate
import xyz.aprildown.theme.Theme

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        Theme.init(this) {
            setColorPrimaryRes(R.color.colorPrimaryDefault)
            setColorPrimaryDarkRes(R.color.colorPrimaryDarkDefault, true)
            setColorAccentRes(R.color.colorAccentDefault)
        }
        Theme.get().addDelegate(MaterialInflationDelegate())
    }
}