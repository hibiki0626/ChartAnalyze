package com.fairhand.chartanalyzeproject.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fairhand.chartanalyzeproject.R;

import java.util.ArrayList;

/**
 * Created by FairHand on 2018/9/19.<br />
 * 自定义Dialog
 */
public class CustomDialog extends Dialog {
    
    private View.OnClickListener onPositiveClickListener;
    
    private View.OnClickListener onNegativeClickListener;
    
    /**
     * 男孩子编辑框集
     */
    private ArrayList<EditText> boyEditTexts;
    
    /**
     * 女孩子编辑框集
     */
    private ArrayList<EditText> girlEditTexts;
    
    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_set_data);
        
        init();
        
    }
    
    /**
     * 初始化
     */
    private void init() {
        TextView positiveButton = findViewById(R.id.dialog_positive);
        TextView negativeButton = findViewById(R.id.dialog_negative);
        if (onPositiveClickListener != null) {
            positiveButton.setOnClickListener(onPositiveClickListener);
        }
        if (onNegativeClickListener != null) {
            negativeButton.setOnClickListener(onNegativeClickListener);
        }
        
        boyEditTexts = new ArrayList<>();
        girlEditTexts = new ArrayList<>();
        
        EditText mEditText11Boy = findViewById(R.id.et_11_boy);
        boyEditTexts.add(mEditText11Boy);
        EditText mEditText11Girl = findViewById(R.id.et_11_girl);
        girlEditTexts.add(mEditText11Girl);
        EditText mEditText12Boy = findViewById(R.id.et_12_boy);
        boyEditTexts.add(mEditText12Boy);
        EditText mEditText12Girl = findViewById(R.id.et_12_girl);
        girlEditTexts.add(mEditText12Girl);
        EditText mEditText13Boy = findViewById(R.id.et_13_boy);
        boyEditTexts.add(mEditText13Boy);
        EditText mEditText13Girl = findViewById(R.id.et_13_girl);
        girlEditTexts.add(mEditText13Girl);
        EditText mEditText14Boy = findViewById(R.id.et_14_boy);
        boyEditTexts.add(mEditText14Boy);
        EditText mEditText14Girl = findViewById(R.id.et_14_girl);
        girlEditTexts.add(mEditText14Girl);
        EditText mEditText15Boy = findViewById(R.id.et_15_boy);
        boyEditTexts.add(mEditText15Boy);
        EditText mEditText15Girl = findViewById(R.id.et_15_girl);
        girlEditTexts.add(mEditText15Girl);
        EditText mEditText16Boy = findViewById(R.id.et_16_boy);
        boyEditTexts.add(mEditText16Boy);
        EditText mEditText16Girl = findViewById(R.id.et_16_girl);
        girlEditTexts.add(mEditText16Girl);
        EditText mEditText17Boy = findViewById(R.id.et_17_boy);
        boyEditTexts.add(mEditText17Boy);
        EditText mEditText17Girl = findViewById(R.id.et_17_girl);
        girlEditTexts.add(mEditText17Girl);
        EditText mEditText18Boy = findViewById(R.id.et_18_boy);
        boyEditTexts.add(mEditText18Boy);
        EditText mEditText18Girl = findViewById(R.id.et_18_girl);
        girlEditTexts.add(mEditText18Girl);
    }
    
    /**
     * 确认按钮点击
     */
    public CustomDialog setOnPositiveClickListener(
            View.OnClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
        return this;
    }
    
    /**
     * 取消按钮点击
     */
    public CustomDialog setOnNegativeClickListener(View.OnClickListener onNegativeClickListener) {
        this.onNegativeClickListener = onNegativeClickListener;
        return this;
    }
    
    /**
     * 获取到输入的男孩子数据
     */
    public ArrayList<Integer> getBoyData() {
        ArrayList<Integer> data = new ArrayList<>();
        for (EditText editText : boyEditTexts) {
            if (!editText.getText().toString().equals("")) {
                data.add(Integer.valueOf(editText.getText().toString()));
            }
        }
        return data;
    }
    
    /**
     * 获取到输入的女孩子数据
     */
    public ArrayList<Integer> getGirlData() {
        ArrayList<Integer> data = new ArrayList<>();
        for (EditText editText : girlEditTexts) {
            if (!editText.getText().toString().equals("")) {
                data.add(Integer.valueOf(editText.getText().toString()));
            }
        }
        return data;
    }
    
}
