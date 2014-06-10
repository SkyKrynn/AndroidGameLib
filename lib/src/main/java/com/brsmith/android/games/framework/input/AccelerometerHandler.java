package com.brsmith.android.games.framework.input;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

//
// SensorSimulator is supposed to allow me to use the provided application
//   to simulate moving the phone around (the accelerometer), plus other stuff
//   Could not get it to work.  Here is a reference to the wiki page for it.
//   May try to get it working at some point.
//
// https://code.google.com/p/openintents/wiki/SensorSimulator
//
//
//import org.openintents.sensorsimulator.hardware.Sensor;
//import org.openintents.sensorsimulator.hardware.SensorEvent;
//import org.openintents.sensorsimulator.hardware.SensorEventListener;
//import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

public class AccelerometerHandler implements SensorEventListener
{
	float accelX;
	float accelY;
	float accelZ;
	
	public AccelerometerHandler(Context context)
	{
		accelX = 0;
		accelY = 0;
		accelZ = 0;
		
		SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		if(manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0)
		{
			Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		}

//        SensorManagerSimulator manager = SensorManagerSimulator.getSystemService(context, Context.SENSOR_SERVICE);
//        manager.connectSimulator();
//        Sensor accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) 
	{
		// Do nothing
	}

	public void onSensorChanged(SensorEvent event) 
	{
		accelX = event.values[0];
		accelY = event.values[1];
		accelZ = event.values[2];
	}
	
	public float getAccelX() { return accelX; }
	public float getAccelY() { return accelY; }
	public float getAccelZ() { return accelZ; }

}
