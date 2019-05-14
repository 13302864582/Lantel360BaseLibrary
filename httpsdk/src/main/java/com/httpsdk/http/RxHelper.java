package com.httpsdk.http;

import android.content.Context;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.RxActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxHelper {
    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static <T> ObservableTransformer<T, T> observableIO2Main(final RxFragment fragment) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(fragment.<T>bindToLifecycle());
            }
        };
    }

    public static <T> ObservableTransformer<T, T> observableIO2Main(final Context context) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                return composeContext(context, observable);
            }
        };
    }


    private static ObservableSource composeContext(Context context, Observable observable) {
        if(context instanceof RxActivity) {
            return  observable.compose(((RxActivity) context).bindUntilEvent(ActivityEvent.PAUSE));
        } else if(context instanceof RxFragmentActivity){
            return  observable.compose(((RxFragmentActivity) context).bindUntilEvent(ActivityEvent.PAUSE));
        }else if(context instanceof RxAppCompatActivity){
            return  observable.compose(((RxAppCompatActivity) context).bindUntilEvent(ActivityEvent.PAUSE));
        }else {
            return observable;
        }
    }
}
