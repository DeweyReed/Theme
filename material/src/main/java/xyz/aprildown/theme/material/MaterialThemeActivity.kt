package xyz.aprildown.theme.material

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.aprildown.theme.Theme

abstract class MaterialThemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.attach(this, MaterialInflationDelegate)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Theme.resume(this)
    }

    override fun onPause() {
        Theme.pause()
        super.onPause()
    }
}