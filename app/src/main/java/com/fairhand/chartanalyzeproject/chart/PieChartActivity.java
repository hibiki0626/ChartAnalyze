package com.fairhand.chartanalyzeproject.chart;
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
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.github.mikephil.charting.renderer.PieChartRenderer;

public class PieChartActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private PieChart pc;
    private PieData pd;
    private Menu menu;
    private MenuItem percent;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        initView();
    }
    //初始化视图
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
        pc = findViewById(R.id.piechart);
        pd = setPieData(PieChartUtil.boyValues[i],PieChartUtil.girlValues[i]);
        PieChartUtil.drawPieChart(pc, pd);
        //设置图表可旋转
        pc.setOnChartValueSelectedListener(this);
        Button lastyear = (Button) findViewById(R.id.lastyear);
        Button nextyear = (Button) findViewById(R.id.nextyear);
        nextyear.setOnClickListener((View.OnClickListener) v -> {
            if (i == 7)
                Toast.makeText(PieChartActivity.this, "没有以后的数据了", Toast.LENGTH_SHORT).show();
            else {
                pd=setPieData(PieChartUtil.boyValues[i++], PieChartUtil.girlValues[i++]);
                PieChartUtil.drawPieChart(pc, pd);
                pc.invalidate();
            }
        });
                lastyear.setOnClickListener(v -> {
                    if(i==0)
                        Toast.makeText(PieChartActivity.this,"没有以前的数据了",Toast.LENGTH_SHORT).show();
                    else {
                       pd=setPieData(PieChartUtil.boyValues[--i], PieChartUtil.girlValues[--i]);
                        PieChartUtil.drawPieChart(pc, pd);
                        pc.invalidate();
                    }

                });
    }

    //设置图表数据
    private PieData setPieData(int a, int b) {
        // 遍历饼状图
        //饼块上显示男生 显示女生
        ArrayList<PieEntry> mYArrayList = new ArrayList<PieEntry>();
        PieEntry boys =new PieEntry(a,"男生");
        PieEntry girls =new PieEntry(b,"女生");
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
        //设置百分比样式
        mPieDataSet.setDrawValues(false);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float px = 5 * (dm.densityDpi / 160f);
        mPieDataSet.setSelectionShift(px);
        PieData pieData = new PieData(mPieDataSet);
        return pieData;
    }
    //实现返回按钮以及菜单按钮功能
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.show_vertices_values:
                    // 获取到此Item
                    if (percent == null) {
                        percent= menu.findItem(R.id.show_vertices_values);
                    }
                    //显示百分比
                    pd.getDataSet().setDrawValues(true);
                    pd.getDataSet().setValueTextSize(20f);
                   // pd.getDataSet().setValueTextColor();
                    pc.invalidate();
                    break;
            default:
                break;
        }
        return true;
    }
    //实现菜单功能
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_pie_chart, menu);
        return true;
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

}
