package xyz.aprildown.theme.app

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_showcase_collapsing_toolbar.*

class ShowcaseCollapsingToolbarActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showcase_collapsing_toolbar)
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
}
