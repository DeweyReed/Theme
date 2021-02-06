package xyz.aprildown.theme.app

import android.os.Bundle
import android.widget.ImageButton
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import xyz.aprildown.theme.app.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
    }

    private fun setUpViews() {
        binding.btnMainPickTheme.setOnClickListener {
            pickTheme()
        }
        binding.btnMainShuffleTheme.setOnClickListener {
            shuffleTheme()
        }
        binding.btnMainDayNight.setOnClickListener {
            toggleDayNight()
        }
        if (isDarkTheme) {
            binding.btnMainDayNight.setImageResource(R.drawable.ic_night)
            binding.btnMainDayNight.tint(android.R.color.darker_gray)
        } else {
            binding.btnMainDayNight.setImageResource(R.drawable.ic_day)
            binding.btnMainDayNight.tint(android.R.color.holo_orange_dark)
        }
    }
}

private fun ImageButton.tint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ContextCompat.getColorStateList(context, colorRes)
    )
}
