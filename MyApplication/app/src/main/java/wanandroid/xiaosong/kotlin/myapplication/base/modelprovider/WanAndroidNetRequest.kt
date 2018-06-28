package wanandroid.xiaosong.kotlin.myapplication.base.modelprovider

import io.reactivex.Flowable
import retrofit2.http.*
import wanandroid.xiaosong.kotlin.library.net.HttpResult
import wanandroid.xiaosong.kotlin.myapplication.main.model.entity.TestBean

/**
 * Created by LiXiaoSong on 18/6/24
 *功能描述：本项目的所有接口声明
 */
interface WanAndroidNetRequest {
    @GET("article/list/{pageNo}/json")
    fun getMainArticalList(@Path("pageNo") pageNo: String): Flowable<HttpResult<TestBean>>
}