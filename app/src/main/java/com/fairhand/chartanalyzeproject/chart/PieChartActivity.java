package com.fairhand.chartanalyzeproject.chart;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fairhand.chartanalyzeproject.R;
import com.fairhand.chartanalyzeproject.util.LineChartUtil;
import com.fairhand.chartanalyzeproject.util.PieChartUtil;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
public class PieChartActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private PieChart pc;
    private PieData pd;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        initView();
    }
    /**
     * 初始化视图
     */
    private void initView() {
        // 设置ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 显示返回按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("PieChart");
        }
        // 初始化UI组件
        pc = (PieChart) findViewById(R.id.piechart);
        pd = setPieData();
        PieChartUtil.drawPieChart(pc, pd);
        pc.setOnChartValueSelectedListener(this);

    }
    private PieData setPieData() {
        // 遍历饼状图
        //饼块上显示男生 显示女生
        ArrayList<PieEntry> mYArrayList = new ArrayList<PieEntry>();
        PieEntry boys =new PieEntry(30,"男生");
        PieEntry girls =new PieEntry(70,"女生");
        mYArrayList.add(boys);
        mYArrayList.add(girls);
        //y轴集合
        PieDataSet mPieDataSet = new PieDataSet(mYArrayList, "班级人数比例图");
        //设置饼状之间的间隙
        mPieDataSet.setSliceSpace(0f);
        ArrayList<Integer> mColorIntegers = new ArrayList<Integer>();
        //饼状的颜色
        mColorIntegers.add(Color.parseColor("#87CEEB"));
        mColorIntegers.add(Color.parseColor("#FFC0CB"));
        //设置颜色集
        mPieDataSet.setColors(mColorIntegers);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float px = 5 * (dm.densityDpi / 160f);
        mPieDataSet.setSelectionShift(px);
        PieData pieData = new PieData(mPieDataSet);
        return pieData;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
