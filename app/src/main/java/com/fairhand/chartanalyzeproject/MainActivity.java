package com.fairhand.chartanalyzeproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.fairhand.chartanalyzeproject.adapter.ChartAdapter;
import com.fairhand.chartanalyzeproject.entry.LineChart;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by FairHand on 2018/9/17.<br />
 */
public class MainActivity extends AppCompatActivity {
    
    private RecyclerView mRecyclerView;
    
    private LineChart[] lineCharts = {
            new LineChart(R.drawable.ic_line_chart_icon,
                    "Line Chart",
                    "一个简单的展示班级男女人数折线图"),
            new LineChart(R.drawable.ic_line_chart_icon,
                    "Bar Chart",
                    "一个简单的展示班级男女人数柱状图"),
            new LineChart(R.drawable.ic_line_chart_icon,
                    "Pie Chart",
                    "一个简单的展示班级男女人数饼状图")};
    
    private ArrayList<LineChart> lineChartArrayList = new ArrayList<>();
    
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
    }
    
    /**
     * 初始化数据
     */
    private void initData() {
        lineChartArrayList.addAll(Arrays.asList(lineCharts));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChartAdapter mChartAdapter = new ChartAdapter(this, lineChartArrayList);
        mRecyclerView.setAdapter(mChartAdapter);
    }
    
}
