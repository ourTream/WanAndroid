package wanandroid.xiaosong.kotlin.myapplication.main.view.adapter

import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanandroid.xiaosong.kotlin.myapplication.main.model.entity.Data

/**
 * Created by LiXiaoSong on 18/6/28
 *功能描述：
 */
class MainListAdapter(layoutRes: Int, source: List<Data>) : BaseQuickAdapter<Data, BaseViewHolder>(layoutRes, source) {
    override fun convert(helper: BaseViewHolder, item: Data) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return super.onCreateViewHolder(parent, viewType)
    }
}