package xyz.aprildown.theme.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.inflateMenu(R.menu.main)

        viewPager.adapter = MainViewPagerAdapter(supportFragmentManager)
    }

    private class MainViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int = 2

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> MainFragment1()
            1 -> MainFragment2()
            else -> throw IllegalStateException("$position")
        }

        override fun getPageTitle(position: Int): CharSequence? = when (position) {
            0 -> "Tab1"
            1 -> "Tab2"
            else -> throw IllegalStateException("$position")
        }
    }
}
