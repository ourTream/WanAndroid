package wanandroid.xiaosong.kotlin.myapplication.main.view

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_main.*
import wanandroid.xiaosong.kotlin.myapplication.R
import wanandroid.xiaosong.kotlin.myapplication.base.BaseFragment
import wanandroid.xiaosong.kotlin.myapplication.base.showSnackBar
import wanandroid.xiaosong.kotlin.myapplication.main.presenter.HomePagePresenter
import wanandroid.xiaosong.kotlin.myapplication.main.view.iview.HomePageView

/**
 * Created by LiXiaoSong on 18/6/25
 *功能描述：
 */
class HomePageFragment : BaseFragment(), HomePageView {
    var firstEnter: Boolean = true
    override fun getRecycleView(): RecyclerView {
        return rv_main
    }

    override fun getEmptyView(): Int {
        return R.layout.layout_empty_view
    }

    override fun getContext(): AppCompatActivity {
        return activity as AppCompatActivity
    }

    override fun getListItemViewId(): Int {
        return R.layout.item_main_list
    }

    private lateinit var presenter: HomePagePresenter
    override fun showToast(toast: String) {
        showSnackBar(sw_main, toast)
    }

    override fun showSuccess() {
        sw_main.isRefreshing = false
    }

    override fun showError(msg: String) {
        showSnackBar(sw_main, msg)
        sw_main.isRefreshing = false
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initViews() {
        if (firstEnter) {
            sw_main.setOnRefreshListener { presenter.loadMainListData(true) }
            rv_main.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            rv_main.addItemDecoration(MainListItemDecoration(activity!!))
            presenter.loadMainListData(true)
            firstEnter = false
        }
    }

    override fun initData() {
        presenter = HomePagePresenter(this)
    }
}