package com.example.asu.bdi.common;

import java.util.List;

// define kinds of data: max,min,recList as the return value of
// stepAlgo.calStepProcess

public class StepReturnObj {

	public int max; // the number of crest
	public int min; // the number of trough
	public List<Float> recList; // the list of all plot data
	public int m;
	public int n;
	public int steps;

}