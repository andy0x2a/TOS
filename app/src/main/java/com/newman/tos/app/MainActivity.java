package com.newman.tos.app;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends ActionBarActivity {


    private Intent service;
    private boolean isServiceActivated = false;

    private void updateUI(Intent intent) {
        Log.i("MAIN ACTIVITY", "Update UI");
        String status = intent.getStringExtra("status");
        if (status != null) {
            Toast.makeText(getApplicationContext(), " Greeted Broadcast !" + status, Toast.LENGTH_LONG).show();
            Log.i("Main activity", status);
        } else {
            Toast.makeText(getApplicationContext(), " Null greet ID!" + status, Toast.LENGTH_LONG).show();
        }


    }

//    @Override
//       protected void onStop() {
//        super.onStop();
//        unregisterReceiver(broadcastReceiver);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        registerReceiver(broadcastReceiver, new IntentFilter(MyService.BROADCAST_ACTION));
    }

    @Override
    protected void onStart() {
        super.onStart();
//        new HttpRequestTask(getApplicationContext()).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
//            HttpRequestTask httpRequestTask = new HttpRequestTask(getApplicationContext());
//            httpRequestTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Start the  service
    public void startNewService(View view) {

        Log.i("MainActivity", "Start New Service button Clicked?");
        if (view.getId() == R.id.button1) {
            Log.i("MainActivity", "button1");
            if (!isServiceActivated) {
                service = new Intent(getApplicationContext(), MyService.class);
                startService(service);
                isServiceActivated = true;

            } else {
                Log.i("MainActivity", "Service already started");
            }

        }
    }

    // Stop the  service
    public void stopNewService(View view) {


        Log.i("MainActivity", "STOP New Service button Clicked?");
        if (view.getId() == R.id.button2) {
            Log.i("MainActivity", "button2");

            service.putExtra("shutdown", true);
            stopService(service);



        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };


}
