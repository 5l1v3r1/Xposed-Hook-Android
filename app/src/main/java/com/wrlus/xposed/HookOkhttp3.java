package com.wrlus.xposed;

import android.util.Log;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import okhttp3.Request;

public class HookOkhttp3 implements IXposedHookLoadPackage {

    private static final String MODNAME = "wrluXposed.HookOkhttp3";
    private static String TAG = HookOkhttp3.MODNAME;

    @Override
    public void handleLoadPackage(LoadPackageParam loadPackageParam) {
        TAG = HookOkhttp3.MODNAME + ":" + loadPackageParam.packageName;
        try {
            // Hook newCall Method to print okhttp 3 request.
            Log.i(TAG, "Hooking build method");
            XposedHelpers.findAndHookMethod(Request.Builder.class,
                    "build", new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                            if (XposedHelpers.getObjectField(param.thisObject, "url") != null) {
                                return Request.class.getDeclaredConstructor(Request.Builder.class).newInstance(param.thisObject);
                            }
                            throw new IllegalStateException("url == null");
                        }
                    }
            );
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

}
