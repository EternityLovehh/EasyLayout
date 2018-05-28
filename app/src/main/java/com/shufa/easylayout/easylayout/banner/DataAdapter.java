package com.shufa.easylayout.easylayout.banner;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.shufa.easylayout.easylayout.R;

/**
 * Created by Dajavu on 25/10/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private int[] images = {R.drawable.loop_first, R.drawable.loop_second, R.drawable.loop_four,
            R.drawable.loop_first, R.drawable.loop_second, R.drawable.loop_four,
            R.drawable.loop_first, R.drawable.loop_second, R.drawable.loop_four};

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.imageView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_banner);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "clicked:" + v.getTag(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
