package xyz.aprildown.theme.app

import android.os.Bundle
import android.widget.ImageButton
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViews()
    }

    private fun setUpViews() {
        btnMainPickTheme.setOnClickListener {
            pickTheme()
        }
        btnMainDayNight.setOnClickListener {
            toggleDayNight()
        }
        if (isDarkTheme) {
            btnMainDayNight.setImageResource(R.drawable.ic_night)
            btnMainDayNight.tint(android.R.color.darker_gray)
        } else {
            btnMainDayNight.setImageResource(R.drawable.ic_day)
            btnMainDayNight.tint(android.R.color.holo_orange_dark)
        }
    }
}

private fun ImageButton.tint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ContextCompat.getColorStateList(context, colorRes)
    )
}
