package xyz.aprildown.theme.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_other.view.*

class OtherFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_other, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val c = view.context
        view.btnShowDialog.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Dialog Title")
                .setMessage("Dialog Message")
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .setNeutralButton(android.R.string.no, null)
                .show()
        }
        view.btnSnackbar.setOnClickListener {
            val sb = Snackbar.make(view, "Snackbar", Snackbar.LENGTH_LONG)
            sb.setAction("Dismiss") {
                if (sb.isShown) sb.dismiss()
            }
            sb.show()
        }
        view.btnCoordinator.setOnClickListener {
            startActivity(Intent(c, CoordinatorActivity::class.java))
        }
    }
}