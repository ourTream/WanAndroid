package wanandroid.xiaosong.kotlin.myapplication.base.modelprovider

import wanandroid.xiaosong.kotlin.library.net.NetManager

/**
 * Created by LiXiaoSong on 18/6/25
 *功能描述：网络接口提供者
 */
class RemoteProvider {
    companion object {
        @JvmStatic
        public fun getServiceProvider(): WanAndroidNetRequest {
            return NetManager.getInstance().getService(WanAndroidNetRequest::class.java)
        }
    }

}