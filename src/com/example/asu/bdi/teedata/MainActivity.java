package com.example.asu.bdi.teedata;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import com.example.asu.bdi.constant.Constant;
import com.example.asu.bdi.log.WriteCsv;

public class MainActivity extends Activity {

	private StringBuffer folderPath;
	private String path;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (WriteCsv.hasSdcard() == true) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath();
			folderPath = new StringBuffer(path).append(Constant.RelPath);
			Log.d("folderPath", folderPath.toString());
			createDirectoryIfNeeded(folderPath.toString());
		}
		else {
			path = this.getApplication().getFilesDir() + Constant.RelPath;
			Log.d("path", path);
			createDirectoryIfNeeded(path);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void disAllDev(View view) {
		Intent intent = new Intent(this, DisAllDeviceActivity.class);
		startActivity(intent);
	}

	private void createDirectoryIfNeeded(String directoryName) {
		File theDir = new File(directoryName);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + directoryName);
			theDir.mkdir();
		}
	}
}
