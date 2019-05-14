package com.xiao360.baselibrary.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentMap;

public class SpCache {
    private static final String TAG = SpCache.class.getSimpleName();
    private static SpCache INSTANCE;
    private ConcurrentMap<String, SoftReference<Object>> mCache;
    private String mPrefFileName = "com.fz.spcache";
    private Context mContext;

    /**

     构造方法
     */
    public SpCache(Context mContext,String mPrefFileName) {
        this.mContext = mContext.getApplicationContext();
        if(mPrefFileName !=null && !mPrefFileName.isEmpty()){
            this.mPrefFileName = mPrefFileName;
        }
    }
    /**
     * 单例（双重检查）初始化
     */
    public static SpCache init(Context context,String mPrefFileName){
        if(INSTANCE==null){
            synchronized (SpCache.class){
                if (INSTANCE==null){
                    INSTANCE = new SpCache(context,mPrefFileName);
                }
            }
        }
        return INSTANCE;
    }

    public static SpCache init(Context context){
        return init(context,null);
    }

    /**
     * 获取单例
     */
    private static SpCache getInstance()
    {
        if (INSTANCE == null)
            throw new NullPointerException("you show invoke SpCache.init() before you use it ");
        return INSTANCE;
    }

    private SharedPreferences getSharedPreferences()
    {
        return mContext.getSharedPreferences(mPrefFileName, Context.MODE_PRIVATE);
    }


    /***
     *   ============================================================contains，remove，clear=================================================================================
     */

//contains
    public boolean contains(String key)
    {
        return mCache.get(key).get() != null ? true : getSharedPreferences().contains(key);
    }

    //remove
    public static SpCache remove(String key)
    {
        return INSTANCE._remove(key);
    }

    private SpCache _remove(String key)
    {
        mCache.remove(key);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
        return INSTANCE;
    }

    //clear
    public static SpCache clear()
    {
        return INSTANCE._clear();
    }

    /**
     * 清除缓存
     */
    private SpCache _clear()
    {
        mCache.clear();
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
        return INSTANCE;
    }




    /***
     *   ============================================================get,put通用抽取=================================================================================
     */
    private Object readDisk(String key,Object defaultObject){
        SharedPreferences sp = getSharedPreferences();
        if(defaultObject instanceof String){
            return sp.getString(key, (String) defaultObject);
        }else if(defaultObject instanceof Float){
            return sp.getFloat(key, (Float) defaultObject);
        }else if(defaultObject instanceof Boolean){
            return sp.getBoolean(key, (Boolean) defaultObject);
        }else if(defaultObject instanceof Integer){
            return sp.getInt(key, (Integer) defaultObject);
        }else if(defaultObject instanceof Long){
            return sp.getLong(key, (Long) defaultObject);
        }
        Log.e(TAG, "you can not read object , which class is " + defaultObject.getClass().getSimpleName());
        return null;
    }


    private <T> SpCache put(String key, T t)
    {
        mCache.put(key, new SoftReference<Object>(t));
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        if (t instanceof String)
        {
            editor.putString(key, (String) t);
        } else if (t instanceof Integer)
        {
            editor.putInt(key, (Integer) t);
        } else if (t instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) t);
        } else if (t instanceof Float)
        {
            editor.putFloat(key, (Float) t);
        } else if (t instanceof Long)
        {
            editor.putLong(key, (Long) t);
        } else
        {
            LogUtils.d("===SP:you may be put a invalid object :");
            editor.putString(key, t.toString());
        }

        SharedPreferencesCompat.apply(editor);
        return INSTANCE;
    }

    private Object get(String key, Object defval)
    {   //缓存
        SoftReference reference = mCache.get(key);
        Object val = null;
        //缓存为空从SP读取，并存入缓存
        if(null == reference || null == reference.get()){
            val = readDisk(key,defval);
            mCache.put(key, new SoftReference<Object>(val));
        }
        //缓存不为空直接取
        val = mCache.get(key).get();
        return val;
    }

/***
 *   ============================================================反射调用=================================================================================
 */


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat
    {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod()
        {
            try
            {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e)
            {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(final SharedPreferences.Editor editor)
        {
            try
            {
                if (sApplyMethod != null)
                {
                    sApplyMethod.invoke(editor);
                    LogUtils.d( "============SPapply");
                    return;
                }
            } catch (IllegalArgumentException e)
            {
            } catch (IllegalAccessException e)
            {
            } catch (InvocationTargetException e)
            {
            }

            new AsyncTask<Void, Void, Void>()
            {

                @Override
                protected Void doInBackground(Void... params)
                {
                    editor.commit();
                    LogUtils.d( "============SPcommit");
                    return null;
                }
            }.execute();
        }
    }
/***

 ============================================================调用方法区=================================================================================
 */
    /***

     get**调用
     */
//get
    public static int getInt(String key, int defaultVal)
    {
        return (int) (getInstance().get(key, defaultVal));
    }

    public static long getLong(String key, long defaultVal)
    {
        return (long) (getInstance().get(key, defaultVal));
    }

    public static String getString(String key, String defaultVal)
    {
        return (String) (getInstance().get(key, defaultVal));
    }

    public static boolean getBoolean(String key, boolean defaultVal)
    {
        return (boolean) (getInstance().get(key, Boolean.class));
    }

    public static float getFloat(String key, float defaultVal)
    {
        return (float) (getInstance().get(key, defaultVal));
    }


    /***
     *    put**调用
     */
//put

    public static SpCache putInt(String key, int val)
    {
        return getInstance().put(key, val);
    }

    public static SpCache putLong(String key, long val)
    {
        return getInstance().put(key, val);
    }

    public static SpCache putString(String key, String val)
    {
        return getInstance().put(key, val);
    }

    public static SpCache putBoolean(String key, boolean val)
    {
        return getInstance().put(key, val);
    }

    public static SpCache putFloat(String key, float val)
    {
        return getInstance().put(key, val);
    }
}
