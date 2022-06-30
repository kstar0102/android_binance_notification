package com.germeny.binance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textView, xcoin, ycoin, zcoin;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    int x=0, y=0, z=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button= (Button) findViewById(R.id.button);
        textView= (TextView) findViewById(R.id.textView);

        xcoin = (TextView)findViewById(R.id.mainX);
        ycoin = (TextView)findViewById(R.id.mainY);
        zcoin = (TextView)findViewById(R.id.mainZ);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {


                final Handler handler = new Handler();
                final int delay = 15000 ; //1000 milliseconds = 1 sec

                handler.postDelayed(new Runnable(){
                    public void run(){
                        handler.postDelayed(this, delay);

                        final Handler childhandler = new Handler();
                        final int childDelay1 = 3000;
                        final int childDelay2 = 8000;
                        final int childDelay3 = 13000;

                        childhandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Common.getInstance().getBinanceUrl(), null,
                                        new Response.Listener<JSONObject >() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                  xcoin.setText(response.getString("openPrice"));
                                                  String openPricex = response.getString("openPrice");
//                                                    Log.e("string sub", openPricex.substring(0,5));
                                                  x = Integer.parseInt(openPricex.substring(0,5));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // enjoy your error status
                                        Toast.makeText(MainActivity.this, getResources().getString(R.string.offline_text), Toast.LENGTH_LONG).show();
                                    }
                                });

                                queue.add(request);
                            }
                        }, childDelay1);

                        childhandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Common.getInstance().getBinanceUrl(), null,
                                        new Response.Listener<JSONObject >() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                  ycoin.setText(response.getString("openPrice"));
                                                    String openPricey = response.getString("openPrice");
//                                                    String[] str = openPricey.split(".");
                                                    y = Integer.parseInt(openPricey.substring(0,5));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // enjoy your error status
                                        Toast.makeText(MainActivity.this, getResources().getString(R.string.offline_text), Toast.LENGTH_LONG).show();
                                    }
                                });

                                queue.add(request);
                            }
                        }, childDelay2);

                        childhandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Common.getInstance().getBinanceUrl(), null,
                                        new Response.Listener<JSONObject >() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                  zcoin.setText(response.getString("openPrice"));
                                                  String openPricez = response.getString("openPrice");
//                                                    String[] str = openPricez.split(".");
                                                    z = Integer.parseInt(openPricez.substring(0,5));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // enjoy your error status
                                        Toast.makeText(MainActivity.this, getResources().getString(R.string.offline_text), Toast.LENGTH_LONG).show();
                                    }
                                });

                                queue.add(request);
                            }
                        }, childDelay3);

                        int[] difR = new int[3];
                        difR[0] = x-y;
                        difR[1] = y-z;
                        difR[2] = x-z;
                        int max = difR[0];

                        for (int i = 1; i < difR.length; i++) {
                            if (difR[i] > max) {
                                max = difR[i];
                            }
                        }

//                        textView.setText(String.valueOf(max));

                        if(max >= 10){
                            showNotification(String.valueOf(max));
                        }

                        Log.d("TAG", String.valueOf(max));
                    }
                }, delay);
            }
        });
    }

    public String connectBin(){
        final String[] price = {""};
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Common.getInstance().getBinanceUrl(), null,
                new Response.Listener<JSONObject >() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            xcoin.setText(response.getString("openPrice"));
                            price[0] = response.getString("openPrice");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // enjoy your error status
                Toast.makeText(MainActivity.this, getResources().getString(R.string.offline_text), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(request);

        return price[0];
    }

    public void showNotification(String price){
        Intent notificationIntent = new Intent(MainActivity. this, MainActivity. class );
        notificationIntent.addCategory(Intent. CATEGORY_LAUNCHER ) ;
        notificationIntent.setAction(Intent. ACTION_MAIN ) ;
        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP );
        PendingIntent resultIntent = PendingIntent. getActivity (MainActivity. this, 0 , notificationIntent , PendingIntent. FLAG_IMMUTABLE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity. this, default_notification_channel_id )
                .setSmallIcon(R.drawable. ic_launcher_foreground )
                .setContentTitle( "Alert" )
                .setContentIntent(resultIntent)
                .setStyle( new NotificationCompat.InboxStyle())
                .setContentText( "BTC high volatility - Price " + price ) ;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
    }

}