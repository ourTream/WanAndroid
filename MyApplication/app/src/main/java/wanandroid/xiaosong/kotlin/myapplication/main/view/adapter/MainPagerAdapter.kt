package wanandroid.xiaosong.kotlin.myapplication.main.view.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import wanandroid.xiaosong.kotlin.myapplication.R
import wanandroid.xiaosong.kotlin.myapplication.main.view.HomePageFragment
import wanandroid.xiaosong.kotlin.myapplication.mine.MineFragment
import wanandroid.xiaosong.kotlin.myapplication.project.ProjectFragment
import wanandroid.xiaosong.kotlin.myapplication.system.SystemFragment

/**
 * Created by LiXiaoSong on 18/6/28
 *功能描述：
 */
class MainPagerAdapter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {
    val titleArray: Array<String> = context.resources.getStringArray(R.array.main_title_array)
    val fragments: Array<Fragment> = arrayOf(HomePageFragment(), SystemFragment(), ProjectFragment(), MineFragment())
    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return titleArray.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleArray[position]
    }
}