package com.xiao360.baselibrary.base;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cangwang.core.ModuleBus;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.xiao360.baselibrary.util.AppManager;
import com.xiao360.baselibrary.util.TUtil;
import com.xiao360.baselibrary.util.ToastUitl;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;

/**
 * activity基类
 * BaseActivityPresenter T 控制器
 * ViewModel E 数据model
 * */
public abstract class BaseMVPActivity<T extends BaseActivityPresenter, E extends ViewModel> extends RxAppCompatActivity {
    public T mPresenter;
    public E mModel;
    private Context mContext;
    private boolean isConfigChange=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //activity设置
        doBeforeSetcontentView();

        //设置视图布局
        setContentView(getLayoutId());
        //ButterKnife绑定注入
        ButterKnife.bind(this);

        if(getStateBarviewID()!=-1)
         findViewById(getStateBarviewID()).setVisibility(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT? View.GONE:View.VISIBLE);

        //模块路由绑定注入
        ARouter.getInstance().inject(this);

        //模块通信绑定
        ModuleBus.getInstance().register(this);

        //上下文
        mContext = this;

        //反射自动生成Presenter控制器
        mPresenter = TUtil.getT(this, 0);

        //通过子类生成Model
        mModel=this.getModel();

        //控制器设置上下文
        if(mPresenter!=null){
            mPresenter.context=this;
        }

        //绑定android生命周期
        if(mPresenter!=null)
        getLifecycle().addObserver(mPresenter);

        //初始化Presenter
        this.initPresenter();

        //初始化状态栏
        ImmersionBar.with(this)
                .hideBar(hideBar())
                .init();

        //初始化view视图
        this.initView();
    }

    protected abstract BarHide hideBar();

    protected abstract int getStateBarviewID();

    protected abstract E getModel();

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
    }
    /*********************子类实现*****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView();

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
    public void startActivityForResult(Class<?> cls, Bundle bundle,int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isConfigChange=true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ModuleBus.getInstance().unregister(this);
        if(!isConfigChange){
            AppManager.getAppManager().finishActivity(this);
        }
    }

    /**
     * 开启浮动加载进度条
     */
    protected E FindModel(Class<E> modelClass, ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(this,factory).get(modelClass);
    }

    protected E FindModel(Class<E> modelClass) {
        return ViewModelProviders.of(this).get(modelClass);
    }

    /**
     * 开启浮动加载进度条
     */
   /* public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(this);
    }*/

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    /*public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(this, msg, true);
    }*/

    /**
     * 停止浮动加载进度条
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


    protected void setFullScreen(boolean FullScreen){
        if (!FullScreen){//设置为非全屏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else{//设置为全屏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    protected int getResColor(int color){
        return getResources().getColor(color);
    }


   /* ImmersionBar.with(this)
            .transparentStatusBar()  //透明状态栏，不写默认透明色
                 .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                 .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
                 .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
                 .navigationBarColor(R.color.colorPrimary) //导航栏颜色，不写默认黑色
                 .barColor(R.color.colorPrimary)  //同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
                 .statusBarAlpha(0.3f)  //状态栏透明度，不写默认0.0f
                 .navigationBarAlpha(0.4f)  //导航栏透明度，不写默认0.0F
                 .barAlpha(0.3f)  //状态栏和导航栏透明度，不写默认0.0f
                 .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                 .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                 .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                 .autoStatusBarDarkModeEnable(true,0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                 .autoNavigationBarDarkModeEnable(true,0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
                 .flymeOSStatusBarFontColor(R.color.btn3)  //修改flyme OS状态栏字体颜色
                 .fullScreen(true)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
                 .hideBar(BarHide.FLAG_HIDE_BAR)  //隐藏状态栏或导航栏或两者，不写默认不隐藏
                 .addViewSupportTransformColor(toolbar)  //设置支持view变色，可以添加多个view，不指定颜色，默认和状态栏同色，还有两个重载方法
                 .titleBar(view)    //解决状态栏和布局重叠问题，任选其一
                 .statusBarView(view)  //解决状态栏和布局重叠问题，任选其一
                 .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                 .supportActionBar(true) //支持ActionBar使用
                 .statusBarColorTransform(R.color.orange)  //状态栏变色后的颜色
                 .navigationBarColorTransform(R.color.orange) //导航栏变色后的颜色
                 .barColorTransform(R.color.orange)  //状态栏和导航栏变色后的颜色
                 .removeSupportView(toolbar)  //移除指定view支持
                 .removeSupportAllView() //移除全部view支持
                 .addTag("tag")  //给以上设置的参数打标记
                 .getTag("tag")  //根据tag获得沉浸式参数
                 .reset()  //重置所以沉浸式参数
                 .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false
                 .setOnKeyboardListener(new OnKeyboardListener() {    //软键盘监听回调
                    @Override
                        public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                            LogUtils.e(isPopup);  //isPopup为true，软键盘弹出，为false，软键盘关闭
                            }
                        })
                 .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //单独指定软键盘模式
                 .setOnNavigationBarListener(onNavigationBarListener) //导航栏显示隐藏监听，目前只支持华为和小米手机
                 .init();  //必须调用方可沉浸式*/
}
