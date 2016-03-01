package com.example.asu.bdi.algorithm;

import java.util.List;
import com.example.asu.bdi.common.StepReturnObj;
import com.example.asu.bdi.constant.Constant;

public class StepAlgo {
	
	private OtherAlgo otherAlgo = new OtherAlgo();

	// define the variable used in the general coding
	float gapXXX = 3;
	float maxXXX;
	float minXXX;

	float x, y, z;
	float acceleration;
	float ssv;

	int i;
	int min;
	int max;
	int steps;

	float altitude;
	float gravity;

	StepReturnObj stepReturnObj = new StepReturnObj();
	

	// calculate the step number
	public StepReturnObj calStepProcess(float pressure, float x, float y,
			float z, List<Float> recList, int m, int n, int max, int min) {

		// create the data of acceleration
		acceleration = otherAlgo.calAcce(x, y, z);
		recList.add(acceleration);

		if (recList.size() >= 3) {
			altitude = otherAlgo.calAlti(pressure);
			gravity = otherAlgo.calGravity(altitude);
			ssv = (float) Math.pow(gravity, 2);

			// make the definition
			// important!!!!!!!!
			minXXX = (float) (ssv - Constant.SsvRange);
			maxXXX = (float) (ssv + Constant.SsvRange);

			i = recList.size();
			if (recList.get(i - 1) > recList.get(i - 2)
					&& recList.get(i - 3) > recList.get(i - 2)
					&& recList.get(i - 2) < minXXX && i - 2 - n >= gapXXX) {
				min++;
				if (min > max) {
					min--;
				}
				else {
					m = i - 2;
				}
			}
			else if (recList.get(i - 1) < recList.get(i - 2)
					&& recList.get(i - 3) < recList.get(i - 2)
					&& recList.get(i - 2) > maxXXX && i - 2 - m >= gapXXX) {
				max++;
				if (max > min + 1) {
					max--;
				}
				else {
					n = i - 2;
				}
			}

			steps = Math.min(max, min);

			stepReturnObj.m = m;
			stepReturnObj.n = n;
			stepReturnObj.max = max;
			stepReturnObj.min = min;
			stepReturnObj.recList = recList;
			stepReturnObj.steps = steps;
		}
		else {
			stepReturnObj.m = 0;
			stepReturnObj.n = 0;
			stepReturnObj.max = 0;
			stepReturnObj.min = 0;
			stepReturnObj.recList = recList;
			stepReturnObj.steps = 0;
		}
		return stepReturnObj;
	}



}
