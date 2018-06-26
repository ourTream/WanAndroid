package wanandroid.xiaosong.kotlin.myapplication

import android.os.Bundle
import wanandroid.xiaosong.kotlin.library.net.NetManager
import wanandroid.xiaosong.kotlin.myapplication.base.BaseActivity
import wanandroid.xiaosong.kotlin.myapplication.base.modelprovider.RemoteProvider
import wanandroid.xiaosong.kotlin.myapplication.main.model.entity.TestBean

/**
 * Created by LiXiaoSong on 18/6/22
 *功能描述：
 */
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NetManager.getInstance().exec(RemoteProvider.getServiceProvider().getMainArticalList("0"))
    }
}