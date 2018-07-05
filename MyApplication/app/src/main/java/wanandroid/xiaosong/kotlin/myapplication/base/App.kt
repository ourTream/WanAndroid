package wanandroid.xiaosong.kotlin.myapplication.base

import android.app.Application
import wanandroid.xiaosong.kotlin.library.net.NetManager

/**
 * Created by LiXiaoSong on 18/6/26
 *功能描述：app基类
 */
class App : Application() {
    companion object {
        private lateinit var app: App
        fun getSelf(): App {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        NetManager.init(app)
    }
}