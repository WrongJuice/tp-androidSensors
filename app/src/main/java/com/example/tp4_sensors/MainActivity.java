package com.example.tp4_sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager = null;
    private Sensor mAccelerometer = null;
    private Sensor mMagnetometer = null;
    private float[] mAccelerometerValues, mMagnetometerValues;

    final SensorEventListener mAccelerometerEventListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Que faire en cas de changement de précision ?
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            // Que faire en cas d'évènements sur le capteur ?
            mAccelerometerValues = sensorEvent.values;
            valuesChanged();
        }
    };

    final SensorEventListener mMagnetometerEventListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Que faire en cas de changement de précision ?
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            // Que faire en cas d'évènements sur le capteur ?
            mMagnetometerValues = sensorEvent.values;
            valuesChanged();
        }
    };

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if(mAccelerometer != null){
            System.out.println("Il y a au moins un accéléromètre");
            // Il y a au moins un accéléromètre
        }else {
            System.out.println("Il n'y a pas d'accéléromètre");
            // Il n'y en a pas
        }

        if(mMagnetometer != null){
            System.out.println("Il y a au moins un magnétometre");
            // Il y a au moins un magnétometre
        }else {
            System.out.println("Il n'y a pas de magnétometre");
            // Il n'y en a pas
        }

        mSensorManager.registerListener(mAccelerometerEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(mMagnetometerEventListener, mMagnetometer, SensorManager.SENSOR_DELAY_UI);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mAccelerometerEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(mMagnetometerEventListener, mMagnetometer, SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mAccelerometerEventListener, mAccelerometer);
        mSensorManager.unregisterListener(mMagnetometerEventListener, mMagnetometer);
    }

    public void valuesChanged(){
        float[] values = new float[3];
        float[] R = new float[9];

        if(mAccelerometerValues != null && mMagnetometerValues != null){
            SensorManager.getRotationMatrix(R, null, mAccelerometerValues, mMagnetometerValues);
            SensorManager.getOrientation(R, values);

            Log.d("Sensors", "Rotation sur l'axe z : " + values[0]);
            Log.d("Sensors", "Rotation sur l'axe x : " + values[1]);
            Log.d("Sensors", "Rotation sur l'axe y : " + values[2]);

        }

    }

}