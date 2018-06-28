package wanandroid.xiaosong.kotlin.myapplication.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by LiXiaoSong on 18/6/27
 *功能描述：
 */
open abstract class BaseFragment : Fragment() {
    private var root: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (root == null)
            root = inflater.inflate(getLayoutId(), container,false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initViews()
        setListener()
    }

    abstract fun getLayoutId(): Int
    open fun initData() {

    }

    open fun setListener() {

    }

    open fun initViews() {

    }
}