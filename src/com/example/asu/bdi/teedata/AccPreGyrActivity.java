package com.example.asu.bdi.teedata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.asu.bdi.algorithm.FloorAlgo;
import com.example.asu.bdi.algorithm.StepAlgo;
import com.example.asu.bdi.common.CommonMethod;
import com.example.asu.bdi.common.FloorReturnObj;
import com.example.asu.bdi.common.StepReturnObj;
import com.example.asu.bdi.constant.Constant;
import com.example.asu.bdi.log.WriteCsv;

public class AccPreGyrActivity extends Activity implements SensorEventListener {

	private SensorManager mSensorManager;
	private SensorEventListener mSensorListener;

	// the view of the App
	private Button btnStart;
	private Button btnStop;
	private TextView textView1;
	private TextView textView2;
//	private TextView textView3;

	// this is the flag reflects the button's changing
	private boolean onOff = false;

	// the data needed in log
	private WriteCsv writeCsv = new WriteCsv();
	private StringBuffer oneRecord = new StringBuffer();
	private String path;
	private String time;
	private String fileName;

	// the data needed in both calculation
	private int count = 0;

	// the data needed in step calculation
	private StepAlgo stepAlgo = new StepAlgo();
	private CommonMethod commonMethod = new CommonMethod();
	private String[] pressureSecond = new String[2];
	private StepReturnObj returnStepObj;
	private StepReturnObj inputStepObj = new StepReturnObj();
	private int max, min, steps;
	private List<Float> acceList = new ArrayList<Float>();

