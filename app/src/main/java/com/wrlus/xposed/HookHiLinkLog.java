package com.wrlus.xposed;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class HookHiLinkLog implements IXposedHookLoadPackage {

    private static final String MODNAME = "wrluXposed.HookHiLinkLog";
    private static String TAG = HookHiLinkLog.MODNAME;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        TAG = HookHiLinkLog.MODNAME + ":" + loadPackageParam.packageName;
        if (loadPackageParam.packageName.equals("com.huawei.smartspeaker")) {
            try {
                // Hook isDebugApk method to enable debug mode.
                Log.i(TAG, "Hooking isDebugApk method");
                findAndHookMethod("com.huawei.hilink.common.log.LogUtil",
                        loadPackageParam.classLoader,
                        "isDebugApk", new XC_MethodReplacement() {
                            @Override
                            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                                return true;
                            }
                        });

                // Hook isDebugStatus method to enable debug mode.
                Log.i(TAG, "Hooking isDebugStatus method");
                findAndHookMethod("com.huawei.hilink.common.log.LogUtil",
                        loadPackageParam.classLoader,
                        "isDebugStatus", new XC_MethodReplacement() {
                            @Override
                            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                                return true;
                            }
                        });

                // Hook isDiagnosisStatus Method to enable diagnosis mode.
                Log.i(TAG, "Hooking isDiagnosisStatus method");
                findAndHookMethod("com.huawei.hilink.common.log.LogUtil",
                        loadPackageParam.classLoader,
                        "isDiagnosisStatus", new XC_MethodReplacement() {
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
