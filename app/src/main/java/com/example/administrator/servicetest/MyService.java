package com.example.administrator.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class MyService extends Service {

    public interface Callback
    {
        public void sendMess(int i);
    }

    public class MyBinder extends Binder
    {
        String i;
        MyService.Callback callback = null;

        public MyBinder() {

        }

        public MyBinder(String i, MyService.Callback callback) {
            this.i = i;
            this.callback = callback;
        }

        public Callback getCallback() {
            return callback;
        }

        public void setCallback(Callback callback) {
            this.callback = callback;
        }

        public String getI() {
            return i;
        }

        public void setI(String i) {
            this.i = i;
        }
    }

    private boolean flag = false;


    public MyService() {
    }


    @Override
    public void onCreate() {
        Log.w("+++++++++++++", "service OnCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        flag = false;

        Log.w("+++++++++++++","service OnDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.w("+++++++++++++","service unbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.w("+++++++++++++","service rebind");
        super.onRebind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w("+++++++++++++","start");
        flag = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(flag)
                {
                  Log.w("++++++","你好");
                    try{
                        Thread.sleep(1000);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.w("+++++++++++++","service onBind");
        flag = true;
        final MyBinder b = new MyBinder();

        new Thread(new Runnable() {
            int i=0;
            @Override
            public void run() {

                while(true) {
                    if (b.getCallback() != null)
                        break;
                }
                while(flag)
                {
                    b.getCallback().sendMess(i);
                    i++;
                }
                b.setCallback(null);
            }
        }).start();

        return b;
    }




}
