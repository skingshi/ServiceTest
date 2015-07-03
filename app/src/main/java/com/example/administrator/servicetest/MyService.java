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
    private boolean flag = false;
    Handler handler;

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
        final MyBinder b = new MyBinder("1");

        new Thread(new Runnable() {
            int i=0;
            @Override
            public void run() {
                while(handler==null) {
                    handler = b.getHandler();
//                    handler = new Handler();
                }
                while(flag)
                {

                    Message mess = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", "data" + i);
                    mess.setData(bundle);
                    try{
                        Thread.sleep(1000);
                        handler.sendMessage(mess);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    Log.w("+++++++++++++",""+i);
                    i++;
                }
                handler=null;
            }
        }).start();

        return b;
    }

    public class MyBinder extends Binder
    {
        String i;

        Handler handler = null;

        public Handler getHandler() {
            return handler;
        }

        public void setHandler(Handler handler) {
            this.handler = handler;
        }

        public MyBinder(String i) {
            this.i = i;
        }

        public String getI() {
            return i;
        }

        public void setI(String i) {
            this.i = i;
        }
    }

}
