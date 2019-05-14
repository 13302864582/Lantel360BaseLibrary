package com.xiao360.baselibrary.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.ViewTreeObserver;

import androidx.annotation.RequiresApi;

public class DetachClickListener implements DialogInterface.OnClickListener{
    public static DetachClickListener wrap(DialogInterface.OnClickListener delegate) {
        return new DetachClickListener(delegate);
    }

    private DialogInterface.OnClickListener mDelegate;

    private DetachClickListener(DialogInterface.OnClickListener delegate) {
        this.mDelegate = delegate;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mDelegate != null) {
            mDelegate.onClick(dialog, which);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void clearOnDetach(Dialog dialog) {
        dialog.getWindow()
                .getDecorView()
                .getViewTreeObserver()
                .addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
                    @Override
                    public void onWindowAttached() {
                    }

                    @Override
                    public void onWindowDetached() {
                        mDelegate = null;
                    }
                });
    }




    DetachClickListener clickListener = DetachClickListener.wrap(new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    });
}
