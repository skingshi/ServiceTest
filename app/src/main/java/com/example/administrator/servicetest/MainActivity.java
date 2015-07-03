package com.example.administrator.servicetest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;




public class MainActivity extends Activity implements View.OnClickListener,ServiceConnection{

    public  Intent service;
    public TextView tv;
    public Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startServiceBtn = (Button)findViewById(R.id.startBtn);
        Button stopServiceBtn = (Button)findViewById(R.id.stopBtn);
        Button bindServiceBtn = (Button)findViewById(R.id.bindBtn);
        Button unbindServiceBtn = (Button)findViewById(R.id.unBindBtn);
        tv = (TextView)findViewById(R.id.tv);
        service = new Intent(this,MyService.class);

        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String txt = bundle.get("data").toString();
                tv.setText(txt);
            }
        };





        startServiceBtn.setOnClickListener(this);
        stopServiceBtn.setOnClickListener(this);
        bindServiceBtn.setOnClickListener(this);
        unbindServiceBtn.setOnClickListener(this);

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {


        Log.w("+++++++++++++", "service connect");
        ((MyService.MyBinder)service).setCallback(new MyService.Callback() {
            @Override
            public void sendMess(int i) {

                Message mess = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("data", "data" + i);
                mess.setData(bundle);
                try {
                    Thread.sleep(1000);
                    handler.sendMessage(mess);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.w("+++++++++++++", "" + i);
            }
        });




    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.w("+++++++++++++","service disconnect");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.startBtn:
                startService(service);
                break;
            case R.id.stopBtn:
                stopService(service);
                break;
            case R.id.bindBtn:
                bindService(service, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.unBindBtn:
                unbindService(this);
                break;
        }
    }
}
