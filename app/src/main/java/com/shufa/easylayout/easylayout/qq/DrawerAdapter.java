package com.shufa.easylayout.easylayout.qq;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shufa.easylayout.easylayout.R;

import java.util.List;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private List<ToolBean> toolBeans;
    private Context mContext;

    public DrawerAdapter(Context mContext, List<ToolBean> toolBeans) {
        this.toolBeans = toolBeans;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tool_item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToolBean toolBean = toolBeans.get(position);
        holder.imageView.setImageResource(toolBean.getImgId());
        holder.textView.setText(toolBean.getName());
    }

    @Override
    public int getItemCount() {
        return toolBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iv_img);
            textView = itemView.findViewById(R.id.tv_name);

        }
    }
}
