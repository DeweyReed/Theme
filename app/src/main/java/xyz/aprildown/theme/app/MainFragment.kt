package xyz.aprildown.theme.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import xyz.aprildown.theme.Theme

class MainFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.switchDark.isChecked =
                AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        view.switchDark.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            reloadActivity()
        }
        listOf(btnDefault, btnTheme1, btnTheme2, btnTheme3, btnTheme4, btnTheme5).forEach {
            it.setOnClickListener(this@MainFragment)
        }
    }

    override fun onClick(v: View?) {
        Theme.edit(requireContext()) {
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
        requireActivity().run {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//        recreate()
        }
    }
}