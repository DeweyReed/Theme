package xyz.aprildown.theme.app.showcase

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import xyz.aprildown.theme.app.BaseActivity
import xyz.aprildown.theme.app.R
import xyz.aprildown.theme.app.databinding.ActivityShowcaseBinding

/**
 * Copied and then modified from [https://github.com/material-components/material-components-android/tree/master/material-theme-builder]
 */
class ShowcaseActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityShowcaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.viewPagerShowcase.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 2

            override fun createFragment(position: Int): Fragment = when (position) {
                0 -> ShowcaseSubsystemFragment()
                1 -> ShowcaseComponentFragment()
                else -> throw IllegalStateException()
            }
        }
        TabLayoutMediator(
            binding.tabLayoutShowcase,
            binding.viewPagerShowcase
        ) { tab, position ->
            tab.text = when (position) {
                0 -> "Subsystem"
                1 -> "Component"
                else -> throw IllegalStateException()
            }
        }.attach()
        // viewPagerShowcase.currentItem = 1
    }

    // /**
    //  * Code to show off.
    //  */
    // override fun recreate() {
    //     if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
    //         startActivity(Intent(this, ShowcaseActivity::class.java))
    //         finish()
    //         overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    //     } else {
    //         super.recreate()
    //     }
    // }

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
