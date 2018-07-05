package wanandroid.xiaosong.kotlin.myapplication.base.baseview

import android.support.v7.widget.RecyclerView
import android.widget.Adapter
import wanandroid.xiaosong.kotlin.myapplication.main.view.adapter.MainListAdapter

/**
 * Created by LiXiaoSong on 18/6/30
 *功能描述：
 */
interface IListView {
    fun getRecycleView(): RecyclerView
}