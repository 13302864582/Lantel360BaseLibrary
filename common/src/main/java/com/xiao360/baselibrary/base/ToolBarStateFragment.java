package com.xiao360.baselibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.baselibrary.R;
import com.xiao360.baselibrary.edit.NormalStateLayout;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

public abstract class ToolBarStateFragment<T extends BaseFragmentPresenter, E extends ViewModel> extends BaseMVPFragment<T, E> {
    protected NormalStateLayout stateLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (super.rootView == null)
            super.rootView = getInflate(R.layout.toolbar_state,inflater, container);
        LinearLayout linearLayout = (LinearLayout) super.rootView;
        //添加toolbar容器
        if(getToolBarLayoutID()!=-1){
            LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.addView(getInflate(getToolBarLayoutID(),inflater, container),params0);
        }

        //添加状态容器
        stateLayout = new CommonLyoutState(getContext());
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params1.weight = 1;
        linearLayout.addView(stateLayout,params1);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    class CommonLyoutState extends NormalStateLayout{
        public CommonLyoutState(Context context) {
            super(context);
        }

        @Override
        protected int getEmptyViewId() {
            return ToolBarStateFragment.this.getEmptyViewId();
        }

        @Override
        protected int getFailViewId() {
            return ToolBarStateFragment.this.getFailViewId();
        }

        @Override
        protected int getLoadingViewId() {
            return ToolBarStateFragment.this.getLoadingViewId();
        }

        @Override
        public View getContentView() {
            return ToolBarStateFragment.this.getContentView();
        }

        @Override
        public int getContentViewLayoutId() {
            return ToolBarStateFragment.this.getContentViewLayoutId();
        }

        @Override
        protected int getContainerLayoutID() {
            return ToolBarStateFragment.this.getContainerLayoutID();
        }
    }

    protected abstract int getContainerLayoutID();

    protected abstract int getFailViewId();

    protected abstract int getLoadingViewId();

    protected abstract int getEmptyViewId();

    protected abstract int getContentViewLayoutId();

    protected abstract View getContentView();

    protected abstract int getToolBarLayoutID();

    private View getInflate(int layoutRes,LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(layoutRes, container, false);
    }

    @Override
    protected int getLayoutResource() {
        return -1;
    }
}
