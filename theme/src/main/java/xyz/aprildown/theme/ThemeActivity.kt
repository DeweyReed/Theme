package xyz.aprildown.theme

import androidx.appcompat.app.AppCompatActivity

abstract class ThemeActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        Theme.resume(this)
    }
}