	// the data needed in floor calculation
	private FloorAlgo floorAlgo = new FloorAlgo();
	private int floor;
	private float altitude;
	private float changeAlti;
	private FloorReturnObj returnFloorObj;
	private FloorReturnObj inputFloorObj = new FloorReturnObj();
	private List<Float> altiList = new ArrayList<Float>();
	private List<Float> avgAltiList = new ArrayList<Float>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acc_pre_tem);

		btnStart = (Button) findViewById(R.id.button1);
		btnStop = (Button) findViewById(R.id.button2);
	}

	// when click on BtnStart, the sensor will be listening...
	public void clickBtnStart(View view) {
		// initialize inputStepObj
		inputStepObj.m = 0;
		inputStepObj.max = 0;
		inputStepObj.min = 0;
		inputStepObj.recList = acceList;
		inputStepObj.steps = 0;

		// initialize inputFloorObj
		inputFloorObj.floor = 0;
		inputFloorObj.pressure = 0;
		inputFloorObj.altitude = 0;
		inputFloorObj.altiList = altiList;
		inputFloorObj.avgAltiList = avgAltiList;
		inputFloorObj.standardAlti = 0;

		fileName = commonMethod.chanDateFormat(Calendar.getInstance()
				.getTimeInMillis());
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorListener = new SensorEventListener() {

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSensorChanged(SensorEvent event) {
				Sensor sensor = event.sensor;
				while (count == 2) {
					oneRecord = new StringBuffer();
					count = 0;
				}
				if (sensor.getType() == Sensor.TYPE_PRESSURE && count == 0) {
					float x = event.values[0];
					long timeInMillis = (long) ((new Date()).getTime() + (event.timestamp - System
							.nanoTime()) / 1000000L);
					time = commonMethod.chanDateFormat(timeInMillis);
					oneRecord = oneRecord.append(time + "," + x);
					count++;

					returnFloorObj = floorAlgo.calFloorProcess(x,
							inputFloorObj.standardAlti, inputFloorObj.altiList,
							inputFloorObj.avgAltiList,
							inputFloorObj.changeAlti, steps,
							inputFloorObj.floor);

					inputFloorObj.standardAlti = returnFloorObj.standardAlti;
					inputFloorObj.altiList = returnFloorObj.altiList;
					inputFloorObj.avgAltiList = returnFloorObj.avgAltiList;
					inputFloorObj.changeAlti = returnFloorObj.changeAlti;
					inputFloorObj.floor = returnFloorObj.floor;

					floor = returnFloorObj.floor;
					altitude = returnFloorObj.altitude;
					changeAlti = returnFloorObj.changeAlti;

					textView1 = (TextView) findViewById(R.id.textView1);
					textView1.setText("At the time of " + time
							+ " ,\nthe Atmospheric pressure is: " + x
							+ " hPa\n" + "Floor number:" + floor + "\n"
							+ "changeAlti = " + changeAlti);

					oneRecord.append("," + changeAlti + ","
							+ floor);

				}
				else if (sensor.getType() == Sensor.TYPE_ACCELEROMETER
						&& count == 1) {
					float x = event.values[0];
					float y = event.values[1];
					float z = event.values[2];
					long timeInMillis = (long) ((new Date()).getTime() + (event.timestamp - System
							.nanoTime()) / 1000000L);
					time = commonMethod.chanDateFormat(timeInMillis);

					oneRecord.append("," + x + "," + y + "," + z);
					count++;

					pressureSecond = oneRecord.toString().split(",");
					float pressure = Float.valueOf(pressureSecond[1]);

					returnStepObj = stepAlgo.calStepProcess(pressure, x, y, z,
							inputStepObj.recList, inputStepObj.m,
							inputStepObj.n, inputStepObj.max, inputStepObj.min);

					inputStepObj.recList = returnStepObj.recList;
					inputStepObj.m = returnStepObj.m;
					inputStepObj.n = returnStepObj.n;
					inputStepObj.max = returnStepObj.max;
					inputStepObj.min = returnStepObj.min;

					max = returnStepObj.max;
					min = returnStepObj.min;

					steps = Math.min(max, min);

					textView2 = (TextView) findViewById(R.id.textView2);
					textView2.setText("At the time of " + time
							+ " ,\nthe data of Accelerometor:\nx = " + x
							+ " m/(s^2)\ny = " + y + " m/(s^2)\nz = " + z
							+ " m/(s^2)\n\n" + "Step number:" + steps + "\n");

					oneRecord.append("," + steps);
//				}
//				else if (sensor.getType() == Sensor.TYPE_GYROSCOPE
//						&& count == 2) {
//					float x = event.values[0];
//					float y = event.values[1];
//					float z = event.values[2];
//					long timeInMillis = (long) ((new Date()).getTime() + (event.timestamp - System
//							.nanoTime()) / 1000000L);
//					time = commonMethod.chanDateFormat(timeInMillis);
//					textView3 = (TextView) findViewById(R.id.textView3);
//					textView3.setText("At the time of " + time
//							+ " ,\nthe data of Gyroscope:\nx = " + x
//							+ " radians/second\ny = " + y
//							+ " radians/second\nz = " + z
//							+ " radians/second\n\n");
//					oneRecord = oneRecord.append("," + x + "," + y + "," + z);
//					count++;

					if (WriteCsv.hasSdcard() == true) {
						path = Constant.SdPath;
					}
					writeCsv.appendToFile(path + "/" + fileName + ".csv",
							oneRecord.toString());
				}
			}

		};
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
				SensorManager.SENSOR_DELAY_FASTEST);
//		mSensorManager.registerListener(mSensorListener,
//				mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
//				SensorManager.SENSOR_DELAY_FASTEST);

		updateButton(!onOff);
	}

	// when click on BtnStop, the Accelerometer sensor will be closed...
	public void clickBtnStop(View view) {

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		mSensorManager.unregisterListener(mSensorListener);

		steps = 0;
		floor = 0;
		altitude = 0;
		changeAlti = 0;

		updateButton(onOff);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_acc_pre_tem, menu);
		return true;
	}

	// the function of change button's status
	public void updateButton(boolean onOff) {
		btnStart.setEnabled(!onOff);
		btnStop.setEnabled(onOff);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {

	}

}
