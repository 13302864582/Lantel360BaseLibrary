package com.httpsdk.http;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Http
 */
public class Http {
    private static final String TAG = Http.class.getSimpleName();
    private static Http INSTANCE;
    private Retrofit retrofit;

    /**
     * 构造方法
     */
    public Http(OkHttpClient.Builder client,String baseUrl) {
        if(client==null){
            client=initOkHttp();
        }
        OkHttpClient okHttpClient=client.build();
        retrofit = initRetrofit(okHttpClient,baseUrl);
    }


    public <T> T createRequest(Class<T> clz){
        T t = retrofit.create(clz);
        return t;
    }

    /**
     * 初始化Retrofit
     */
   private Retrofit initRetrofit(OkHttpClient client, String baseUrl) {
       Retrofit.Builder builder = new Retrofit.Builder();
       builder.client(client)
               .baseUrl(baseUrl)
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               .addConverterFactory(GsonConverterFactory.create())
               .build();
        return builder.build();
    }

    /**
     * 初始化okhttp
     */
    private OkHttpClient.Builder initOkHttp() {
        return new OkHttpClient().newBuilder()
                .readTimeout(Constans.DEFAULT_TIME, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(Constans.DEFAULT_TIME, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(Constans.DEFAULT_TIME,TimeUnit.SECONDS)//设置写入超时时间
                .addInterceptor(new LogInterceptor())//添加打印拦截器
                .retryOnConnectionFailure(true);//设置出现错误进行重新连接
    }


    /**
     * 单例（双重检查）初始化
     */
    public static Http init(OkHttpClient.Builder client,String baseUrl){
        if(INSTANCE==null){
            synchronized (Http.class){
                if (INSTANCE==null){
                    INSTANCE = new Http(client,baseUrl);
                }
            }
        }
        return INSTANCE;
    }

    public static Http init(String baseUrl){
        return init(null,baseUrl);
    }


    /**
     * 获取单例
     */
    public static Http getInstance()
    {
        if (INSTANCE == null)
            throw new NullPointerException("you show invoke Http.init() before you use it ");
        return INSTANCE;
    }

}
