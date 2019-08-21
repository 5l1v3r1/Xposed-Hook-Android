package com.wrlus.xposed.helper;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class LogHelperService extends Service {
    private ServiceBinder binder = new ServiceBinder();
    private int count = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        count++;
        return super.onStartCommand(intent, flags, startId);
    }

    public int test() {
        Log.d("LogHelperService", String.valueOf(++count));
        return count;
    }

    public class ServiceBinder extends Binder {
        public LogHelperService getService() {
            return LogHelperService.this;
        }
    }
}
