package com.example.qhsj.aidlclienttest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.qhsj.aidltest.IMyAidlInterface;


public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface service;

    /*
     * 第一步 先创建一个ServiceConnection 对象
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.e("chris", "onServiceDisconnected---" + arg0.getPackageName());
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.e("chris", "onServiceConnected:" + name.getPackageName());
            // 获取远程Service的onBinder方法返回的对象代理
            service = IMyAidlInterface.Stub.asInterface(binder);
        }
    };
    private TextView tvResult;
    private TextView tvTestAIDL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        // 第二步 绑定
        toBindService(); // 要在点击事件之前绑定，不然你需要点击两次，才有成功

        initListen();

    }

    private void toBindService() {
        //使用意图对象绑定开启服务
        Intent intent = new Intent();
        //在5.0及以上版本必须要加上这个
        intent.setPackage("com.example.qhsj.aidltest");
        intent.setAction("com.example.qhsj.aidltest.MAIDLService");//这个是上面service的action
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initListen() {
        tvTestAIDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testAIDL();
            }
        });
    }

    private void initView() {
        tvTestAIDL = (TextView) findViewById(R.id.tvTestAIDL);
        tvResult = (TextView) findViewById(R.id.tvResult);
    }

    private void testAIDL() {

         // 第三步 调用
        if(service != null){
            int calculation = 0;
            try {
                calculation = service.calculation(1, 2);
            } catch (RemoteException e) {
                Log.e("chris","RemoteException=" + e.getCause());
                e.printStackTrace();
            }
            tvResult.setText("计算结果="+calculation);
        }
    }

    /*
     * 第四步 不用的时候解除绑定
     */
    @Override
    protected void onDestroy () {
        super.onDestroy();
        if (mServiceConnection != null) {
            unbindService(mServiceConnection);
        }
    }

}
