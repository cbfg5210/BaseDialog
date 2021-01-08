package cbfg.dialog

import android.os.Build
import android.os.Bundle
import android.view.*
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
            setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        }
        super.onGetLayoutInflater(savedInstanceState)
        configWindow(dialog?.window)
        // 换成 Activity 的 inflater, 解决 fragment 样式 bug
        return activity!!.layoutInflater
    }

    private fun configWindow(window: Window?) {
        window ?: return
        if (isFullScreen) {
            hideBottomUIMenu(window)
        }
    }

    /**
     * 隐藏虚拟按键，并且全屏
     *
     * @param window
     */
    private fun hideBottomUIMenu(window: Window) {
        if (Build.VERSION.SDK_INT < 19) {
            //for low api versions
            window.decorView.systemUiVisibility = View.GONE
            return
        }

        //for new api versions.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                // hide nav bar
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                // hide status bar
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }
}