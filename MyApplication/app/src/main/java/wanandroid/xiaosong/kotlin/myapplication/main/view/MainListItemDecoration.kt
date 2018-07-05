package wanandroid.xiaosong.kotlin.myapplication.main.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import wanandroid.xiaosong.kotlin.library.util.dpToPx
import wanandroid.xiaosong.kotlin.myapplication.R

/**
 * Created by LiXiaoSong on 18/7/5
 *功能描述：
 */
class MainListItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    val lineWidth: Int = dpToPx(context, 1)
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        var v: View?
        var params: RecyclerView.LayoutParams
        var top: Float
        var bottom: Float
        var left: Float = parent.paddingLeft.toFloat()
        var right: Float = (parent.width - parent.paddingRight).toFloat()
        var paint = Paint()
        paint.color = parent.context.resources.getColor(R.color.normalGray)
        paint.style = Paint.Style.FILL
        for (i in 0..parent.childCount) {
            v = parent.getChildAt(i)
            if (v != null) {
                params = v.layoutParams as RecyclerView.LayoutParams
                top = v.bottom + params.bottomMargin + v.translationY
                bottom = top + lineWidth
                c.drawRect(left, top, right, bottom, paint)
            }
        }
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {

    }

    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect.bottom = lineWidth
    }
}