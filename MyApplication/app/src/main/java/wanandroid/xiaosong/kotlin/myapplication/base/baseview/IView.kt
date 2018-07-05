package wanandroid.xiaosong.kotlin.myapplication.base.baseview

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity

/**
 * Created by LiXiaoSong on 18/6/28
 *功能描述：
 */
interface IView {
    fun showToast(toast: String)
    fun getContext(): AppCompatActivity
}