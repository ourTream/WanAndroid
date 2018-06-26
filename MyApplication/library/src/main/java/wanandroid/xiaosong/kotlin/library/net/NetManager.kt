package wanandroid.xiaosong.kotlin.library.net

import android.content.Context
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.functions.Consumer
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import wanandroid.xiaosong.kotlin.library.util.haveNetConnection
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by LiXiaoSong on 18/6/24
 *功能描述：网络管理类，在application中作为单例使用
 */
class NetManager {
    private var retrofit: Retrofit
    private val BASE_URL: String = "http://www.wanandroid.com/"
    private val requests: HashMap<Class<out Any>, Any> = HashMap()//request的缓存

    companion object {
        private lateinit var INSTANCE: NetManager
        private lateinit var context: Context
        /**
         * 初始化方法，在application中调用，且只能调用一次
         */
        fun init(applicationContext: Context) {
            this.context = applicationContext
            this.INSTANCE = NetManager()
        }

        @JvmStatic
        fun getInstance(): NetManager {
            if (context == null) {
                Log.e("NetManager", "no context!")
                throw RuntimeException("netManager no context,please init in application")
            }
            return INSTANCE
        }
    }

    private val cacheInterceptor: Interceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            var request = chain!!.request()
            request = request
                    .newBuilder()
                    .addHeader("Cache-Control", "public, max-age=5,max-stale=5")
                    .build()
            if (!haveNetConnection(context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            var response = chain.proceed(request)
            if (haveNetConnection(context)) {
                var cacheControl = request.cacheControl().toString()
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build()
            } else {
                return response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build()
            }
        }
    }

    private constructor() {
        //配置缓存
        var file = File("${context.cacheDir}${File.separator}netcache")
        var cache = Cache(file, 20 * 1024 * 1024)
        //初始化Okhttp
        var client: OkHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }

    public fun <T : Any> getService(clazz: Class<T>): T {
        if (requests.get(clazz) == null) {
            requests.put(clazz, retrofit.create(clazz))
        }
        return (requests.get(clazz) as T?) ?: retrofit.create(clazz)
    }

    /**
     * 执行网络请求方法
     */
    public fun <T : Any> exec(flowable: Flowable<HttpResult<T>>) {
        flowable
                .doOnSubscribe(Consumer {
//                    if (!haveNetConnection(context)) {
//                        throw ApiException(ResultHelper.NET_ERROR, "网络异常")
//                    }
                })
                .compose(ResultHelper.processResult<T>())//结果处理
                .compose(SchedulesHelper.applyNormalSchedulers())//线程切换
                .subscribe(
                        Consumer { T -> Log.v("test", T.toString()) },
                        Consumer { e -> Log.v("test", e.toString()) })
    }
}