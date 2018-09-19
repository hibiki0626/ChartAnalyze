package com.fairhand.chartanalyzeproject.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.fairhand.chartanalyzeproject.R;
import com.fairhand.chartanalyzeproject.view.MyMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

/**
 * Created by FairHand on 2018/9/19.<br />
 * 折线图工具类
 */
public class LineChartUtil {
    
    /**
     * 模拟男孩子数据
     */
    private static Integer[] boyValues = {17, 21, 15, 15, 11, 10, 19, 11, 12, 13};
    
    /**
     * 模拟女孩子数据
     */
    private static Integer[] girlValues = {21, 17, 14, 24, 17, 17, 14, 22, 19, 14};
    
    /**
     * 获取Y轴数据
     */
    public static ArrayList<Entry> getYValues(boolean isBoy) {
        ArrayList<Entry> entries = new ArrayList<>();
        int index = 0;
        if (isBoy) {
            for (int i = 2011; i <= 2018; i++) {
                entries.add(new Entry(i, boyValues[index++]));
            }
        } else {
            for (int i = 2011; i <= 2018; i++) {
                entries.add(new Entry(i, girlValues[index++]));
            }
        }
        return entries;
    }
    
    /**
     * 计算并获取总人数Y轴数据
     */
    public static ArrayList<Entry> getTotalYValues() {
        ArrayList<Entry> entries = new ArrayList<>();
        int index = 0;
        for (int i = 2011; i <= 2018; i++) {
            entries.add(new Entry(i, boyValues[index] + girlValues[index]));
            index++;
        }
        return entries;
    }
    
    /**
     * 设置折线图数据
     */
    public static LineDataSet setLineChartData(Context mContext, String label,
                                               int lineColorID, ArrayList<Entry> entries) {
        
        LineDataSet lineDataSet = new LineDataSet(entries, label);
        // 设置数据依赖左侧Y轴
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        // 设置折线颜色
        lineDataSet.setColor(ContextCompat.getColor(mContext, lineColorID));
        // 不显示圆点
        lineDataSet.setDrawCircles(false);
        // 设置折线宽度
        lineDataSet.setLineWidth(1.6f);
        // 格式化值为字符串
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.valueOf((int) value);
            }
        });
        // 设置值的文本大小
        lineDataSet.setValueTextSize(12);
        // 禁止显示十字交叉的纵横线
        lineDataSet.setDrawHighlightIndicators(false);
        
        return lineDataSet;
    }
    
    /**
     * 绘制折线图
     */
    public static void drawLineChart(Context mContext, LineChart mLineChart) {
        // 设置按比例缩放
        mLineChart.setPinchZoom(true);
        // 设置无数据时显示的文字
        mLineChart.setNoDataText(mContext.getString(R.string.no_data));
        // 禁止缩放
        mLineChart.setScaleEnabled(false);
        // 禁止描述
        mLineChart.getDescription().setEnabled(false);
        // 设置点击一个点显示一个值的对话框
        mLineChart.setMarker(new MyMarkerView(mContext));
        // 设置动画
        mLineChart.animateXY(1200, 1200);
        
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
        yAxis.setAxisMinimum(0);
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
    }
    
}
