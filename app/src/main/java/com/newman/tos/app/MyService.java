package com.newman.tos.app;

/**
 * Created by anewman on 5/12/2014.
 */

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends
        Service {
//        IntentService {


    private boolean isStarted = false;
    private boolean receivedShutdown = false;

    public static final String BROADCAST_ACTION = "com.newman.tos.app.myservice.broadcastaction";
    private final BooleHolder boolHolder = new BooleHolder();


    public MyService() {

    }


    private void doService(final Intent intent, int startId) {


        Log.i("MyService", "called, state = " + isStarted);

        if (!isStarted) {
            new HttpRequestTask(getApplicationContext(), boolHolder).execute();
            Log.i("MyService", "Started new HRT");
            isStarted = true;
        }


    }


    public class BooleHolder {

        Boolean isActive = true;

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }


    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart(final Intent intent, int startId) {
        doService(intent, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

    }
}