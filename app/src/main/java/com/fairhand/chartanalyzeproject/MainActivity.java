package com.fairhand.chartanalyzeproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;

import com.fairhand.chartanalyzeproject.adapter.ChartAdapter;
import com.fairhand.chartanalyzeproject.entity.Chart;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by FairHand on 2018/9/17.<br />
 */
public class MainActivity extends AppCompatActivity {
    
    private RecyclerView mRecyclerView;
    
    private Chart[] charts = {
            new Chart(R.drawable.ic_line_chart_icon,
                    "Line Chart",
                    "一个简单的展示班级男女人数折线图"),
            new Chart(R.drawable.ic_bar_chart_icon,
                    "Bar Chart",
                    "一个简单的展示班级男女人数柱状图"),
            new Chart(R.drawable.ic_pie_chart_icon,
                    "Pie Chart",
                    "一个简单的展示班级男女人数饼状图")};
    
    private ArrayList<Chart> chartArrayList = new ArrayList<>();
    
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
        mRecyclerView = findViewById(R.id.recycler_view);
        
        // 设置退出过渡动画(向左滑动)
        Slide slide = new Slide();
        slide.setDuration(520);
        slide.setSlideEdge(Gravity.START);
        getWindow().setExitTransition(slide);
    }
    
    /**
     * 初始化数据
     */
    private void initData() {
        chartArrayList.addAll(Arrays.asList(charts));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChartAdapter mChartAdapter = new ChartAdapter(this, chartArrayList);
        mRecyclerView.setAdapter(mChartAdapter);
    }
    
}
