package com.xiao360.baselibrary.edit;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;

public abstract class StateLayout extends FrameLayout {
    private SparseArray<View> views;

    /** 包含多个状态的容器 */
    private FrameLayout container;

    public StateLayout(Context context) {
        this(context,null);
    }

    public StateLayout(Context context,AttributeSet attrs) {
        super(context, attrs);
        this.views = new SparseArray<>();
        init();
    }

    /** 初始化容器 */
    private void init() {
        // 创建包含“正在加载”、“加载失败”、“加载为空”状态的容器
        container = (FrameLayout) View.inflate(getContext(), getContainerLayoutID(), null);
        initContainer(container);
        addView(container);
    }


    /**
     * 显示指定的View，并且隐藏其它View
     * @param view
     */
    protected void showView(View view) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
//			child.setVisibility(child == view ? View.VISIBLE : View.GONE);
            if (child == view) {
                // 显示指定的View
                child.setVisibility(View.VISIBLE);
            } else {
                // 其它的View隐藏
                child.setVisibility(View.GONE);
            }
        }
    }



    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = container.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /** 初始化容器 */
    protected abstract void initContainer(FrameLayout container);

    /** 返回状态容器的布局ID */
    protected abstract int getContainerLayoutID();
}
