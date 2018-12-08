package xyz.aprildown.theme.app


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_widget.view.*

class WidgetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_widget, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (view as SwipeRefreshLayout).isRefreshing = true

        view.btnSnackbar.setOnClickListener {
            val sb = Snackbar.make(view, "Snackbar", Snackbar.LENGTH_LONG)
            sb.setAction("Dismiss") {
                if (sb.isShown) sb.dismiss()
            }
            sb.show()
        }

    }
}
