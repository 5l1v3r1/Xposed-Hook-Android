package com.wrlus.xposed;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class HookDanaleRxHelper implements IXposedHookLoadPackage {

    private static final String MODNAME = "wrluXposed.HookDanaleRxHelper";
    private static String TAG = HookDanaleRxHelper.MODNAME;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        TAG = HookDanaleRxHelper.MODNAME + ":" + lpparam.packageName;
        if (lpparam.packageName.equals("com.huawei.ipc") || lpparam.packageName.equals("com.huawei.ipc_honor")) {
            try {
                // Hook _call method to show traffic to the danale device.
                Log.i(TAG, "Hooking _call method");
                final String basePkg = "com.danale.sdk.device";
                findAndHookMethod(basePkg + ".util.RxHelper",
                        lpparam.classLoader,
                        "_call",
                        Class.class,
                        basePkg + ".bean.CmdDeviceInfo",
                        basePkg + ".service.BaseCmdRequest",
                        basePkg + ".service.BaseCmdResponse",
                        new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                Log.i(TAG, "cls: " + param.args[0]);
                                Log.i(TAG, "cmdDeviceInfo: " + param.args[1]);
                                Log.i(TAG, "baseCmdRequest: " + param.args[2]);
                                Log.i(TAG, "baseCmdResponse: " + param.args[3]);
                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Log.i(TAG, "_call returns: "+param.getResult());
                            }
                        });
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }
}
