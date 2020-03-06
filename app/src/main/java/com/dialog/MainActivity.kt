package com.dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDialog.setOnClickListener {
            val dialog = TestDialog()
            //dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog2)
            dialog.show(supportFragmentManager, "TestDialog")
        }
    }
}
