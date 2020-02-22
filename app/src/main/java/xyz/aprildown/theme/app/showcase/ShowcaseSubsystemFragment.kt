package xyz.aprildown.theme.app.showcase

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_showcase_subsystem.*
import xyz.aprildown.theme.app.MainActivity
import xyz.aprildown.theme.app.R

class ShowcaseSubsystemFragment : Fragment(R.layout.fragment_showcase_subsystem) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val context = requireContext()
        btnShowcaseSubsystemWidgets.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
