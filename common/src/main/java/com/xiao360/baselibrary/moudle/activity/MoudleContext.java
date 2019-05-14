package com.xiao360.baselibrary.moudle.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.collection.SparseArrayCompat;

public class MoudleContext {
    private Activity activity;
    private SparseArrayCompat<ViewGroup> viewGroups = new SparseArrayCompat<>();

    public Bundle getSavedInstance() {
        return savedInstance;
    }

    public void setSavedInstance(Bundle savedInstance) {
        this.savedInstance = savedInstance;
    }

    private Bundle savedInstance;

    public SparseArrayCompat<ViewGroup> getViewGroups() {
        return viewGroups;
    }

    public void setViewGroups(SparseArrayCompat<ViewGroup> viewGroups) {
        this.viewGroups = viewGroups;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
