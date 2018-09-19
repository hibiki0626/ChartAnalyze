package com.fairhand.chartanalyzeproject.entry;

/**
 * Created by FairHand on 2018/9/19.<br />
 * 折线图实体类
 */
public class LineChart {
    
    /**
     * 图
     */
    private int imageID;
    
    /**
     * 图名
     */
    private String chartName;
    
    /**
     * 图描述
     */
    private String chartDescribe;
    
    public LineChart(int imageID, String chartName, String chartDescribe) {
        this.imageID = imageID;
        this.chartName = chartName;
        this.chartDescribe = chartDescribe;
    }
    
    public int getImageID() {
        return imageID;
    }
    
    public String getChartName() {
        return chartName;
    }
    
    public String getChartDescribe() {
        return chartDescribe;
    }
    
}
