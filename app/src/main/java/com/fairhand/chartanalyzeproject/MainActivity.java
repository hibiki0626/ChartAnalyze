package com.fairhand.chartanalyzeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * Created by FairHand on 2018/9/17.<br />
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    private Button toLineChart;
    
    private Button toBarChart;
    
    private Button toPieChart;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
        
        initData();
        
    }
    
    /**
     * 初始化视图
     */
    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toLineChart = findViewById(R.id.bt_line_chart);
        toBarChart = findViewById(R.id.bt_bar_chart);
        toPieChart = findViewById(R.id.bt_pie_chart);
    }
    
    /**
     * 初始化数据
     */
    private void initData() {
        toLineChart.setOnClickListener(this);
        toBarChart.setOnClickListener(this);
        toPieChart.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_line_chart:
                // 打开折线图
                Intent toLineIntent = new Intent(MainActivity.this, LineChartActivity.class);
                startActivity(toLineIntent);
                break;
            case R.id.bt_bar_chart:
                break;
            case R.id.bt_pie_chart:
                break;
            default:
                break;
        }
    }
    
}
