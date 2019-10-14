package com.wrlus.xposed;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class HookHealthSn implements IXposedHookLoadPackage {
    private static final String MODNAME = "wrluXposed.HookHealthSn";
    private static String TAG = HookHealthSn.MODNAME;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        TAG = HookHealthSn.MODNAME + ":" + loadPackageParam.packageName;
        if (loadPackageParam.packageName.startsWith("com.huawei.health")) {
            try {
                Log.i(TAG, "Hooking o.byr.l method");
                findAndHookMethod("o.byr",
                        loadPackageParam.classLoader,
                        "l", new XC_MethodReplacement() {
                            @Override
                            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                                return "M0VQMD";
                            }
                        });
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

    }
}
