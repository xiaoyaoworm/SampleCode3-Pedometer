package com.example.asu.bdi.constant;

public final class Constant {
	
	// log file tag
	public static final String LogTagAcce = "Accelormeter";
	public static final String LogTagPre = "Pressure";
	public static final String LogTagTem = "Temperature";
	public static final String LogTagGyr = "Gyroscope";
	public static final String LogTagCsv = "CSV";
	
	public static final String SdPath = "/mnt/sdcard/monitorDataLog";
	public static final String RelPath = "/monitorDataLog";
	public static final String LocalPath = "";
	
	// the data used in step calculation
	public static final float MeanRadious = (float) 6371009; // the radious of earth
	public static final float StandGravity = (float) 9.80665; // the gravity of sea level
	public static final int SsvRange = 30; // the range of SSV+- 30
	public static final int OneFloor = 3; // one floor is 3 meter
	public static final int OneFloorSteps = 5; // climbing one floor needs at least 5 steps

}
