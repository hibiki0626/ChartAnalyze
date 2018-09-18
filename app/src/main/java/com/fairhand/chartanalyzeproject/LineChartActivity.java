package com.fairhand.chartanalyzeproject;

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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by FairHand on 2018/9/17.<br />
 * 折线图
 */
public class LineChartActivity extends AppCompatActivity {
    
    private LineChart mLineChart;
    
    private Menu menu;
    
    /**
     * 表名
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
            actionBar.setTitle("Line Chart");
        }
        
        mTextView = findViewById(R.id.tv_chart_name);
        mLineChart = findViewById(R.id.line_chart);
        // 按比例缩放
        mLineChart.setPinchZoom(true);
        // 无数据时显示的文字
        mLineChart.setNoDataText("暂无数据");
        // 禁止缩放
        mLineChart.setScaleEnabled(false);
        // 禁止描述
        mLineChart.getDescription().setEnabled(false);
        // 设置点击一个点显示一个值的对话框
        mLineChart.setMarker(new MyMarkerView(this, R.layout.custom_marker_view));
        
        // X坐标轴
        final XAxis xAxis = mLineChart.getXAxis();
        // 设置X轴位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 禁止显示网格线
        xAxis.setDrawGridLines(false);
        // 设置X轴的最小间距
        xAxis.setGranularity(1f);
        // 设置X轴最大、最小值（自动分配刻度显示）
        xAxis.setAxisMinimum(2011f);
        xAxis.setAxisMaximum(2018f);
        // 格式化X轴值为字符串
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }
        });
        
        // Y坐标轴
        YAxis yAxis = mLineChart.getAxisLeft();
        // 禁止显示网格线
        yAxis.setDrawGridLines(false);
        // 设置Y轴最大、最小值（自动分配刻度显示）
        yAxis.setAxisMinimum(30);
        yAxis.setAxisMaximum(60);
        // 禁止显示右侧Y轴
        mLineChart.getAxisRight().setEnabled(false);
        
        // 图例设置
        Legend legend = mLineChart.getLegend();
        // 设置水平居右对齐
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        // 设置垂直居上对齐
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        // 设置布局为垂直布局
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        // 设置图例在图表外部绘制
        legend.setDrawInside(false);
        // 设置图例的文本方向
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        // 设置图例的形式为水平线条
        legend.setForm(Legend.LegendForm.LINE);
        // 设置标签的大小
        legend.setTextSize(12f);
        
        setLineChartData();
        
    }
    
    /**
     * 设置图表数据
     */
    private void setLineChartData() {
        ArrayList<Entry> boyEntries = new ArrayList<>();
        for (int i = 2010; i <= 2018; i++) {
            int yValues = new Random().nextInt(60);
            if (yValues < 30) {
                yValues += 30;
            }
            boyEntries.add(new Entry(i, yValues));
        }
        
        ArrayList<Entry> girlEntries = new ArrayList<>();
        for (int i = 2010; i <= 2018; i++) {
            int yValues = new Random().nextInt(60);
            if (yValues < 30) {
                yValues += 30;
            }
            girlEntries.add(new Entry(i, yValues));
        }
        
        LineDataSet boyDataSet;// 男孩子折线
        LineDataSet girlDataSet;// 女孩子折线
        if ((mLineChart.getData() != null) && (mLineChart.getData().getDataSetCount() > 0)) {
            // 获取数据集
            boyDataSet = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
            girlDataSet = (LineDataSet) mLineChart.getData().getDataSetByIndex(1);
            // 设置值
            boyDataSet.setValues(boyEntries);
            girlDataSet.setValues(girlEntries);
            // 通过数据更新
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            // 创建一个标签为男孩子的数据集
            boyDataSet = new LineDataSet(boyEntries, "男孩子");
            // 设置数据依赖左侧Y轴
            boyDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            // 设置折线颜色
            boyDataSet.setColor(ContextCompat.getColor(this, R.color.blueBoy));
            // 不显示圆点
            boyDataSet.setDrawCircles(false);
            // 设置折线宽度
            boyDataSet.setLineWidth(2f);
            // 格式化值为字符串
            boyDataSet.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return String.valueOf((int) value);
                }
            });
            // 设置值的文本大小
            boyDataSet.setValueTextSize(12);
            // 禁止显示十字交叉的纵横线
            boyDataSet.setDrawHighlightIndicators(false);
            
            // 创建一个标签为女孩子的数据集
            girlDataSet = new LineDataSet(girlEntries, "女孩子");
            girlDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            girlDataSet.setColor(ContextCompat.getColor(this, R.color.pinkGirl));
            girlDataSet.setDrawCircles(false);
            girlDataSet.setLineWidth(2f);
            girlDataSet.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return String.valueOf((int) value);
                }
            });
            girlDataSet.setValueTextSize(12);
            girlDataSet.setDrawHighlightIndicators(false);
            
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
                mLineChart.animateX(3000);
                break;
            case R.id.y_axis_animation:// Y轴动画
                mLineChart.animateY(3000, Easing.EasingOption.EaseInCubic);
                break;
            case R.id.x_y_axis_animation:// XY轴动画
                mLineChart.animateXY(3000, 3000);
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
