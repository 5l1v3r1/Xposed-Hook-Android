/**
 * Copyright (c) 2018, wrlu.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms and conditions of the GNU General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package cn.xiaolus.xpmodule;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wrlus.xposed.helper.LogHelperService;

/**
 * Created by wrlu on 2018/1/18.
 */
public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogHelperService bridgeService = ((LogHelperService.ServiceBinder)service).getService();
            TextView logTextView = new TextView(MainActivity.this);
            logTextView.setText(String.valueOf(bridgeService.test()));
            Toast.makeText(MainActivity.this, logTextView.getText(), Toast.LENGTH_SHORT).show();
            linearLayout.addView(logTextView);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MainActivity", "Disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.linear_main);
        TextView welcome = new TextView(this);
        welcome.setText("应用程序已启动");
        Button refreshButton = findViewById(R.id.btnRefresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogHelperService.class);
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
            }
        });
        Button clearButton = findViewById(R.id.btnClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeAllViews();
            }
        });
        linearLayout.addView(welcome);
    }
}
