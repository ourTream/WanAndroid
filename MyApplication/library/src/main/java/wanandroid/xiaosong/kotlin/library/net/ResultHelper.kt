package wanandroid.xiaosong.kotlin.library.net

import com.google.gson.JsonParseException
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.functions.Function
import org.json.JSONException
import org.reactivestreams.Publisher
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * Created by LiXiaoSong on 18/6/25
 *功能描述：结果处理转换类
 */
class ResultHelper {

    companion object {
        val JSON_ERROR: Int = -101;
        val HTTP_ERROR: Int = -102;
        val NET_ERROR: Int = -103;
        val OTHER_ERROR: Int = -104;
        fun <T : Any> processResult(): FlowableTransformer<HttpResult<T>, T> {
            return object : FlowableTransformer<HttpResult<T>, T> {
                override fun apply(upstream: Flowable<HttpResult<T>>): Publisher<T> {
                    return upstream.onErrorResumeNext(ErrorResumeException()).flatMap(ResultHandler())
                }
            }
        }

        class ExceptionProcess {
            companion object {
                fun processException(error: Throwable): ApiException {
                    var code: Int
                    if (error is JsonParseException || error is JSONException || error is ParseException) {
                        code = JSON_ERROR
                    } else if (error is ConnectException) {
                        code = NET_ERROR
                    } else if (error is UnknownHostException || error is SocketException) {
                        code = HTTP_ERROR
                    } else code = OTHER_ERROR
                    return ApiException(code, error.message ?: "")
                }
            }
        }

        class ErrorResumeException<T : Any> : Function<Throwable, Flowable<out HttpResult<T>>> {
            override fun apply(t: Throwable): Flowable<out HttpResult<T>> {
                return Flowable.error(ExceptionProcess.processException(t))
            }
        }

        class ResultHandler<T : Any> : Function<HttpResult<T>, Flowable<T>> {
            override fun apply(t: HttpResult<T>): Flowable<T> {
                if (t.errorCode == 0) {
                    return Flowable.just(t.data)
                } else return Flowable.error(ApiException(HTTP_ERROR, "请求失败"))
            }

        }
    }
}