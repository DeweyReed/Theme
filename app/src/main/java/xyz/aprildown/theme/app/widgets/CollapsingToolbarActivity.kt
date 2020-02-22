package xyz.aprildown.theme.app.widgets

import android.os.Bundle
import android.view.GestureDetector
import android.view.Menu
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_collapsing_toolbar.*
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.app.BaseActivity
import xyz.aprildown.theme.app.R

class CollapsingToolbarActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsing_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        val detector = GestureDetectorCompat(this,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                    pickTheme()
                    return true
                }

                override fun onDoubleTap(e: MotionEvent?): Boolean {
                    toggleDayNight()
                    return true
                }
            })
        fab.setOnTouchListener { _, event ->
            detector.onTouchEvent(event)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        Theme.get().tintMenu(menu)
        return true
    }
}
