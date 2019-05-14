package com.xiao360.baselibrary.mvp;

import com.xiao360.baselibrary.lifecycle.BaseLifecycleObserver;

public abstract class BasePresenter<T,E> implements BaseLifecycleObserver {
    protected E mModel;
    protected T mView;

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
    }
}
