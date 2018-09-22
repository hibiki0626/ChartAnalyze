package com.fairhand.chartanalyzeproject.util;
import android.graphics.Typeface;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
public class PieChartUtil {
    /**
     * 显示饼状图表
     *
     * @param pieChart
     * @param PieData
     */
    public static void drawPieChart(PieChart pieChart, PieData PieData) {
        //设置半透明圈效果
        pieChart.setTransparentCircleRadius(61f);
        //设置内部圆的半径
        pieChart.setHoleRadius(64f);
        //饼状图可以添加文字
        pieChart.setDrawCenterText(true);
        //是否显示内部圆环
        pieChart.setDrawHoleEnabled(true);
        //设置起始角度
        pieChart.setRotationAngle(90f);
        pieChart.setRotationEnabled(true);//设置可以手动旋转
        pieChart.setUsePercentValues(true);//显示成百分比
        pieChart.setCenterText("近八年来移动班级男女人数走势图");
        //设置图标中间文字字体大小
        pieChart.setCenterTextSize(28);
        //设置字体类型
        pieChart.setCenterTextTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC));
        //将数据添加到PieData中
        pieChart.setData(PieData);
        //设置高光效果
        pieChart.needsHighlight(50);
        //设置图标文本字体大小
        pieChart. setEntryLabelTextSize(10f);
        //获取图例
        Legend l = pieChart.getLegend();
        //是否启用图列（true：下面属性才有意义）
        l.setEnabled(true);
        //设置图例的形状
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        //设置图例的大小
        l.setFormSize(10);
        //设置图例标签文本的大小
        l.setTextSize(14f);
        //设置每个图例实体中标签和形状之间的间距
        l.setFormToTextSpace(10f);
        //
        l.setDrawInside(false);
        //设置图列换行(注意使用影响性能,仅适用legend位于图表下面)
        l.setWordWrapEnabled(true);
        //设置图例实体之间延X轴的间距
        l.setXEntrySpace(7f);
        //设置图例实体之间延Y轴的间距
        l.setYEntrySpace(0f);
        //设置比例块Y轴偏移量
        l.setYOffset(0f);
        pieChart.animateXY(1200, 1200);//设置动画时间
    }

}
