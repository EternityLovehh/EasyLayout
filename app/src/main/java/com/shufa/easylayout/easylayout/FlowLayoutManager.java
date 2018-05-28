package com.shufa.easylayout.easylayout;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式布局layoutmanager
 * Created by Administrator on 2018/4/20 0020.
 */

public class FlowLayoutManager extends RecyclerView.LayoutManager {


    private FlowLayoutManager self = this;
    private int left, top, right;
    //最大容器宽度
    private int usedMaxWidth;

    protected int width, height;
    //竖直方向的偏移量
    private int verticalScrollOffset = 0;
    //计算显示内容的总高度
    private int totalHeight;

    public int getTotalHeight() {
        return totalHeight;
    }
    private Row row = new Row();
    private List<Row> lineRows = new ArrayList<>();
    //保存所有的item 的上下左右偏移量信息
    private SparseArray<Rect> allItems = new SparseArray<>();

    public FlowLayoutManager() {
        //设置主动测量规则，适应recyclerview高度为wrap_content
        setAutoMeasureEnabled(true);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 获取每一个item在屏幕上占据的位置
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0){
            detachAndScrapAttachedViews(recycler);
            verticalScrollOffset = 0;
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()){
            return;
        }
        //onLayoutChildren方法在recyclerview初始化时会执行两遍
        detachAndScrapAttachedViews(recycler);
        if (getChildCount() == 0){
            width = getWidth();
            height = getHeight();
            left = getPaddingLeft();
            right = getPaddingRight();
            top = getPaddingTop();
            usedMaxWidth = width - left - right;
        }
        totalHeight = 0;
        int cuLineTop = top;
        //当前行使用的宽度
        int cuLineWidth = 0;
        int itemLeft;
        int itemTop;
        int maxHeightItem = 0;

        row = new Row();
        lineRows.clear();
        allItems.clear();

        removeAllViews();
        for (int i = 0; i < getItemCount(); i++) {
            View childAt = recycler.getViewForPosition(i);
            if (View.GONE == childAt.getVisibility()){
                continue;
            }
            measureChildWithMargins(childAt, 0, 0);

            int childWidth = getDecoratedMeasuredWidth(childAt);
            int childHeight = getDecoratedMeasuredHeight(childAt);
            int childUseWidth = childWidth;
            int childUseHeight = childHeight;
            //如果加上当前的item还小于最大的宽度的话
            if(cuLineWidth + childUseWidth < usedMaxWidth){
                itemLeft = left + cuLineWidth;
                itemTop = cuLineTop;
                Rect frame = allItems.get(i);
                if (frame == null){
                    frame = new Rect();
                }

                frame.set(itemLeft, itemTop, itemLeft + childWidth, itemTop + childHeight);
                allItems.put(i, frame);
                cuLineWidth += childUseWidth;
                maxHeightItem = Math.max(maxHeightItem, childUseHeight);
                row.addViews(new Item(childUseHeight, childAt, frame));
                row.setCuTop(cuLineTop);
                row.setMaxHeight(maxHeightItem);
            }else {
                //换行
                formatAboveRow();
                cuLineTop += maxHeightItem;
                totalHeight += maxHeightItem;
                itemTop = cuLineTop;
                itemLeft = left;
                Rect frame = allItems.get(i);
                if (frame == null){
                    frame = new Rect();
                }
                frame.set(itemLeft, itemTop, itemLeft + childWidth, itemTop + childHeight);
                allItems.put(i, frame);
                cuLineWidth = childUseWidth;
                maxHeightItem = childUseHeight;
                row.addViews(new Item(childUseHeight, childAt, frame));
                row.setCuTop(cuLineTop);
                row.setMaxHeight(maxHeightItem);
            }
            //最后一行进行刷新下布局
            if (i == getItemCount() - 1){
                formatAboveRow();
                totalHeight += maxHeightItem;
            }
        }

