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
//        Service {
        IntentService {


    private boolean isStarted = false;
    private boolean receivedShutdown = false;

    public static final String BROADCAST_ACTION = "com.newman.tos.app.myservice.broadcastaction";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyService(String name) {
        super(name);
    }

    public MyService() {
        super(null);
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        doService(intent, 0);

    }


    private void doService(final Intent intent, int startId) {

        Log.i("MyService", "starting runnable");
        Runnable r = new Runnable() {
            public void run() {

                Log.i("MyService", "sleeping");
                int ctr = 0;
                while (ctr++ < 10) {
                    Log.i("MyService", "ctr " + ctr);
                    boolean shutdown = intent.getBooleanExtra("shutdown", false);
                    Log.i("Runnable:" , "..." + shutdown);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Runnable", "waking");
                stopSelf();
            }
        };

        Thread t = new Thread(r);
           t.run();


        Log.i("MyService", "called, state = " + isStarted);

        if (!isStarted) {
            new HttpRequestTask(getApplicationContext()).execute();
            Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();
            isStarted = true;
        }


    }

    public boolean isReceivedShutdown() {
        return receivedShutdown;
    }

    public void setReceivedShutdown(boolean receivedShutdown) {
        this.receivedShutdown = receivedShutdown;
    }



   /*  Regular service code, not IntentService
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

    }*/
}