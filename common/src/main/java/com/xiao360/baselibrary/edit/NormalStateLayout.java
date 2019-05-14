package com.xiao360.baselibrary.edit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public abstract class NormalStateLayout extends StateLayout {
   private View loadingView;
    private View failView;
    private View emptyView;
    private View contentView;

    public NormalStateLayout(Context context) {
        super(context);
    }

    public NormalStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initContainer(FrameLayout container) {
        // 由于每一个界面它们正常显示的界面都不一样，所以获取这些正常界面的方法写成抽象的，让子类去决定
        if (getContentView() != null) {
            contentView = getContentView();
        } else {
            contentView = View.inflate(getContext(), getContentViewLayoutId(), null);
        }

        container.addView(contentView);

        // 获取“正在加载”、“加载失败”、“加载为空”这3种状态的View的引用
        loadingView = container.findViewById(getLoadingViewId());
        failView = container.findViewById(getFailViewId());
        emptyView = container.findViewById(getEmptyViewId());

        // 默认显示“正在加载”
       /* loadingView.setVisibility(View.VISIBLE);
        failView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);*/
    }

    protected abstract int getEmptyViewId();

    protected abstract int getFailViewId();

    protected abstract int getLoadingViewId();

    /** 显示“正在加载”的View */
    public void showLoadingView() {
        showView(loadingView);
    }

    /** 显示“加载失败”的View */
    public void showFailView() {
        showView(failView);
    }

    /** 显示“正在加载”的View */
    public void showEmptyView() {
        showView(emptyView);
    }

    /** 显示数据加载成功时”正常界面“的View */
    public void showContentView() {
        showView(contentView);
    }

   /** 返回一个正常界面的View */
    public abstract View getContentView();

    /** 返回一个正常界面的布局id */
    public abstract int getContentViewLayoutId();
}
