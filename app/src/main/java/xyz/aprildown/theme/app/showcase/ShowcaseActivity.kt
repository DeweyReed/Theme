package xyz.aprildown.theme.app.showcase

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_showcase.*
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.app.BaseActivity
import xyz.aprildown.theme.app.R

/**
 * Copied and then modified from [https://github.com/material-components/material-components-android/tree/master/material-theme-builder]
 */
class ShowcaseActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showcase)
        setSupportActionBar(toolbar)

        viewPagerShowcase.adapter = object : FragmentStatePagerAdapter(
            supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount(): Int = 2
            override fun getPageTitle(position: Int): CharSequence? = when (position) {
                0 -> "Subsystem"
                1 -> "Component"
                else -> throw IllegalStateException()
            }

            override fun getItem(position: Int): Fragment = when (position) {
                0 -> ShowcaseSubsystemFragment()
                1 -> ShowcaseComponentFragment()
                else -> throw IllegalStateException()
            }
        }
        tabLayoutShowcase.setupWithViewPager(viewPagerShowcase)
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
        R.id.action_day_night -> {
            toggleDayNight()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
