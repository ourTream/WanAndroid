package wanandroid.xiaosong.kotlin.myapplication.base

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import wanandroid.xiaosong.kotlin.myapplication.base.baseview.IView

/**
 * Created by LiXiaoSong on 18/6/30
 *功能描述：
 */
open class BasePresenter(view: IView) {
    protected val context: AppCompatActivity = view.getContext()
}