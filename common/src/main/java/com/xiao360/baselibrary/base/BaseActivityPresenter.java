package com.xiao360.baselibrary.base;

import com.xiao360.baselibrary.mvp.BasePresenter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseActivityPresenter<T,E> extends BasePresenter<T,E> {
    protected RxAppCompatActivity context;
}
