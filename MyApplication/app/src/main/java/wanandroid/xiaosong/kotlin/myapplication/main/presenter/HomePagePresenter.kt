package wanandroid.xiaosong.kotlin.myapplication.main.presenter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.raizlabs.android.dbflow.kotlinextensions.update
import io.reactivex.functions.Consumer
import wanandroid.xiaosong.kotlin.library.net.NetManager
import wanandroid.xiaosong.kotlin.myapplication.R
import wanandroid.xiaosong.kotlin.myapplication.base.BasePresenter
import wanandroid.xiaosong.kotlin.myapplication.base.baseview.IView
import wanandroid.xiaosong.kotlin.myapplication.base.modelprovider.RemoteProvider
import wanandroid.xiaosong.kotlin.myapplication.main.view.adapter.MainListAdapter
import wanandroid.xiaosong.kotlin.myapplication.main.view.iview.HomePageView

/**
 * Created by LiXiaoSong on 18/6/25
 *功能描述：
 */
class HomePagePresenter(view: HomePageView) : BasePresenter(view) {
    private var adapter: MainListAdapter? = null
    private val v = view
    private var currentPage: Int = 0
    public fun loadMainListData(isRefresh: Boolean) {
        if (isRefresh) {
            currentPage = 0
        } else {
            currentPage++
        }
        NetManager
                .getInstance()
                .execR(RemoteProvider.getServiceProvider().getMainArticalList(currentPage))
                .filter({
                    //初始化adapter
                    if (adapter == null) {
                        adapter = MainListAdapter(v.getListItemViewId(), it.datas)
                        adapter?.bindToRecyclerView(v.getRecycleView())
                        adapter?.setPreLoadNumber(2)
                        adapter?.disableLoadMoreIfNotFullPage()
                        adapter?.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
                            override fun onLoadMoreRequested() {
                                loadMainListData(false)
                            }
                        }, v.getRecycleView())
                        adapter?.setEmptyView(v.getEmptyView(), v.getRecycleView())
                        false
                    } else
                        true
                })
                .map {
                    //数据处理
                    if (currentPage == 0) {//上拉刷新
                        adapter?.setNewData(it.datas)
                    }
                    if (currentPage > 0) {//下拉加载更多
                        adapter?.addData(it.datas)
                        adapter?.loadMoreComplete()
                        if (it.datas.size == 0) {
                            adapter?.loadMoreEnd()
                        }
                    }
                }
                .subscribe(//结果处理
                        { v.showSuccess() },
                        {
                            v.showError(it.message ?: "")
                            if (currentPage > 0) {
                                adapter?.loadMoreFail()
                            }
                        }
                )
    }

    public fun loadMoreListData() {

    }
}