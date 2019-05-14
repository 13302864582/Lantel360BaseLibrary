package com.xiao360.baselibrary.moudle.activity;

import android.content.res.Configuration;

import java.util.ArrayList;

import androidx.collection.ArrayMap;

public class MoudleManager {
    private ArrayMap<String, ArrayList<Integer>> modules =new ArrayMap<>();

    protected ArrayMap<String,AbsMoudle> allModules =new ArrayMap<>();

    public void moudleConfig(ArrayMap<String, ArrayList<Integer>> modules) {
        this.modules = modules;
    }

    public ArrayMap<String, ArrayList<Integer>> getModules() {
        return modules;
    }

    public AbsMoudle getModuleByNames(String name) {
        if(allModules!=null && allModules.size()>0){
            return allModules.get(name);
        }
        return null;
    }


    public void onConfigurationChanged(Configuration newConfig) {
        for(AbsMoudle moudle : allModules.values()){
            if(moudle!=null){
                moudle.onOrientationChanged(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
            }
        }
    }

    public void onResume(){
        for(AbsMoudle moudle : allModules.values()){
                if(moudle!=null){
                    moudle.onResume();
                }
        }
    }

    public void onPause(){
        for(AbsMoudle moudle : allModules.values()){
            if(moudle!=null){
                moudle.onPause();
            }
        }
    }

    public void onStop(){
        for(AbsMoudle moudle : allModules.values()){
            if(moudle!=null){
                moudle.onStop();
            }
        }
    }

    public void onDestroy(){
        for(AbsMoudle moudle : allModules.values()){
            if(moudle!=null){
                moudle.onDestroy();
            }
        }
    }
}
