package com.fairhand.chartanalyzeproject.chart;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.fairhand.chartanalyzeproject.util.LineChartUtil;
import com.fairhand.chartanalyzeproject.R;
import com.fairhand.chartanalyzeproject.view.CustomDialog;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by FairHand on 2018/9/17.<br />
 * 折线图
 */
public class LineChartActivity extends AppCompatActivity {
    
    private LineChart mLineChart;
    
    private Menu menu;
    
    /**
     * 此折线图名
     */
    private TextView mLineChartName;
    
    /**
     * 菜单项
     */
    private MenuItem onlyBoyItem;
    private MenuItem onlyGirlItem;
    private MenuItem verticesItem;
    private MenuItem circleItem;
    
    /**
     * 三条折线（男孩子、女孩子、总人数）
     */
    private LineDataSet boyDataSet;
    private LineDataSet girlDataSet;
    private LineDataSet totalDataSet;
    
    /**
     * 输入的数据
     */
    private ArrayList<Integer> inputBoySet;
    private ArrayList<Integer> inputGirlSet;
    
    /**
     * 折线数据
     */
    private LineData lineData;
    
    /**
     * Y轴
     */
    private YAxis yAxis;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        
        initAnimation();
        
        initView();
        
    }
    
    /**
     * 初始化动画
     */
    private void initAnimation() {
        Window window = getWindow();
        // 淡入淡出动画
        Fade fade = new Fade();
        fade.setDuration(520);
        window.setEnterTransition(fade);
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
        
        mLineChartName = findViewById(R.id.tv_chart_name);
        mLineChart = findViewById(R.id.line_chart);
        
        yAxis = mLineChart.getAxisLeft();
        
        LineChartUtil.drawLineChart(this, mLineChart, yAxis);
        
        setLineChartData();
        
    }
    
    /**
     * 设置图表数据
     */
    private void setLineChartData() {
        
        if ((mLineChart.getData() != null) && (mLineChart.getData().getDataSetCount() > 0)) {
            // 获取数据集
            boyDataSet = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
            girlDataSet = (LineDataSet) mLineChart.getData().getDataSetByIndex(1);
            // 设置值
            boyDataSet.setValues(LineChartUtil.getYValues(true, null));
            girlDataSet.setValues(LineChartUtil.getYValues(false, null));
            totalDataSet.setValues(LineChartUtil.getTotalYValues(null, null, null));
            // 通过数据更新
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            boyDataSet = LineChartUtil.setLineChartData(this, "男孩子",
                    R.color.blueBoy, LineChartUtil.getYValues(true, null));
            girlDataSet = LineChartUtil.setLineChartData(this, "女孩子",
                    R.color.pinkGirl, LineChartUtil.getYValues(false, null));
            totalDataSet = LineChartUtil.setLineChartData(this, "总人数",
                    R.color.colorPrimaryDark, LineChartUtil.getTotalYValues(null, null, null));
            
            lineData = new LineData(boyDataSet, girlDataSet, totalDataSet);
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
                if (verticesItem == null) {
                    verticesItem = menu.findItem(R.id.show_vertices_values);
                }
                boyDataSet.setDrawValues(!boyDataSet.isDrawValuesEnabled());
                girlDataSet.setDrawValues(!girlDataSet.isDrawValuesEnabled());
                totalDataSet.setDrawValues(!totalDataSet.isDrawValuesEnabled());
                // 重绘
                mLineChart.invalidate();
                
                if (boyDataSet.isDrawValuesEnabled()) {
                    verticesItem.setTitle(getString(R.string.hint_vertices_values));
                } else {
                    verticesItem.setTitle(getString(R.string.show_vertices_values));
                }
                break;
            case R.id.show_circle:// 是否显示圆点
                if (circleItem == null) {
                    circleItem = menu.findItem(R.id.show_circle);
                }
                boyDataSet.setDrawCircles(!boyDataSet.isDrawCirclesEnabled());
                girlDataSet.setDrawCircles(!girlDataSet.isDrawCirclesEnabled());
                totalDataSet.setDrawCircles(!totalDataSet.isDrawCirclesEnabled());
                mLineChart.invalidate();
                
                if (boyDataSet.isDrawCirclesEnabled()) {
                    circleItem.setTitle(getString(R.string.hint_circle));
                } else {
                    circleItem.setTitle(R.string.show_circle);
                }
                break;
            case R.id.dynamic_data:// 动态数据
                showSetDataDialog();
                break;
            case R.id.only_see_boy:// 只看男孩子
                if (onlyBoyItem == null) {
                    onlyBoyItem = menu.findItem(R.id.only_see_boy);
                }
                boyDataSet.setVisible(true);
                girlDataSet.setVisible(false);
                totalDataSet.setVisible(false);
                mLineChart.invalidate();
                break;
            case R.id.only_see_girl:// 只看女孩子
                if (onlyGirlItem == null) {
                    onlyGirlItem = menu.findItem(R.id.only_see_girl);
                }
                boyDataSet.setVisible(false);
                girlDataSet.setVisible(true);
                totalDataSet.setVisible(false);
                mLineChart.invalidate();
                break;
            case R.id.all_data:// 全数据
                boyDataSet.setVisible(true);
                girlDataSet.setVisible(true);
                totalDataSet.setVisible(true);
                mLineChart.invalidate();
                break;
            case R.id.save_to_local:// 保存到本地
                save();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }
    
    /**
     * 打开设置数据对话框
     */
    private void showSetDataDialog() {
        final CustomDialog dialog = new CustomDialog(this, R.style.CustomDialog);
        dialog.setCancelable(false);
        dialog.setOnPositiveClickListener(v -> {
            inputBoySet = dialog.getBoyData();
            inputGirlSet = dialog.getGirlData();
            // 判断输入数据是否完整
            if ((inputBoySet.size() < 8) || (inputGirlSet.size() < 8)) {
                // 震动
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                assert vibrator != null;
                vibrator.vibrate(240);
                Toast.makeText(LineChartActivity.this, "请输入完整数据", Toast.LENGTH_SHORT).show();
            } else {
                setDynamicData();
                Toast.makeText(LineChartActivity.this, "数据更新完成", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).setOnNegativeClickListener(v -> dialog.dismiss()).show();
    }
    
    /**
     * 设置动态数据
     */
    private void setDynamicData() {
        // 男孩子、女孩子数据
        int[] boyValues;
        int[] girlValues;
        // API >= 24
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            boyValues = inputBoySet.stream().mapToInt(Integer::intValue).toArray();
            girlValues = inputGirlSet.stream().mapToInt(Integer::intValue).toArray();
        } else {
            boyValues = arrayList2IntArray(inputBoySet);
            girlValues = arrayList2IntArray(inputGirlSet);
        }
        boyDataSet = LineChartUtil.setLineChartData(this, "男孩子",
                R.color.blueBoy, LineChartUtil.getYValues(true, boyValues));
        girlDataSet = LineChartUtil.setLineChartData(this, "女孩子",
                R.color.pinkGirl, LineChartUtil.getYValues(false, girlValues));
        totalDataSet = LineChartUtil.setLineChartData(this, "总人数",
                R.color.colorPrimaryDark, LineChartUtil.getTotalYValues(boyValues, girlValues, yAxis));
        lineData = new LineData(boyDataSet, girlDataSet, totalDataSet);
        lineData.setDrawValues(false);
        mLineChart.setData(lineData);
        mLineChart.invalidate();
        mLineChart.animateXY(1200, 1200);
        // 重设菜单栏标题
        if (onlyBoyItem != null) {
            onlyBoyItem.setTitle(R.string.only_see_boy);
        }
        if (onlyGirlItem != null) {
            onlyGirlItem.setTitle(R.string.only_see_girl);
        }
        if (verticesItem != null) {
            verticesItem.setTitle(R.string.show_vertices_values);
        }
        if (circleItem != null) {
            circleItem.setTitle(R.string.show_circle);
        }
    }
    
    /**
     * ArrayList转换为数组
     */
    private int[] arrayList2IntArray(ArrayList<Integer> list) {
        int[] toInt = new int[list.size()];
        for (int i = 0; i < toInt.length; i++) {
            toInt[i] = list.get(i);
        }
        return toInt;
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
            if (mLineChart.saveToGallery(mLineChartName.getText().toString() + UUID.randomUUID(),
                    100)) {
                Toast.makeText(LineChartActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LineChartActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    // 回调方法
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mLineChart.saveToGallery(mLineChartName.getText().toString() + UUID.randomUUID(),
                            100)) {
                        Toast.makeText(LineChartActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LineChartActivity.this, "保存失败",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "无法获取读取权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
    
}
