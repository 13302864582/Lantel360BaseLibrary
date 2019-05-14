package com.xiao360.baselibrary.listview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiao360.baselibrary.base.BaseModel;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>{
    /**
     *  context 上下文
     */
    protected Context context;

    /**
     * inflater 布局渲染器
     */
    protected LayoutInflater inflater;

    /**
     * datas 数据模型
     */
    protected List<T> datas;

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    /**
     * 适配器构造
     */
    public BaseRecyclerViewAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
        this.inflater = LayoutInflater.from(context);
    }


    /**
     * 创建视图的holder
     */
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = CreateViewHolder(inflater,parent,viewType);
        return holder;
    }

    protected abstract BaseViewHolder CreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);


    /**
     * 获取holder
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if(null == datas || position>=datas.size()){
            bindViewHolder(holder, null, position,getItemViewType(position));
        }else {
            bindViewHolder(holder, datas.get(position), position,getItemViewType(position));
        }
    }


    /**
     * 条目数量
     */
    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size();
    }

    protected abstract void bindViewHolder(BaseViewHolder holder, T data, int position, int viewType);

    public String getString(int str) {
        return null == context?null:context.getResources().getString(str);
    }

    public int getColor(int color) {
        return null == context?null:context.getResources().getColor(color);
    }

    public void addDatas(List data) {
        if(datas != null && data != null)
            datas.addAll(data);
    }

    protected void setText(String value,TextView textView) {
        setText(value,"",textView);
    }

    protected void setText(String value,String defaultStr,TextView textView) {
        textView.setText(TextUtils.isEmpty(value)?defaultStr:value);
    }
}
