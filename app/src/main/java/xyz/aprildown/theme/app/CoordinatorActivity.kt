package xyz.aprildown.theme.app

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_coordinator.*
import xyz.aprildown.theme.material.MaterialThemeActivity

class CoordinatorActivity : MaterialThemeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
