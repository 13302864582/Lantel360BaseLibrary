package com.xiao360.baselibrary.lifecycle;


import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LifeCycleFragmentFactory extends ViewModelProvider.NewInstanceFactory {
    private LifecycleProvider<FragmentEvent> provider;

    public LifeCycleFragmentFactory(LifecycleProvider<FragmentEvent> provider) {
        this.provider = provider;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Constructor constructor=null;
        try {
            constructor=modelClass.getConstructor(LifecycleProvider.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            return (T) constructor.newInstance(provider);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
