package com.dialog

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cbfg.dialog.BDFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val callback: (() -> Unit) by lazy {
        { Toast.makeText(this, "Click", Toast.LENGTH_LONG).show() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setFullScreen(window)
        window.decorView.setOnSystemUiVisibilityChangeListener { setFullScreen(window) }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDialog.setOnClickListener {
//            showCallbackDialog()
            TestDialog().show(supportFragmentManager, "TestDialog")
//            TDialog().show(supportFragmentManager,"TestDialog")
//            showDialog()
        }

        /*
         * 屏幕旋转后
         */
        if (savedInstanceState != null) {
            (supportFragmentManager.findFragmentByTag("CallbackDialog") as TestCallbackDialog?)?.setCallback(
                callback
            )
        }
    }

    private fun showCallbackDialog() {
        val dialog = TestCallbackDialog()
        dialog.setCallback(callback)
        dialog.show(supportFragmentManager, "CallbackDialog")
    }

    private fun showDialog() {
        /*
         * 报错:
         * java.lang.IllegalStateException: Fragment null must be a public static class to be  properly recreated from instance state.
         */
        object : BDFragment() {
            init {
                layoutRes = R.layout.dialog_test
            }
        }.show(supportFragmentManager, "TestDialog")
    }

    class TDialog : BDFragment() {
        init {
            layoutRes = R.layout.dialog_test
        }
    }

    private fun setFullScreen(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //设置 Android 4.4 的全屏效果
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}
