package com.shufa.easylayout.easylayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

/**
 * 顶部悬浮
 * Created by Administrator on 2018/4/20 0020.
 */

public class SupensionDecoration extends RecyclerView.ItemDecoration {

    private List<Product.Classify> classifies;
    private Paint mPaint;
    //用于存放测量文字的rect
    private Rect mBounds;

    private static int COLOR_TITLE_BG = Color.parseColor("#eeeeee");
    private static final int COLOR_TITLE_FONT = Color.parseColor("#aaaaaa");
    //title字体大小
    private static int mTitleFontSize;

    private int mHeaderViewCount = 0;
    private int mTitleHeight = 0;
    private int paddingLeft = 0;

    public SupensionDecoration(Context mContext, List<Product.Classify> classifies) {
        super();
        this.classifies = classifies;
        mPaint = new Paint();
        mBounds = new Rect();
        //dp转px
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, mContext.getResources().getDisplayMetrics());
        paddingLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mContext.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, mContext.getResources().getDisplayMetrics());

        mPaint.setTextSize(mTitleFontSize);
        mPaint.setAntiAlias(true);
    }

    public int getmHeaderViewCount() {
        return mHeaderViewCount;
    }

    public SupensionDecoration setmHeaderViewCount(int mHeaderViewCount) {
        this.mHeaderViewCount = mHeaderViewCount;
        return this;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        //这里的position不算ondraw里面添加的view
        pos -= getmHeaderViewCount();
        //pos = 1, size = 1, 1>0?true
        if (classifies == null || classifies.isEmpty() || pos > classifies.size() - 1 || pos < 0){
            return; //越界
        }

        String tag = classifies.get(pos).title;
        //出现一个奇怪的bug，有时候child为空，所以将 child = parent.getChildAt(i)。-》 parent.findViewHolderForLayoutPosition(pos).itemView
        View child = parent.findViewHolderForLayoutPosition(pos + getmHeaderViewCount()).itemView;

        boolean flag = false;//定义一个flag，Canvas是否位移过的标志
        if ((pos + 1) < classifies.size()) {//防止数组越界（一般情况不会出现）
            if (null != tag && !tag.equals(classifies.get(pos + 1).title)) {//当前第一个可见的Item的tag，不等于其后一个item的tag，说明悬浮的View要切换了
                Log.d("zxt", "onDrawOver() called with: c = [" + child.getTop());//当getTop开始变负，它的绝对值，是第一个可见的Item移出屏幕的距离，
                //这里计算第一个可见的item的剩余高度是自己的顶部的坐标+item自身的高度，剩下的就是留下来可见的部分，这里的child.getTop()是一个负值
                if (child.getHeight() + child.getTop() < mTitleHeight) {//当第一个可见的item在屏幕中还剩的高度小于title区域的高度时，我们也该开始做悬浮Title的“交换动画”
                    c.save();//每次绘制前 保存当前Canvas状态，
                    flag = true;

                    //一种头部折叠起来的视效，个人觉得也还不错~，由于child.getHeight() + child.getTop()是在不断地变小，因此这里有种渐变的裁剪矩形的感觉
                    //可与193行 c.drawRect 比较，只有bottom参数不一样，由于 child.getHeight() + child.getTop() < mTitleHeight，所以绘制区域是在不断的减小，有种折叠起来的感觉
                    // c.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + child.getHeight() + child.getTop());

                    //类似饿了么点餐时,商品列表的悬停头部切换“动画效果”
                    //上滑时，将canvas上移 （y为负数） ,所以后面canvas 画出来的Rect和Text都上移了，有种切换的“动画”感觉
                    //这里是将下面画的悬浮的部分往上移动mTitleHeight的距离,这里平移是下面画的悬浮title部分，
                    //这里是从0到-mTitleHeight的过程，直至整个悬浮的title消失
                    c.translate(0, child.getHeight() + child.getTop() - mTitleHeight);//其实这里移动的是
                }
            }
        }

        //实际上这里是绘制悬浮的title，永远在顶部显示
        mPaint.setColor(COLOR_TITLE_BG);
        //这里实际上是一个固定的位置添加一个矩形的title罢了
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        //将稳步通过画笔来算出它占据的空间
        mPaint.getTextBounds(tag, 0, tag.length(), mBounds);
        //这里也算的左下角的坐标
        c.drawText(tag, child.getPaddingLeft() + paddingLeft,
                parent.getPaddingTop() + mTitleHeight - (mTitleHeight/ 2 - mBounds.height() / 2), mPaint);
        //只有做了切换悬浮title动画时才有该操作
        if (flag)
            //恢复画布到之前的保存状态
            c.restore();
    }
}
