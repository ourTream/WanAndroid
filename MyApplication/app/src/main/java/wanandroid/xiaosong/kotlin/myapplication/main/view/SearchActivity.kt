package wanandroid.xiaosong.kotlin.myapplication.main.view

import kotlinx.android.synthetic.main.activity_search.*
import wanandroid.xiaosong.kotlin.myapplication.R
import wanandroid.xiaosong.kotlin.myapplication.base.BaseActivity

class SearchActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_search
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        mImmersionBar.titleBar(search_toolbar).init()
    }

    override fun initView() {
        setSupportActionBar(search_toolbar)
    }
}
