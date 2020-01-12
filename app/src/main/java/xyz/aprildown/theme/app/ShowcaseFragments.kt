package xyz.aprildown.theme.app

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.forEach
import androidx.fragment.app.Fragment

class TextViewFragment : Fragment(R.layout.fragment_text_view)

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
        }
    }
}

class ImageFragment : Fragment(R.layout.fragment_image)
