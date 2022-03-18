package com.t19emccs114.appmobilicis;

import static android.content.Context.SENSOR_SERVICE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import static android.hardware.Sensor.TYPE_LIGHT;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener,SensorEventListener{

    double latitude, longitude, pressure,accelerometer,gyroscope,lightsensor;
    TextView tvlongitude, tvlatitude, tvbarometer,tvaccelerometer,tvgyroscope,tvlightsensor;
    LocationManager locationManager;
    SensorManager sensorManager;
    Sensor pressureSensor,accelerometerSensor,ambientLightSensor,gyroscopeSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvlongitude = findViewById(R.id.tvlongitude);
        tvlatitude = findViewById(R.id.tvlatitude);
        tvbarometer = findViewById(R.id.tvbarometer);
        tvaccelerometer=findViewById(R.id.tvaccelerometer);

        tvgyroscope=findViewById(R.id.tvgyroscope);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor= sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        ambientLightSensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


        SensorEventListener pressurelistener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                pressure = event.values[0];
                tvbarometer.setText(String.valueOf(pressure));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        SensorEventListener accelerometerListener= new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                accelerometer= event.values[0];
                tvaccelerometer.setText(String.valueOf(accelerometer));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        SensorEventListener gyroscopeListener= new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                gyroscope= event.values[0]+ event.values[1] +event.values[2];
                tvgyroscope.setText(String.valueOf(gyroscope));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        SensorEventListener ambientlightListener= new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                gyroscope= event.values[0];
                tvgyroscope.setText(String.valueOf(gyroscope));

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(pressurelistener,pressureSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(accelerometerListener,accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(gyroscopeListener,gyroscopeSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,ambientLightSensor,SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        tvlatitude.setText(String.valueOf(latitude));
        tvlongitude.setText(String.valueOf(longitude));
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()== TYPE_LIGHT){
            ((TextView)findViewById(R.id.tvlightsensor)).setText(""+event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

