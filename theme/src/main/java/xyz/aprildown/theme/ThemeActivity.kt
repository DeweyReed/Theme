package xyz.aprildown.theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class ThemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Theme.tintSystemUi(this)
    }
}
