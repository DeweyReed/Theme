package xyz.aprildown.theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class ThemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.attach(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Theme.resume(this)
    }
}