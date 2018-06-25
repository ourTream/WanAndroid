package wanandroid.xiaosong.kotlin.myapplication.base.modelprovider

/**
 * Created by LiXiaoSong on 18/6/25
 *功能描述：网络接口提供者
 */
class RemoteProvider {
    companion object {
        @JvmStatic
        public fun getServiceProvider(): Class<WanAndroidNetRequest> {
            return WanAndroidNetRequest::class.java
        }
    }

}