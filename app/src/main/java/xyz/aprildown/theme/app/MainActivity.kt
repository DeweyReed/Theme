package xyz.aprildown.theme.app

import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager.adapter = Adapter(supportFragmentManager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private class Adapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = when (position) {
            0 -> MainFragment()
            1 -> WidgetsFragment()
            2 -> ButtonFragment()
            3 -> OtherFragment()
            else -> throw IndexOutOfBoundsException()
        }

        override fun getCount(): Int = 4

        override fun getPageTitle(position: Int): CharSequence? = when (position) {
            0 -> "Main"
            1 -> "Widget"
            2 -> "Button"
            3 -> "Other"
            else -> throw IndexOutOfBoundsException()
        }
    }
}
