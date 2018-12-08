package xyz.aprildown.theme.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_material.*
import xyz.aprildown.theme.material.MaterialThemeActivity

class MaterialActivity : MaterialThemeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            setTitle(R.string.title_activity_material)
        }
        viewPager.adapter = Adapter(supportFragmentManager)
    }

    private class Adapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = when (position) {
            0 -> ButtonFragment()
            1 -> WidgetFragment()
            2 -> OtherFragment()
            else -> throw IndexOutOfBoundsException()
        }

        override fun getCount(): Int = 3

        override fun getPageTitle(position: Int): CharSequence? = when (position) {
            0 -> "Button"
            1 -> "Widget"
            2 -> "Other"
            else -> throw IndexOutOfBoundsException()
        }
    }
}
