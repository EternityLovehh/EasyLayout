package com.shufa.easylayout.easylayout.qq;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.shufa.easylayout.easylayout.R;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//    private Context mContext;
//    private List<String> videoList;
//
//    public CardAdapter(Context mContext, List<String> videoList) {
//        this.mContext = mContext;
//        this.videoList = videoList;
//    }

    public static final int CONTENT = 1;
    public static final int VIDEO = 2;

    @Override
    public int getItemViewType(int position) {
        if (position > 10)
            return VIDEO;
        else
            return CONTENT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIDEO){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_video_item, null);
            return new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, null);
            return new ItemViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).jzVideoPlayerStandard.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4"
                    , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "嫂子闭眼睛");
            Glide.with(((ViewHolder) holder).jzVideoPlayerStandard.getContext()).load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640").into(((ViewHolder) holder).jzVideoPlayerStandard.thumbImageView);
        }
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        JZVideoPlayerStandard jzVideoPlayerStandard;
        public ViewHolder(View itemView) {
            super(itemView);
            jzVideoPlayerStandard = itemView.findViewById(R.id.video_player);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
