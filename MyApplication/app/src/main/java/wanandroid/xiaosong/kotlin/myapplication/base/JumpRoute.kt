package wanandroid.xiaosong.kotlin.myapplication.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import wanandroid.xiaosong.kotlin.myapplication.main.view.SearchActivity
import java.util.*

/**
 * Created by LiXiaoSong on 18/6/28
 *功能描述：页面跳转路由类，统一跳转，方便管理
 */
class JumpRoute {
    companion object {
        private lateinit var intent: Intent
        /**
         * 公共跳转类
         */
        private fun <T> jumpToInterface(context: Activity, clazz: Class<T>, bundle: Bundle?) {
            intent = Intent(context, clazz)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }

        /**
         * 公共跳转类，附带requestCode
         */
        private fun <T> jumpToInterfaceForResult(context: Activity, clazz: Class<T>, requestCode: Int, bundle: Bundle?) {
            intent = Intent(context, clazz)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            context.startActivityForResult(intent, requestCode)
        }

        //具体路由类，由开发用户填写
        //搜索页跳转
        fun jumpToSearchPage(context: Activity) {
            jumpToInterface(context, SearchActivity::class.java, null)
        }

        //登录页跳转
        fun jumpToLoginPage(context:Activity){

        }
        //网址导航页跳转
        fun jumpToNavigatePage(context:Activity){

        }

    }
}