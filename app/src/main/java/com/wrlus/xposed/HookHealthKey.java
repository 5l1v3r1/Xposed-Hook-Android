package com.wrlus.xposed;

import android.util.Log;

import org.bouncycastle.util.encoders.Hex;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class HookHealthKey implements IXposedHookLoadPackage {
    private static final String MODNAME = "wrluXposed.HookHealthKey";
    private static String TAG = HookHealthKey.MODNAME;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        TAG = HookHealthKey.MODNAME + ":" + loadPackageParam.packageName;
        if (loadPackageParam.packageName.startsWith("com.huawei.health")) {
            try {
                Log.i(TAG, "Hooking o.byp.a method");
                findAndHookMethod("o.byp",
                        loadPackageParam.classLoader,
                        "a",
                        byte[].class, byte[].class, byte[].class,
                        new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                byte[] result = (byte[]) param.args[2];
                                Log.i(TAG, "MAC Address: "+ new String(result));
                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                byte[] result = (byte[]) param.getResult();
                                Log.i(TAG, "K2: "+ Hex.toHexString(result));
                            }
                        });
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

    }
}
