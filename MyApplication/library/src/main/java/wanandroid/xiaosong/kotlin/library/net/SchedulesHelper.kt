package wanandroid.xiaosong.kotlin.library.net

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher

/**
 * Created by LiXiaoSong on 18/6/24
 *功能描述：线程帮助类
 */
class SchedulesHelper {
    companion object {
        fun getIoSchedules(): Scheduler {
            return Schedulers.io()
        }

        fun getComptation(): Scheduler {
            return Schedulers.computation()
        }

        fun getMainThread(): Scheduler {
            return AndroidSchedulers.mainThread()
        }

        fun <T> applyNormalSchedulers(): FlowableTransformer<T, T> {
            return object : FlowableTransformer<T, T> {
                override fun apply(upstream: Flowable<T>): Publisher<T> {
                    return upstream
                            .subscribeOn(getIoSchedules())
                            .observeOn(getMainThread())
                }

            }
        }
    }
}