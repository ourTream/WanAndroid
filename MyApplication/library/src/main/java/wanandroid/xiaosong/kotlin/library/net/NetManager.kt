package wanandroid.xiaosong.kotlin.library.net

import android.content.Context
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
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
 * 使用方式：
 *         1.在Application的onCreate方法中调用：NetManager.init()
 *         2.任意地方：NetManager.getInstance().exec(Service(自己定义的).接口方法（接口参数）
 */
class NetManager {
    private var retrofit: Retrofit
    private val BASE_URL: String = "http://www.wanandroid.com/"
    private val requests: HashMap<Class<out Any>, Any> = HashMap()//request的缓存

    companion object {
        private lateinit var INSTANCE: NetManager
        private lateinit var INSTANCE_NO_CACHE: NetManager
        private lateinit var context: Context
        /**
         * 初始化方法，在application中调用，且只能调用一次
         */
        fun init(applicationContext: Context) {
            this.context = applicationContext
            this.INSTANCE = NetManager(true)
            this.INSTANCE_NO_CACHE = NetManager(false)
        }

        //获取有缓存策略的网络请求
        @JvmStatic
        fun getInstance(): NetManager {
            if (context == null) {
                Log.e("NetManager", "no context!")
                throw RuntimeException("netManager no context,please init in application")
            }
            return INSTANCE
        }

        //无缓存策略的网络请求
        @JvmStatic
        fun getInstanceNoCache(): NetManager {
            if (context == null) {
                Log.e("NetManager", "no context!")
                throw RuntimeException("netManager no context,please init in application")
            }
            return INSTANCE_NO_CACHE
        }

    }

    //缓存策略拦截器，有网时，将数据进行缓存，但每隔5秒将从新请求新的数据，无网时，直接使用缓存数据
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

    private constructor(hasCache: Boolean) {
        var builder = OkHttpClient.Builder()
        //配置缓存
        if (hasCache) {
            var file = File("${context.cacheDir}${File.separator}netcache")
            var cache = Cache(file, 20 * 1024 * 1024)
            builder.cache(cache)
                    .addInterceptor(cacheInterceptor)
                    .addNetworkInterceptor(cacheInterceptor)
        }
        //初始化Okhttp
        var client: OkHttpClient = builder
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
     * 执行网络请求方法,返回可取消的内容
     */
    public fun <T : Any> exec(flowable: Flowable<HttpResult<T>>): Disposable {
        return flowable
                .doOnSubscribe(Consumer {
                    //由于有缓存的存在，这里暂时当做备用策略
                    //                    if (!haveNetConnection(context)) {
//                        throw ApiException(ResultHelper.NET_ERROR, "网络异常")
//                    }
                })
                .compose(ResultHelper.processResult<T>())//结果处理
                .compose(SchedulesHelper.applyNormalSchedulers())//线程切换
                .subscribe(
                        { Log.v("test", it.toString()) },
                        { Log.v("test", it.toString()) })
    }

    /**
     * 仅封装了处理，需要手动调用（subscribe）
     */
    public fun <T : Any> execR(flowable: Flowable<HttpResult<T>>): Flowable<T> {
        return flowable
                .compose(ResultHelper.processResult<T>())//结果处理
                .compose(SchedulesHelper.applyNormalSchedulers())//线程切换
    }
}