package com.xiao360.baselibrary.moudle.activity;

import android.os.Bundle;

public abstract class AbsMoudle {
    public abstract void init(MoudleContext moudleContext, Bundle extend);

    public abstract void onSavedInstanceState(Bundle outState);

    public abstract void onOrientationChanged(Boolean isLand);

    public abstract void onResume();

    public abstract void onPause();

    public abstract void onStop();

    public abstract void onDestroy();
}
