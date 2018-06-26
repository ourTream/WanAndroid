package wanandroid.xiaosong.kotlin.library.net

/**
 * Created by LiXiaoSong on 18/6/24
 *功能描述：网络请求返回实体，需根据不同的接口规范进行修正
 */
data class HttpResult<T : Any>(var data:T,var errorCode:Int,var errorMsg:String)