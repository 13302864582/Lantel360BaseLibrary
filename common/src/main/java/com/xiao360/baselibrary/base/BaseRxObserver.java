package com.xiao360.baselibrary.base;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseRxObserver<T> implements Observer<T> {

    private static final String TAG = "BaseObserver";
    @Override
    public void onSubscribe(Disposable d) {
        //Log.d(TAG, "onSubscribe: " );
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e);
    }

    @Override
    public void onComplete() {
       // Log.d(TAG, "onComplete: " );
    }

    public abstract void onSuccess(T demo);

    public abstract void onFailure(Throwable e);
}
