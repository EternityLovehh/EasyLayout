package com.shufa.easylayout.easylayout.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shufa.easylayout.easylayout.R;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity<V extends ViewPagerLayoutManager> extends AppCompatActivity {

    private List<Integer> imgs = new ArrayList<>();

    private RecyclerView recyclerBanner;
    private V viewPagerLayoutManager;
    private CarouselLayoutManager carouselLayoutManager;
    private CenterSnapHelper centerSnapHelper;

    protected CarouselLayoutManager createLayoutManager() {
        return new CarouselLayoutManager(this, dp2px(this, 100));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        imgs.add(R.drawable.loop_first);
        imgs.add(R.drawable.loop_second);
        imgs.add(R.drawable.loop_four);
        imgs.add(R.drawable.loop_first);
        imgs.add(R.drawable.loop_second);
        imgs.add(R.drawable.loop_four);

        recyclerBanner = findViewById(R.id.recycler_banner);

        viewPagerLayoutManager = (V) createLayoutManager();
        recyclerBanner.setAdapter(new DataAdapter());
        recyclerBanner.setLayoutManager(viewPagerLayoutManager);

        centerSnapHelper = new CenterSnapHelper();
        carouselLayoutManager = (CarouselLayoutManager) getViewPagerLayoutManager();
        //设置间距
        carouselLayoutManager.setItemSpace(400);
        //设置缩放比
        carouselLayoutManager.setMinScale(0.8f);
        //自动归位居中
        centerSnapHelper.attachToRecyclerView(recyclerBanner);

        carouselLayoutManager.scrollToPosition(3);
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public V getViewPagerLayoutManager() {
        return viewPagerLayoutManager;
    }

}
