package com.xiao360.baselibrary.moudle.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;


import java.util.ArrayList;

import androidx.collection.ArrayMap;
import androidx.collection.SparseArrayCompat;

public class ActivityMoudleManager extends MoudleManager{
    public void initMoudles(Bundle savedInstance, Activity activity, ArrayMap<String,ArrayList<Integer>> modules){
        if(activity ==null || modules == null)
            return;
        moudleConfig(modules);
        initMoudles(savedInstance,activity);
    }

    private void initMoudles(Bundle savedInstance, Activity activity) {
        if(getModules() == null)
            return;
        for(String moudleName : getModules().keySet()){
                //Moudle工厂创建对应moudle
                AbsMoudle moudle = MoudleFactory.newMoudleInstance(moudleName);
                if(moudle!=null){
                    MoudleContext moudleContext = new MoudleContext();
                    moudleContext.setActivity(activity);
                    moudleContext.setSavedInstance(savedInstance);
                    SparseArrayCompat<ViewGroup> viewGroups =new SparseArrayCompat<>();
                    ArrayList<Integer> mViewIds = getModules().get(moudleName);
                    if(mViewIds != null && mViewIds.size() > 0){
                        for(int i = 0;i < mViewIds.size();i++){
                            viewGroups.put(i, (ViewGroup) activity.findViewById(mViewIds.get(i).intValue()));
                        }
                    }
                    moudleContext.setViewGroups(viewGroups);
                    moudle.init(moudleContext,savedInstance);
                    allModules.put(moudleName,moudle);
                }
        }

    }
}
