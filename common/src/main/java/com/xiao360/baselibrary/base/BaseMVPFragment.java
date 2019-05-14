package com.xiao360.baselibrary.base;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cangwang.core.ModuleBus;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.xiao360.baselibrary.util.TUtil;
import com.xiao360.baselibrary.util.ToastUitl;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;

public abstract class BaseMVPFragment<T extends BaseFragmentPresenter, E extends ViewModel> extends RxFragment {
    protected View rootView;
    public T mPresenter;
    public E mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ModuleBus.getInstance().register(this);
        mPresenter = TUtil.getT(this, 0);
        mModel=this.getModel();
        if(mPresenter!=null)
            mPresenter.context=this;
        getLifecycle().addObserver(mPresenter);
        initPresenter();
        mPresenter.onCrete(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, rootView);
        if(getStateBarviewID()!=-1)
        rootView.findViewById(getStateBarviewID()).setVisibility(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT?View.GONE:View.VISIBLE);
        initView();
        return rootView;
    }

    protected abstract int getStateBarviewID();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ModuleBus.getInstance().unregister(this);
    }


    protected abstract E getModel();

    //获取布局文件
    protected abstract int getLayoutResource();
    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();
    //初始化view
    protected abstract void initView();


    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 开启加载进度条
     */
    /*public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(getActivity());
    }*/

    /**
     * 开启加载进度条
     *
     * @param msg
     */
  /*  public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(getActivity(), msg, true);
    }*/

    /**
     * 停止加载进度条
     */
    /*public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }*/


    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUitl.showShort(text);
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
        ToastUitl.showShort(resId);
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    public void showLongToast(int resId) {
        ToastUitl.showLong(resId);
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUitl.showLong(text);
    }


    /*public void showToastWithImg(String text,int res) {
        ToastUitl.showToastWithImg(text,res);
    }*/

    /**
     * 网络访问错误提醒
     */
   /* public void showNetErrorTip() {
        ToastUitl.showToastWithImg(getText(R.string.net_error).toString(),R.drawable.ic_wifi_off);
    }*/

   /* public void showNetErrorTip(String error) {
        ToastUitl.showToastWithImg(error,R.drawable.ic_wifi_off);
    }*/

    protected E FindModel(Class<E> modelClass, ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(this,factory).get(modelClass);
    }

    protected E FindModel(Class<E> modelClass) {
        return ViewModelProviders.of(this).get(modelClass);
    }

    protected int getResColor(int color) {
        return getRes().getColor(color);
    }

    private Resources getRes() {
        return getContext().getResources();
    }

    protected String[] getResStringArray(int array) {
        return getRes().getStringArray(array);
    }

   /* @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dispatchCreate(mProcessListener);
        dispatch(Lifecycle.Event.ON_CREATE);
    }
    @Override
    public void onStart() {
        super.onStart();
        dispatchStart(mProcessListener);
        dispatch(Lifecycle.Event.ON_START);
    }
    @Override
    public void onResume() {
        super.onResume();
        dispatchResume(mProcessListener);
        dispatch(Lifecycle.Event.ON_RESUME);
    }
    @Override
    public void onPause() {
        super.onPause();
        dispatch(Lifecycle.Event.ON_PAUSE);
    }
    @Override
    public void onStop() {
        super.onStop();
        dispatch(Lifecycle.Event.ON_STOP);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dispatch(Lifecycle.Event.ON_DESTROY);
        // just want to be sure that we won't leak reference to an activity
        mProcessListener = null;
    }*/
}
