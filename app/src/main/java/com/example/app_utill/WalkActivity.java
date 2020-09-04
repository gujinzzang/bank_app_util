package com.example.app_utill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import static android.content.Intent.ACTION_TIME_CHANGED;

public class WalkActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepCountSensor;
    private int mCounterSteps = 0;
    private TimeReceiver timeReceiver;
    TextView walknum;
    TextView date;
    WebView wv_main;
    //현재 걸음 수
    private int mSteps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        walknum = findViewById(R.id.walknum);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepCountSensor == null) {
            Toast.makeText(this, "No step detect sensor", Toast.LENGTH_SHORT).show();
        }

        long now = System.currentTimeMillis();
        Date time = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd, hh:mm");
        date = findViewById(R.id.date);
        String getTime = simpleDate.format(time);
        date.setText(getTime);

        wv_main = (WebView) findViewById(R.id.wv_main);
        WebSettings webSettings = wv_main.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wv_main.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wv_main.loadUrl("https://www.daum.net");

        timeReceiver = new TimeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_TIME_CHANGED);
        this.registerReceiver(timeReceiver,intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, stepCountSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        unregisterReceiver(timeReceiver);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {

            //stepcountsenersor는 앱이 꺼지더라도 초기화 되지않는다. 그러므로 우리는 초기값을 가지고 있어야한다.
//            if () {
//                // initial value
//                mCounterSteps = (int) sensorEvent.values[0];
//            }
            //리셋 안된 값 + 현재값 - 리셋 안된 값
            mSteps = (int) sensorEvent.values[0] - mCounterSteps;
            walknum.setText("걸음수 : " + Integer.toString(mSteps));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}