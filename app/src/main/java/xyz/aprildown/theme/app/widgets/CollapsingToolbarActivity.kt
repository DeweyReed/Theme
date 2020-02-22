package xyz.aprildown.theme.app.widgets

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        Theme.get().tintMenu(menu)
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
