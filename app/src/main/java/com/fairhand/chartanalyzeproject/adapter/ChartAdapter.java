package com.fairhand.chartanalyzeproject.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fairhand.chartanalyzeproject.R;
import com.fairhand.chartanalyzeproject.chart.LineChartActivity;
import com.fairhand.chartanalyzeproject.entity.Chart;

import java.util.ArrayList;

/**
 * Created by FairHand on 2018/9/19.<br />
 * 图表RecyclerView的适配器类
 */
public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ViewHolder> {
    
    private Context mContext;
    
    private ArrayList<Chart> charts;
    
    public ChartAdapter(Context mContext, ArrayList<Chart> charts) {
        this.mContext = mContext;
        this.charts = charts;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(mContext).inflate(
                R.layout.item_recycler_view, viewGroup, false);
        final ViewHolder holder = new ViewHolder(rootView);
        holder.mCardView.setOnClickListener(v -> {
            switch (holder.getAdapterPosition()) {
                case 0:
                    Intent intent = new Intent(mContext, LineChartActivity.class);
                    if (mContext instanceof Activity) {
                        mContext.startActivity(intent,
                                ActivityOptions.makeSceneTransitionAnimation((Activity) mContext).toBundle());
                    } else {
                        mContext.startActivity(intent);
                    }
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        });
        return holder;
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Chart chart = charts.get(position);
        Glide.with(mContext).load(chart.getImageID()).into(viewHolder.chartImage);
        viewHolder.chartName.setText(chart.getChartName());
        viewHolder.chartDescribe.setText(chart.getChartDescribe());
    }
    
    @Override
    public int getItemCount() {
        return charts.size();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView chartImage;
        TextView chartName;
        TextView chartDescribe;
        
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            chartImage = itemView.findViewById(R.id.iv_chart_icon);
            chartName = itemView.findViewById(R.id.tv_chart_name);
            chartDescribe = itemView.findViewById(R.id.tv_chart_description);
        }
    }
    
}