        totalHeight = Math.max(totalHeight, getVerticalSpace());
        fillLayout(recycler, state);

    }

    /**
     * 对出现在屏幕上的item进行展示， 超出屏幕的item回收到缓存中
     * @param recycler
     * @param state
     */
    private void fillLayout(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //跳过preLayout,PreLayout主要用于支持动画
        if (state.isPreLayout()){
            return;
        }

        //当前scroll offset状态下的显示区域
        Rect displayFrame = new Rect(getPaddingLeft(), getPaddingTop() + verticalScrollOffset, getWidth() - getPaddingRight(),
                verticalScrollOffset + (getHeight() - getPaddingBottom()));
        //对所有的信息进行遍历
        for (int j = 0; j < lineRows.size(); j++) {
            Row row = lineRows.get(j);
            float lineTop = row.cuTop;
            float lineBottom = lineTop + row.maxHeight;
            //如果该行在屏幕中，进行放置item
            if (lineTop < displayFrame.bottom && displayFrame.top < lineBottom){
                List<Item> views = row.views;
                for (int i = 0; i < views.size(); i++) {
                    View scrap = views.get(i).view;
                    measureChildWithMargins(scrap, 0, 0);
                    addView(scrap);
                    Rect frame = views.get(i).rect;
                    //将这个item布局出来
                    layoutDecoratedWithMargins(scrap, frame.left, frame.top - verticalScrollOffset, frame.right, frame.bottom - verticalScrollOffset);;
                }
            }else {
                //将不再屏幕中的item放到缓存中
                List<Item> views = row.views;
                for (int i = 0; i < views.size(); i++) {
                    View scrap = views.get(i).view;
                    removeAndRecycleView(scrap, recycler);
                }
            }
        }
    }

    /**
     * 计算每一行没有居中的viewgroup, 让居中显示
     */
    private void formatAboveRow() {

        List<Item> views = row.views;

        for (int i = 0; i < views.size(); i++) {
            Item item = views.get(i);
            View view = item.view;
            int position = getPosition(view);
            //如果该item的位置不在该行中间位置的话，进行重新放置
            if (allItems.get(position).top < row.cuTop + (row.maxHeight - views.get(i).useHeight)/ 2){
                Rect frame = allItems.get(position);
                if (frame == null){
                    frame = new Rect();
                }

                frame.set(allItems.get(position).left, (int) (row.cuTop + (row.maxHeight - views.get(i).useHeight) / 2),
                        allItems.get(position).right, (int) (row.cuTop + (row.maxHeight - views.get(i).useHeight)/ 2) + getDecoratedMeasuredHeight(view));
                allItems.put(position, frame);
                item.setRect(frame);
                views.set(i, item);
            }
        }

        row.views = views;
        lineRows.add(row);
        row = new Row();

    }

    /**
     * 竖直方向需要滑动的条件
     * @return
     */
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    /**
     * 竖直方向滑动的偏移量
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //实际要滑动的距离
        int travel = dy;
        //如果滑动到最顶部
        if(verticalScrollOffset + dy < 0){
            //限制滑动到顶部之后，不让继续向上滑动了
            travel = -verticalScrollOffset; //verticalScrollOffset=0
        }else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()){
            //如果滑动到最底部
            travel = totalHeight - getVerticalSpace() - verticalScrollOffset; //verticalScrollOffset=totalHeight - getVerticalSpace()
        }

        //将竖直方向的偏移量+ travel
        verticalScrollOffset += travel;
        //平移容器内的item
        offsetChildrenVertical(-travel);
        fillLayout(recycler, state);
        return travel;
    }

    private int getVerticalSpace() {
        return self.getHeight() - self.getPaddingBottom() - self.getPaddingTop();
    }

    //行信息定义
    public class Row{
        //每一行的头部坐标
        float cuTop;
        //每一行需要占据的最大高度
        float maxHeight;
        //每一行存储的Item
        List<Item> views = new ArrayList<>();

        public void setCuTop(float cuTop) {
            this.cuTop = cuTop;
        }

        public void setMaxHeight(float maxHeight) {
            this.maxHeight = maxHeight;
        }

        public void addViews(Item view){
            views.add(view);
        }
    }

    //每个item定义
    public class Item{
        int useHeight;
        View view;
        Rect rect;

        public void setRect(Rect rect) {
            this.rect = rect;
        }

        public Item(int useHeight, View view, Rect rect) {
            this.useHeight = useHeight;
            this.view = view;
            this.rect = rect;
        }
    }
}
