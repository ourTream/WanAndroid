package wanandroid.xiaosong.kotlin.myapplication

import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import wanandroid.xiaosong.kotlin.library.net.NetManager
import wanandroid.xiaosong.kotlin.myapplication.base.BaseActivity
import wanandroid.xiaosong.kotlin.myapplication.base.JumpRoute
import wanandroid.xiaosong.kotlin.myapplication.base.modelprovider.RemoteProvider
import wanandroid.xiaosong.kotlin.myapplication.main.view.adapter.MainPagerAdapter

/**
 * Created by LiXiaoSong on 18/6/22
 *功能描述：主页
 */
class MainActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        mImmersionBar.titleBar(toolbar).init()
    }

    override fun initData() {
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        toolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.search -> consume { JumpRoute.jumpToSearchPage(this@MainActivity) }
                    R.id.navgation -> consume { JumpRoute.jumpToNavigatePage(this@MainActivity) }
                    R.id.login -> consume { JumpRoute.jumpToLoginPage(this@MainActivity) }
                    R.id.share -> consume { doShare() }
                    else -> return false
                }
                return false
            }
        })
        vp_main.adapter = MainPagerAdapter(supportFragmentManager, this)
        tbl_main.setupWithViewPager(vp_main)

    }

    override fun setListener() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun doShare() {}

}