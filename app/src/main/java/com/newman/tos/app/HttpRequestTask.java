package com.newman.tos.app;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import static java.lang.Thread.*;

/**
 * Created by anewman on 5/12/2014.
 */
public class HttpRequestTask extends AsyncTask<Void, Void, TOSResponse> {
    private final Context applicationContext;
    private MyService.BooleHolder boolHolder;
    private boolean isActive = true;


    public HttpRequestTask(Context applicationContext, MyService.BooleHolder boolHolder) {
        this.applicationContext = applicationContext;

        this.boolHolder = boolHolder;
    }


    @Override
    protected TOSResponse doInBackground(Void... params) {


//        while(this.boolHolder.getIsActive() == true) {
        while ("A".equals("A")) {

            try {
                sleep(10000);

                Log.i("HttpRequestTask", "slept for 10s ");

                final String url = "http://tos-env.elasticbeanstalk.com/tos/check";
                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

                restTemplate.getMessageConverters().add(messageConverter);
                TOSResponse tosResponse = restTemplate.getForObject(url, TOSResponse.class);
                alert(tosResponse);
//                return tosResponse;
            }catch(NumberFormatException e) {

            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

    }


    Log.i("HttpRequestTask","ending");
    return null;
}


    void alert(TOSResponse tosResponse) {


        Log.i("HttpRequestTask", "Response " + tosResponse.getStatus());


        if ("ACTIVATE".equals(tosResponse.getStatus())) ;
        {


            PowerManager pm = (PowerManager) applicationContext.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
            wakeLock.acquire();

            KeyguardManager keyguardManager = (KeyguardManager) applicationContext.getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
            keyguardLock.disableKeyguard();

//            Intent intent = new Intent();
//            intent.setAction(MyService.BROADCAST_ACTION);
//            intent.putExtra("status", tosResponse.getStatus());
//            this.applicationContext.sendBroadcast(intent);

        }
    }


}
