package xyz.aprildown.theme.app.widgets

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import xyz.aprildown.theme.app.BaseActivity
import xyz.aprildown.theme.app.R
import xyz.aprildown.theme.app.databinding.ActivityCollapsingToolbarBinding

class CollapsingToolbarActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCollapsingToolbarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_change_theme -> {
            pickTheme()
            true
        }
        R.id.action_shuffle_theme -> {
            shuffleTheme()
            true
        }
        R.id.action_day_night -> {
            toggleDayNight()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
