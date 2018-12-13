package xyz.aprildown.theme.app

import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_coordinator.*
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.material.MaterialThemeActivity

class CoordinatorActivity : MaterialThemeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bottomBar.inflateMenu(R.menu.main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        Theme.get().tintMenu(menu!!)
        return true
    }
}
