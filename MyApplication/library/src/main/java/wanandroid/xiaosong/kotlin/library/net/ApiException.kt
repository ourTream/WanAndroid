package wanandroid.xiaosong.kotlin.library.net

/**
 * Created by LiXiaoSong on 18/6/24
 *功能描述：请求错误异常
 */
data class ApiException(var code: Int, override var message: String) : Exception()