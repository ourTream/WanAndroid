package wanandroid.xiaosong.kotlin.myapplication.base

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import wanandroid.xiaosong.kotlin.myapplication.R

/**
 * Created by LiXiaoSong on 18/6/30
 *功能描述：UI工具类
 * 注：snakebar工具使用的时候要注意最外层布局是否是约束布局
 */

/**
 * 短暂显示的snackbar
 */
fun showSnackBar(v: View, content: String) {
    Snackbar.make(v, content, Snackbar.LENGTH_SHORT)
}

/**
 * 带点击事件的snackbar
 */
fun showClickSnackBar(v: View, content: String, listener: View.OnClickListener) {
    Snackbar.make(v, content, Snackbar.LENGTH_INDEFINITE).setAction("确定", listener)
}

/**
 *图片下载
 */
fun getPicToImage(context: Context, url: String, target: ImageView, defPic: Int = R.mipmap.people, errorPic: Int = R.mipmap.people) {
    Glide.with(context).load(url).asBitmap().placeholder(defPic).error(defPic).into(target)
}