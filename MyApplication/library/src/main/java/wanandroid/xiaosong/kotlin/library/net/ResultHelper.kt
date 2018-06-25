package wanandroid.xiaosong.kotlin.library.net

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.functions.Function
import org.reactivestreams.Publisher

/**
 * Created by LiXiaoSong on 18/6/25
 *功能描述：结果处理转换类
 */
class ResultHelper {
    companion object {
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
                    return ApiException(1, error.message ?: "")
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
                } else return Flowable.error(ApiException(1, "请求失败"))
            }

        }
    }
}