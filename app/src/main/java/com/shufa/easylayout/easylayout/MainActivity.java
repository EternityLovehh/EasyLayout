package com.shufa.easylayout.easylayout;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.shufa.easylayout.easylayout.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private List<Product.Classify> products = new ArrayList<>();
    private List<Product.Classify.Des> detailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tb_bar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_product);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            detailList.add(new Product.Classify.Des(1, "红色"));
            detailList.add(new Product.Classify.Des(2, "白色"));

            products.add(new Product.Classify(1, "颜色", null));
            products.add(new Product.Classify(2, "性别", detailList));
            products.add(new Product.Classify(2, "性别", detailList));
            products.add(new Product.Classify(2, "性别", detailList));
        }

        recyclerView.setAdapter(new ProductAdapter(this, products));
        recyclerView.addItemDecoration(new SupensionDecoration(this, products));
    }
}