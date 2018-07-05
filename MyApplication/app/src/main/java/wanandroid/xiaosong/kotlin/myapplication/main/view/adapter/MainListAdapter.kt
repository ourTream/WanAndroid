package wanandroid.xiaosong.kotlin.myapplication.main.view.adapter

import android.view.ViewGroup
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanandroid.xiaosong.kotlin.myapplication.R
import wanandroid.xiaosong.kotlin.myapplication.base.getPicToImage
import wanandroid.xiaosong.kotlin.myapplication.main.model.entity.Data

/**
 * Created by LiXiaoSong on 18/6/28
 *功能描述：主页列表adapter
 */
class MainListAdapter(layoutRes: Int, source: List<Data>) : BaseQuickAdapter<Data, BaseViewHolder>(layoutRes, source) {
    override fun convert(helper: BaseViewHolder, item: Data) {
        helper.setText(R.id.tv_main_list, item.author)
        helper.setText(R.id.tv_main_list_content, item.title)
        helper.setText(R.id.tv_main_list_tag, item.superChapterName + "/" + item.chapterName)
        helper.setText(R.id.tv_main_list_time, item.niceDate)
        getPicToImage(mContext, item.envelopePic, helper.getView<ImageView>(R.id.iv_main_list))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return super.onCreateViewHolder(parent, viewType)
    }
}