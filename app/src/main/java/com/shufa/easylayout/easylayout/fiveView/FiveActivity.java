package com.shufa.easylayout.easylayout.fiveView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shufa.easylayout.easylayout.R;

public class FiveActivity extends AppCompatActivity {

    private CustomView customView;
    private float []data = {170, 180, 100, 170, 150};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);

        customView = findViewById(R.id.custom_view);
        customView.setData(data);
    }
}
