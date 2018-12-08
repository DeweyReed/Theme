package xyz.aprildown.theme.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main1.*
import kotlinx.android.synthetic.main.fragment_main1.view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast
import xyz.aprildown.theme.Theme

class MainFragment1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val c = view.context

        switchDark.isChecked =
                AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        switchDark.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            reloadTheme()
        }

        view.btnDefault.setOnClickListener {
            Theme.edit(c) {
                colorPrimaryRes = R.color.colorPrimary
                colorPrimaryDarkRes = R.color.colorPrimaryDark
                autoColorStatusBar()
                colorAccentRes = R.color.colorAccent
                colorNavigationBarRes = R.color.colorPrimary
            }
            reloadTheme()
        }

        view.btnAmberPurple.setOnClickListener {
            Theme.edit(c) {
                colorPrimaryRes = R.color.md_amber_500
                autoPrimaryDarkAndStatusBar()
                colorAccentRes = R.color.md_deep_purple_400
                colorNavigationBarRes = R.color.md_amber_500
            }
            reloadTheme()
        }

        view.btnBluePink.setOnClickListener {
            Theme.edit(c) {
                colorPrimaryRes = R.color.md_blue_500
                colorPrimaryDarkRes = R.color.md_blue_800
                colorStatusBarRes = R.color.md_blue_800
                colorAccentRes = R.color.md_pink_500
                colorNavigationBarRes = R.color.md_blue_500
            }
            reloadTheme()
        }

        view.btnGreyGreen.setOnClickListener {
            Theme.edit(c) {
                colorPrimaryRes = R.color.md_grey_500
                autoPrimaryDarkAndStatusBar()
                colorAccentRes = R.color.md_green_800
                colorNavigationBarRes = R.color.md_grey_500
            }
            reloadTheme()
        }

        view.btnRedLime.setOnClickListener {
            Theme.edit(c) {
                colorPrimaryRes = R.color.md_red_500
                autoPrimaryDarkAndStatusBar()
                colorAccentRes = R.color.md_lime_800
                colorNavigationBarRes = R.color.md_red_500
            }
            reloadTheme()
        }

        view.btnBlackGrey.setOnClickListener {
            Theme.edit(c) {
                colorPrimaryRes = R.color.md_grey_900
                autoPrimaryDarkAndStatusBar()
                colorAccentRes = R.color.md_grey_400
                colorNavigationBarRes = R.color.md_grey_900
            }
            reloadTheme()
        }

        view.btn.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Dialog Title")
                .setMessage("Dialog Message")
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .setNeutralButton(android.R.string.no, null)
                .show()
        }

        view.btnBorderless.setOnClickListener {
            view.layoutRoot.snackbar("Snackbar", "Action") {
                requireContext().toast("Toast")
            }
        }
    }

    private fun reloadTheme() {
        requireActivity().run {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}