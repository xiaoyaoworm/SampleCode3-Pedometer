package com.example.asu.bdi.teedata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisAllDeviceActivity extends Activity {

	private SensorManager mSensorManager;
	private List<Sensor> mSensorList = new ArrayList<Sensor>();
	private Sensor accSensor, preSensor, gyrSensor;

	private TextView textView1, textView2;
	private Button button;
	private String allDev;
	private StringBuffer allDevInfo;

	private String suggestion;
	private StringBuffer suggInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dis_all_dev);

		// display all the devices in it.
		allDev = disAllDev();
		// check whether it can run our monitor program
		suggestion = checkSuggestion();

		textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText(allDev);

		if (suggestion != null && !suggestion.equals("")) {
			textView2 = (TextView) findViewById(R.id.textView2);
			textView2.setText(suggestion);
			button = (Button) findViewById(R.id.button1);
			button.setEnabled(false);
		} 

	}

	// display all the devices in it.
	private String disAllDev() {
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		allDevInfo = new StringBuffer();
		Iterator<Sensor> iter = mSensorList.iterator();
		while (iter.hasNext()) {
			allDevInfo.append(iter.next().getName() + "\n");
		}

		allDev = allDevInfo.toString();

		return allDev;
	}

	// check whether it can run our monitor program
	private String checkSuggestion() {
		suggInfo = new StringBuffer();
		accSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		preSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
		gyrSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

		if (accSensor == null) {
			suggInfo.append("This device doesn't have the accelerometer sensor.\n");
		}
		else if (preSensor == null) {
			suggInfo.append("This device doesn't have the pressure sensor.\n");
		}
		else if (gyrSensor == null) {
			suggInfo.append("This device doesn't have the gyroscope sensor.\n");
		}

		suggestion = suggInfo.toString();
		return suggestion;
	}

	public void getAll(View view) {
		Intent intent = new Intent(this, AccPreGyrActivity.class);
		startActivity(intent);
	}
}
