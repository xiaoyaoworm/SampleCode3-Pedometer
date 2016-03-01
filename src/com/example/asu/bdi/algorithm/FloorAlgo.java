package com.example.asu.bdi.algorithm;

import java.util.List;
import com.example.asu.bdi.common.FloorReturnObj;
import com.example.asu.bdi.constant.Constant;

public class FloorAlgo {

	private OtherAlgo otherAlgo = new OtherAlgo();
	private FloorReturnObj floorReturnObj = new FloorReturnObj();

	float altitude;
	int origStep;

	int count = 0; // means 4 * 0.4s = 1.6s, the most time without move in order
					// to refresh standardAltitude;

	float sum = 0;
	float avgAltitude = 0;

	// calculate the floor you climbed
	public FloorReturnObj calFloorProcess(float pressure, float standardAlti,
			List<Float> altiList, List<Float> avgAltiList, float changeAlti,
			int step, int floor) {

		// calculate the pressure, then add it into altiList
		altitude = otherAlgo.calAlti(pressure);
		altiList.add(altitude);

		if (standardAlti == 0) {
			standardAlti = altitude;
			changeAlti = 0;
			origStep = step;
		}

		// calculate average altitude of 10 points to add it into avgAltiList
		if (altiList.size() == 10) {
			for (int i = 0; i < 10; i++) {
				sum += altiList.get(i);
			}
			avgAltitude = sum / 10;
			avgAltiList.add(avgAltitude);
			System.out.println("avgAltitude: " + avgAltitude);
			altiList.clear();
			sum = 0;

			// altiList.size means 10 points = 0.4s
			// then count = 5 means 50 points = 2s
			// if 1) no move, refresh the standardAlti into altitude
			// or 2) standardAlti is higher than altitude then refresh

			count++;
			if (count == 4) {
				count = 0; // making every 4* 0.4s, run this code
				System.out.println("step: " + step +" "+origStep);
				if (step - origStep == 0 || standardAlti - 1 > avgAltitude || changeAlti < -1) {
					standardAlti = avgAltitude;
					changeAlti = 0;
				}
				origStep = step;
			}

			changeAlti = changeAlti + (avgAltitude - standardAlti);
			standardAlti = avgAltitude;

			if (changeAlti >= Constant.OneFloor) {
				floor++;
				changeAlti = changeAlti - Constant.OneFloor;
			}
		}
		System.out.println("avgAltitude: " + avgAltitude+"\n"+"standardAlti: " + standardAlti+"\n"+"changeAlti: " + changeAlti);

		floorReturnObj.floor = floor;
		floorReturnObj.standardAlti = standardAlti;
		floorReturnObj.altiList = altiList;
		floorReturnObj.avgAltiList = avgAltiList;
		floorReturnObj.altitude = altitude;
		floorReturnObj.changeAlti = changeAlti;

		return floorReturnObj;
	}
}
