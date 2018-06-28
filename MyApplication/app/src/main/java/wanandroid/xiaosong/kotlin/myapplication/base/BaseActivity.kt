package wanandroid.xiaosong.kotlin.myapplication.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.gyf.barlibrary.ImmersionBar

/**
 * Created by LiXiaoSong on 18/6/25
 *功能描述：activity基类
 */
open abstract class BaseActivity : AppCompatActivity() {
    private lateinit var imm: InputMethodManager
    protected lateinit var mImmersionBar: ImmersionBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        if (isImmersionBarEnabled())
            initImmersionBar()
        initData()
        initView()
        setListener()
    }

    //获取上个界面数据
    open protected fun initData() {}

    //数据UI绑定
    open protected fun initView() {}

    //初始化监听
    open protected fun setListener() {}

    /**
     * 本界面的UI布局
     */
    protected abstract fun getLayout(): Int

    open protected fun initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar.init()
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean
     * @return the boolean
     */
    protected fun isImmersionBarEnabled(): Boolean {
        return true
    }

    inline fun consume(f: () -> Unit): Boolean {
        f()
        return true
    }
}