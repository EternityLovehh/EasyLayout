package com.shufa.easylayout.easylayout.qq;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.shufa.easylayout.easylayout.R;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;

public class DrawerActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerTool;
    private RecyclerView recyclerCard;
    private List<ToolBean> tools = new ArrayList<>();
    private DrawerAdapter adapter;
    private CardAdapter cardAdapter;
    private BottomSheetBehavior sheetBehavior;

    private ImageView ivUp, ivDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        ivUp = findViewById(R.id.iv_up);
        ivDown = findViewById(R.id.iv_down);

        ivUp.setOnClickListener(this);
        ivDown.setOnClickListener(this);

        recyclerTool = findViewById(R.id.recycler_tool);
        recyclerCard = findViewById(R.id.recycler_card);

        initData();
        initCard();
    }

    private void initCard() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerCard.setLayoutManager(layoutManager);
        cardAdapter = new CardAdapter();

        recyclerCard.setAdapter(cardAdapter);

        recyclerCard.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                JZVideoPlayer.onChildViewAttachedToWindow(view, R.id.video_player);
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
//                JZVideoPlayer jzvd = view.findViewById(R.id.video_player);
//                if (jzvd != null && JZUtils.dataSourceObjectsContainsUri(jzvd.dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
//                    JZVideoPlayer currentJzvd = JZVideoPlayerManager.getCurrentJzvd();
//                    if (currentJzvd != null && currentJzvd.currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN) {
//                        JZVideoPlayer.releaseAllVideos();
//                    }
//                }

                JZVideoPlayer.onChildViewDetachedFromWindow(view);
            }
        });
    }

    private void initData() {

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerTool.setLayoutManager(layoutManager);

        ToolBean toolBean = new ToolBean();
        for (int i = 0; i < 9; i++) {
            toolBean.setImgId(R.drawable.ic_launcher);
            toolBean.setName(getResources().getStringArray(R.array.voicer_cloud_entries)[i]);
            tools.add(toolBean);
        }

        adapter = new DrawerAdapter(this, tools);
        recyclerTool.setAdapter(adapter);


        sheetBehavior = BottomSheetBehavior.from(findViewById(R.id.ll_bottom));

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // Check Logs to see how bottom sheets behaves
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_up:
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.iv_down:
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
