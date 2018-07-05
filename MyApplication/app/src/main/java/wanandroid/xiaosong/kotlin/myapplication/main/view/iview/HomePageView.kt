package wanandroid.xiaosong.kotlin.myapplication.main.view.iview

import android.support.v7.widget.RecyclerView
import wanandroid.xiaosong.kotlin.myapplication.base.baseview.IListView
import wanandroid.xiaosong.kotlin.myapplication.base.baseview.INetView
import wanandroid.xiaosong.kotlin.myapplication.base.baseview.IView

/**
 * Created by LiXiaoSong on 18/6/30
 *功能描述：
 */
interface HomePageView : IView, INetView, IListView {
    fun getListItemViewId(): Int
    fun getEmptyView(): Int
}