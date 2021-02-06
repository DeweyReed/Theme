package xyz.aprildown.theme.app.showcase

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import xyz.aprildown.theme.app.MainActivity
import xyz.aprildown.theme.app.R
import xyz.aprildown.theme.app.databinding.FragmentShowcaseSubsystemBinding

class ShowcaseSubsystemFragment : Fragment(R.layout.fragment_showcase_subsystem) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val context = requireContext()
        val binding = FragmentShowcaseSubsystemBinding.bind(view)
        binding.btnShowcaseSubsystemWidgets.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
