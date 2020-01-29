package xyz.aprildown.theme.app

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_showcase_basic.*

class ShowcaseBasicActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showcase_basic)
        setSupportActionBar(toolbar)

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
        toolbar.setOnTouchListener { _, event ->
            detector.onTouchEvent(event)
        }
    }
}
