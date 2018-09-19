package com.fairhand.chartanalyzeproject.chart;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.fairhand.chartanalyzeproject.util.LineChartUtil;
import com.fairhand.chartanalyzeproject.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.List;

/**
 * Created by FairHand on 2018/9/17.<br />
 * 折线图
 */
public class LineChartActivity extends AppCompatActivity {
    
    private LineChart mLineChart;
    
    private Menu menu;
    
    /**
     * 折线图名
     */
    private TextView mTextView;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        
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
            actionBar.setTitle("Line LineChart");
        }
        
        mTextView = findViewById(R.id.tv_chart_name);
        mLineChart = findViewById(R.id.line_chart);
        
        LineChartUtil.drawLineChart(this, mLineChart);
        
        setLineChartData();
        
    }
    
    /**
     * 设置图表数据
     */
    private void setLineChartData() {
        
        LineDataSet boyDataSet;// 男孩子折线
        LineDataSet girlDataSet;// 女孩子折线
        
        if ((mLineChart.getData() != null) && (mLineChart.getData().getDataSetCount() > 0)) {
            // 获取数据集
            boyDataSet = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
            girlDataSet = (LineDataSet) mLineChart.getData().getDataSetByIndex(1);
            // 设置值
            boyDataSet.setValues(LineChartUtil.getYValues());
            girlDataSet.setValues(LineChartUtil.getYValues());
            // 通过数据更新
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            boyDataSet = LineChartUtil.setLineChartData(this, "男孩子",
                    R.color.blueBoy, LineChartUtil.getYValues());
            girlDataSet = LineChartUtil.setLineChartData(this, "女孩子",
                    R.color.pinkGirl, LineChartUtil.getYValues());
            
            LineData lineData = new LineData(boyDataSet, girlDataSet);
            // 不显示数据
            lineData.setDrawValues(false);
            // 设置数据
            mLineChart.setData(lineData);
        }
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // 加载菜单布局
        getMenuInflater().inflate(R.menu.menu_line_chart, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_vertices_values:// 显示顶点值
                // 获取到此Item
                MenuItem menuItem = menu.findItem(R.id.show_vertices_values);
                // 获取到数据集
                List<ILineDataSet> valuesSets = mLineChart.getData().getDataSets();
                LineDataSet valuesSet = null;
                // 遍历显示或隐藏值
                for (ILineDataSet iLineDataSet : valuesSets) {
                    valuesSet = (LineDataSet) iLineDataSet;
                    valuesSet.setDrawValues(!valuesSet.isDrawValuesEnabled());
                }
                // 重绘
                mLineChart.invalidate();
                
                if (valuesSet != null) {
                    if (valuesSet.isDrawValuesEnabled()) {
                        menuItem.setTitle("隐藏顶点值");
                    } else {
                        menuItem.setTitle("显示顶点值");
                    }
                }
                break;
            case R.id.if_show_circle:// 是否显示圆点
                MenuItem menuItem1 = menu.findItem(R.id.if_show_circle);
                List<ILineDataSet> circleSets = mLineChart.getData().getDataSets();
                LineDataSet circleSet = null;
                for (ILineDataSet dataSet : circleSets) {
                    circleSet = (LineDataSet) dataSet;
                    circleSet.setDrawCircles(!circleSet.isDrawCirclesEnabled());
                }
                mLineChart.invalidate();
                
                if (circleSet != null) {
                    if (circleSet.isDrawCirclesEnabled()) {
                        menuItem1.setTitle("隐藏圆点");
                    } else {
                        menuItem1.setTitle("显示圆点");
                    }
                }
                break;
            case R.id.x_axis_animation:// X轴动画
                mLineChart.animateX(1200, Easing.EasingOption.EaseInOutExpo);
                break;
            case R.id.y_axis_animation:// Y轴动画
                mLineChart.animateY(1200, Easing.EasingOption.EaseInOutExpo);
                break;
            case R.id.x_y_axis_animation:// XY轴动画
                mLineChart.animateXY(1200, 1200,
                        Easing.EasingOption.EaseInOutExpo, Easing.EasingOption.EaseInOutExpo);
                break;
            case R.id.save_to_local:// 保存到本地
                save();
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
    
    /**
     * 保存图表到本地
     */
    private void save() {
        if (ContextCompat.checkSelfPermission(LineChartActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请获取权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            if (mLineChart.saveToGallery(mTextView.getText().toString(), 100)) {
                Toast.makeText(LineChartActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LineChartActivity.this, "文件已存在", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    // 回调方法
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mLineChart.saveToGallery(mTextView.getText().toString(), 100)) {
                        Toast.makeText(LineChartActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LineChartActivity.this, "文件已存在",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "无法获取权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
    
}
