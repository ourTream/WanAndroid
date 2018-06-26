package wanandroid.xiaosong.kotlin.library.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo

/**
 * Created by LiXiaoSong on 18/6/26
 *功能描述：工具方法集合
 */
/**
 * 判断网络是否连接
 */
fun haveNetConnection(context: Context): Boolean {
    var connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        var infos: Array<NetworkInfo> = connectivityManager.allNetworkInfo
        for (info in infos) {
            if (info.state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
    }
    return false
}

/**
 * dp转px
 */
fun dpToPx(context: Context, value: Int): Int {
    var scale = context.resources.displayMetrics.density;
    return (value * scale + 0.5f) as Int
}

/**
 * px转dp
 */
fun pxToDp(context: Context, value: Int): Int {
    var scale = context.resources.displayMetrics.density;
    return return (value / scale + 0.5f) as Int
}