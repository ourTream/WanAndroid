package wanandroid.xiaosong.kotlin.library.net

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.functions.Consumer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by LiXiaoSong on 18/6/24
 *功能描述：网络管理类，在application中作为单例使用
 */
class NetManager {
    private var retrofit: Retrofit
    private val BASE_URL: String = "http://www.wanandroid.com/"

    companion object {
        private val INSTANCE: NetManager = NetManager();
        @JvmStatic
        fun getInstance(): NetManager {
            return INSTANCE
        }
    }

    private constructor() {
        var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build()
        retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    public fun <T : NetRequest> getService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }

    /**
     * 执行网络请求方法
     */
    public fun <T : Any> exec(flowable: Flowable<HttpResult<T>>) {
        flowable
                .compose(ResultHelper.processResult<T>())//结果处理
                .compose(SchedulesHelper.applyNormalSchedulers())//线程切换
                .subscribe(
                        Consumer { T -> Log.v("test", T.toString()) },
                        Consumer { e -> Log.v("test", e.toString()) })
    }
}