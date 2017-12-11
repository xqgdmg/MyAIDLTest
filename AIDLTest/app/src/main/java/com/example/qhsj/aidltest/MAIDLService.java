package com.example.qhsj.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by Chris on 2017/12/11.
 *
 */
public class MAIDLService extends Service {

    /*
     * 返回 aidl接口
     */
    public IBinder onBind(Intent t) {
        Log("service on bind");
        return mBinder;
    }

    /*
     * aidl接口
     */
    IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
        @Override
        public int calculation(int anInt, int bnInt) throws RemoteException {
            Log("anInt=" + anInt + "---" + "bnInt=" + bnInt);
            return anInt + bnInt;
        }
    };

    /********************************************** 以下只是打印 **********************************************************/
    private void Log(String str) {
        Log.e("chris", "----------" + str + "----------");
    }

    public void onCreate() {
        Log("service created");
    }

    public void onStart(Intent intent, int startId) {
        Log("service started id = " + startId);
    }

    public void onDestroy() {
        Log("service on destroy");
        super.onDestroy();
    }

    public boolean onUnbind(Intent intent) {
        Log("service on unbind");
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent) {
        Log("service on rebind");
        super.onRebind(intent);
    }

}
