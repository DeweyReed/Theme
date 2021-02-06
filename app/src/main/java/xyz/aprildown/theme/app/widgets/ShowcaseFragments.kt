package xyz.aprildown.theme.app.widgets

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.RadioButton
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.app.R
import xyz.aprildown.theme.app.databinding.FragmentDialogBinding

class TextViewFragment : Fragment(R.layout.fragment_text_view)

class TextInputFragment : Fragment(R.layout.fragment_text_input)

class ButtonFragment : Fragment(R.layout.fragment_button)

class CompoundButtonFragment : Fragment(R.layout.fragment_compound_button) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewGroup = view as ViewGroup
        viewGroup.forEach { current ->
            if (current is RadioButton) {
                current.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        viewGroup.forEach { other ->
                            if (other !== current &&
                                other is RadioButton &&
                                other.isEnabled &&
                                other.isChecked
                            ) {
                                other.isChecked = false
                            }
                        }
                    }
                }
            }
            if (current is CheckedTextView) {
                current.setOnClickListener {
                    current.toggle()
                }
            }
        }
    }
}

class ImageFragment : Fragment(R.layout.fragment_image)

class AppBarFragment : Fragment(R.layout.fragment_app_bar)

class ChipFragment : Fragment(R.layout.fragment_chip)

class FabFragment : Fragment(R.layout.fragment_fab)

class CardFragment : Fragment(R.layout.fragment_card)

class BarFragment : Fragment(R.layout.fragment_bar)

class DialogFragment : Fragment(R.layout.fragment_dialog) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val context = view.context
        val binding = FragmentDialogBinding.bind(view)
        binding.btnDialogTimePicker.setOnClickListener {
            TimePickerDialog(context, null, 12, 12, false)
                .show()
        }
        binding.btnDialogMaterialTimePicker.setOnClickListener {
            Theme.get().enabled = false
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setTitleText("MaterialTimePicker")
                .build()
                .apply {
                    addOnDismissListener {
                        Theme.get().enabled = true
                    }
                }
                .show(childFragmentManager, null)
        }
        binding.btnDialogMaterialDateTimePicker.setOnClickListener {
            Theme.get().enabled = false
            MaterialDatePicker.Builder.datePicker()
                .build()
                .apply {
                    addOnDismissListener {
                        Theme.get().enabled = true
                    }
                }
                .show(childFragmentManager, null)
        }
    }
}
