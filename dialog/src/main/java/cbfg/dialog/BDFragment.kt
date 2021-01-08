package cbfg.dialog

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment

abstract class BDFragment : DialogFragment() {
    //如果传入布局资源则表示通过 onCreateView 进行布局
    var layoutRes = 0
    var isFullScreen = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layoutRes == 0) {
            return super.onCreateView(inflater, container, savedInstanceState)
        }
        if (dialog?.window?.isFloating == true) {
            return inflater.inflate(layoutRes, container, false)
        }
        return DFrameLayout(context!!).also {
            it.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            it.setOnTouchOutsideListener {
                if (isCancelable) {
                    dismiss()
                }
            }
            inflater.inflate(layoutRes, it, true)
        }
    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        //如果不通过 onCreateView 布局的话不用覆写此方法
        //通过 onCreateDialog 布局的话覆不覆写这里没有影响
        if (layoutRes == 0) {
            return super.onGetLayoutInflater(savedInstanceState)
        }
        if (theme == 0) {
            setStyle(
                STYLE_NORMAL,
                if (isFullScreen) R.style.FullScreenDialogTheme else R.style.BaseDialogTheme
            )
        }
        super.onGetLayoutInflater(savedInstanceState)

        if (isFullScreen) {
            val window = dialog?.window
            if (window != null) {
                setFullScreen(window)
                window.decorView.setOnSystemUiVisibilityChangeListener { setFullScreen(window) }
            }
        }

        // 换成 Activity 的 inflater, 解决 fragment 样式 bug
        return activity!!.layoutInflater
    }

    /**
     * 全屏
     * @param window
     */
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