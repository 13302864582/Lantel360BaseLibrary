package com.xiao360.baselibrary.util;

import android.content.Context;
import android.os.Build;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;


import java.util.List;

import androidx.annotation.NonNull;

public class PermissionTool {
    public static void requestPermission(final Context context, final PermissionQuestListener listener, final String... permissionGroup) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermissions(context, permissionGroup)) {
                if (listener != null)
                    listener.onGranted();
                return;
            }

            // 没有权限，申请权限
            AndPermission.with(context)
                    .runtime()
                    .permission(permissionGroup)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            if (listener != null)
                                listener.onGranted();
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(final List<String> data) {
                            if (AndPermission.hasAlwaysDeniedPermission(context, permissionGroup)) {
                                //替换为自己的对话框
                                new MaterialDialog.Builder(context)
                                        .title("标题")
                                        .content("内容")
                                        .cancelable(false)
                                        .canceledOnTouchOutside(false)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                dialog.dismiss();
                                                //拒绝后重复请求
                                                requestPermission(context, listener, permissionGroup);
                                            }
                                        })
                                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                dialog.dismiss();
                                                if (listener != null)
                                                    listener.onDenied(data);
                                            }
                                        })
                                        .show();
                            } else {
                                //拒绝后重复请求
                                requestPermission(context, listener, permissionGroup);
                            }
                        }
                    })
                    .start();
        } else {
            if (listener != null)
                listener.onGranted();
        }
    }

    /**
     * 权限申请监听
     */
    public interface PermissionQuestListener {
        void onGranted(); //允许获得权限后操作

        void onDenied(List<String> data); //拒绝权限后操作
    }
}
