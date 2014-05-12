package com.newman.tos.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by anewman on 5/12/2014.
 */
public class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
    private final Context applicationContext;


    public HttpRequestTask(Context applicationContext) {
        this.applicationContext = applicationContext;

    }

    @Override
    protected Greeting doInBackground(Void... params) {

//            PowerManager pm = (PowerManager) applicationContext.getSystemService(Context.POWER_SERVICE);
//            PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
//            wakeLock.acquire();
//            KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
//            KeyguardManager.KeyguardLock keyguardLock =  keyguardManager.newKeyguardLock("TAG");
//            keyguardLock.disableKeyguard();
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            wakeLock.release();


        try {
            final String url = "http://rest-service.guides.spring.io/greeting";
            RestTemplate restTemplate = new RestTemplate();
            MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

            restTemplate.getMessageConverters().add(messageConverter);
            Greeting greeting = restTemplate.getForObject(url, Greeting.class);
            return greeting;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Greeting greeting) {


        PowerManager pm = (PowerManager) applicationContext.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        wakeLock.acquire();
        Log.i("HttpRequestTask", "Greeted " + greeting.getId());
        Intent intent = new Intent();
        intent.setAction(MyService.BROADCAST_ACTION);
        intent.putExtra("grtId", greeting.getId());
        this.applicationContext.sendBroadcast(intent);

//        Toast.makeText(this.applicationContext, " Greeted!" + greeting.getId(), Toast.LENGTH_LONG).show();
    }


}
