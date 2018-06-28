package wanandroid.xiaosong.kotlin.myapplication.main.presenter

import io.reactivex.functions.Consumer
import wanandroid.xiaosong.kotlin.library.net.NetManager
import wanandroid.xiaosong.kotlin.myapplication.base.modelprovider.RemoteProvider
import wanandroid.xiaosong.kotlin.myapplication.main.view.adapter.MainPagerAdapter

/**
 * Created by LiXiaoSong on 18/6/25
 *功能描述：
 */
class HomePagePresenter {
    private var adapter: MainPagerAdapter? = null
    public fun loadMainListData() {
        NetManager
                .getInstance()
                .execR(RemoteProvider.getServiceProvider().getMainArticalList("1"))
                .subscribe(
                        Consumer {
                            if(adapter == null) {

                            }
                        }
                )
    }
}