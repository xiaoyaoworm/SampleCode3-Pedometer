package com.example.asu.bdi.algorithm;

import com.example.asu.bdi.constant.Constant;

public class OtherAlgo {
	
	float altitude;
	float gravity;
	float acceleration;

	// get the altitude from pressure
	public float calAlti(float pressure) {
		// p = air pressure (HPa)
		// h = altitude above sea level (m)
		// ==> h = [1-(p/1013.25)^0.190284]*145366.45*0.3048
		altitude = (float) ((float) (1 - Math.pow(pressure / 1013.25, 0.190284)) * 145366.45 * 0.3048);
		return altitude;
	}

	// get the acceleration
	public float calAcce(float x, float y, float z) {
		acceleration = x * x + y * y + z * z;
		return acceleration;
	}

	// get the gravity on time from altitude
	public float calGravity(float alti) {
		// g(h) = g(0)*(r/(r+h))^2
		// where
		// g(0) is standard gravity: 9.80665
		// h = height beyond sea level
		// r = Earth's mean radius
		gravity = (float) (Constant.StandGravity * Math.pow(
				(Constant.MeanRadious / (Constant.MeanRadious + alti)), 2));
		return gravity;
	}
	
}
