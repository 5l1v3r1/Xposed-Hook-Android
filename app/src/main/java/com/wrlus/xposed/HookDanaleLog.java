package com.wrlus.xposed;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class HookDanaleLog implements IXposedHookLoadPackage {

    private static final String MODNAME = "wrluXposed.HookDanaleLog";
    private static String TAG = HookDanaleLog.MODNAME;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        TAG = HookDanaleLog.MODNAME + ":" + loadPackageParam.packageName;
        if (loadPackageParam.packageName.equals("com.huawei.ipc") || loadPackageParam.packageName.equals("com.huawei.ipc_honor")) {
            try {
                // Hook isDebugApk method to enable debug mode.
                Log.i(TAG, "Hooking isDebug method");
                findAndHookMethod("com.danale.sdk.utils.LogUtil",
                        loadPackageParam.classLoader,
                        "isDebug", new XC_MethodReplacement() {
                            @Override
                            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                                return true;
                            }
                        });
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }
}
