package com.fairhand.chartanalyzeproject.view;

import android.content.Context;
import android.widget.TextView;

import com.fairhand.chartanalyzeproject.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

/**
 * Created by FairHand on 2018/9/17.<br />
 */
public class MyMarkerView extends MarkerView {
    
    private TextView mMarkTextView;
    
    private MPPointF mOffset;
    
    /**
     * 默认UI（灰色对话框）
     */
    public MyMarkerView(Context context) {
        super(context, R.layout.default_marker_view);
        mMarkTextView = findViewById(R.id.tv_marker_view);
    }
    
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     * 自定义对话框样式
     *
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mMarkTextView = findViewById(R.id.tv_marker_view);
    }
    
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            mMarkTextView.setText(String.valueOf((int) ce.getHigh()));
        } else {
            mMarkTextView.setText(String.valueOf((int) e.getY()));
        }
        super.refreshContent(e, highlight);
    }
    
    @Override
    public MPPointF getOffset() {
        if (mOffset == null) {
            // 居中
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }
        return mOffset;
    }
    
}
