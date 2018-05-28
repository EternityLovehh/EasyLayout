package com.shufa.easylayout.easylayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Product.Classify> productList;
    public static final int HEAD = 1;
    public static final int ITEM = 2;

    public ProductAdapter(Context mContext, List<Product.Classify> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @Override
    public int getItemViewType(int position) {
        if (productList.get(position).type == HEAD)
            return HEAD;
        else
            return ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD){
            View view = LayoutInflater.from(mContext).inflate(R.layout.flow_item_head, parent, false);
            HeadViewHolder viewHolder = new HeadViewHolder(view);
            return viewHolder;
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.flow_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder)
            ((HeadViewHolder) holder).tvHead.setText(productList.get(position).title);
        else
        if (productList.get(position).getDes() != null) {
            Product.Classify.Des des = productList.get(position).getDes().get(position);
            ((ViewHolder) holder).tvFlow.setText(des.content);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{
        TextView tvHead;
        public HeadViewHolder(View itemView) {
            super(itemView);
            tvHead = (TextView) itemView.findViewById(R.id.tv_head);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvFlow;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFlow = (TextView) itemView.findViewById(R.id.tv_flow);
        }
    }
}
