package com.xiao360.baselibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.baselibrary.R;
import androidx.annotation.Nullable;

public class ImageText extends RelativeLayout {
    private float textSize;
    private int textColor;
    private int imgRes;

    private int textLeftMargin;
    private TextView mTextView;
    private ImageView mImgView;
    private View mRootView;
    private String text;

    public ImageText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.img_text,this,false);
        addView(mRootView);
        mImgView = mRootView.findViewById(R.id.icon);
        mTextView = mRootView.findViewById(R.id.title);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImageTextView);
            imgRes = typedArray.getResourceId(R.styleable.ImageTextView_imgSrc,0);
            text = typedArray.getString(R.styleable.ImageTextView_textDef);
            textSize = typedArray.getDimension(R.styleable.ImageTextView_textSize, R.dimen.img_text);
            textColor = typedArray.getColor(R.styleable.ImageTextView_textColor, getResources().getColor(R.color.edit_txt_color));
            textLeftMargin = (int) typedArray.getDimension(R.styleable.ImageTextView_textMarginLeft, 0);

            mImgView.setImageResource(imgRes);
            mTextView.setTextColor(textColor);
            mTextView.setTextSize(textSize);
            mTextView.setText(text);
        }
    }

    public void setEnableImgText(boolean enable){
        mImgView.setEnabled(enable);
        mTextView.setEnabled(enable);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public void setTextColor(int color) {
        mTextView.setTextColor(color);
    }

    public void setTextSize(int size) {
        mTextView.setTextSize(size);
    }

    public void setImgSize(int size) {
        setImgSize(size,size);
    }

    public void setImgSize(int width,int height) {
        RelativeLayout.LayoutParams params = (LayoutParams) mImgView.getLayoutParams();
        params.width = width;
        params.height = height;
        mImgView.setLayoutParams(params);
    }

    public void setImgRes(int res) {
        mImgView.setImageResource(res);
    }

}
