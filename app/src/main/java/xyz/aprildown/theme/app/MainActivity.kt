package xyz.aprildown.theme.app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.ThemeActivity

class MainActivity : ThemeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchDark.isChecked =
                AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        switchDark.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            reloadActivity()
        }

        btnMaterial.setOnClickListener {
            startActivity<MaterialActivity>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    fun updateTheme(v: View?) {
        Theme.edit(this) {
            when (v?.id) {
                R.id.btnDefault -> {
                    colorPrimaryRes = R.color.colorPrimary
                    colorPrimaryDarkRes = R.color.colorPrimaryDark
                    colorAccentRes = R.color.colorAccent
                    colorStatusBarRes = R.color.colorPrimaryDark
                    colorNavigationBar = null
                }
                R.id.btnTheme1 -> {
                    colorPrimaryRes = R.color.md_amber_500
                    autoPrimaryDarkAndStatusBar()
                    colorAccentRes = R.color.md_deep_purple_400
                    colorNavigationBarRes = R.color.md_amber_500
                }
                R.id.btnTheme2 -> {
                    colorPrimaryRes = R.color.md_red_500
                    autoPrimaryDarkAndStatusBar()
                    colorAccentRes = R.color.md_blue_500
                    colorNavigationBarRes = R.color.md_red_500
                }
                R.id.btnTheme3 -> {
                    colorPrimaryRes = R.color.md_blue_500
                    colorPrimaryDarkRes = R.color.md_blue_800
                    colorStatusBarRes = R.color.md_blue_800
                    colorAccentRes = R.color.md_pink_500
                    colorNavigationBarRes = R.color.md_blue_500
                }
                R.id.btnTheme4 -> {
                    colorPrimaryRes = R.color.md_grey_500
                    autoPrimaryDarkAndStatusBar()
                    colorAccentRes = R.color.md_green_800
                    colorNavigationBarRes = R.color.md_grey_500
                }
                R.id.btnTheme5 -> {
                    colorPrimaryRes = R.color.md_grey_900
                    autoPrimaryDarkAndStatusBar()
                    colorAccentRes = R.color.md_grey_400
                    colorNavigationBarRes = R.color.md_grey_900
                }
            }
        }
        reloadActivity()
    }

    private fun reloadActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//        recreate()
    }
}
