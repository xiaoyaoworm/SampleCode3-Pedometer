package com.example.asu.bdi.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import android.os.Environment;

public class WriteCsv {

	// check whether SD card exist
	public static boolean hasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// write Log part
	public void appendToFile(String filePath, String data) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath,
					true))); // true means: "append"
			pw.println(data);
		}
		catch (IOException e) {
			// Report problem or handle it
			e.printStackTrace();
		}
		finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

}
