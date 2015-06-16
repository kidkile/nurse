package com.example.nurse;

import java.util.TreeMap;

import android.util.Log;

/*
 * This class includes 3 vital signs:temperature(float), 
 * blood pressure(float), heart rate(float). 
 * These information is recorded in a map(Use recorded time as key). 
 * Every time someone tries to add new information, it is added into the map in VitalSign class.
 */
public class VitalSign {
	TreeMap<String, Float> Temperature;
	TreeMap<String, Float[]> BloodPressure;
	TreeMap<String, Integer> HeartRate;
	TreeMap<String, String> TextDescription;

	public VitalSign(TreeMap<String, Float> temperature,
			TreeMap<String, Float[]> bloodPressure,
			TreeMap<String, Integer> heartRate,
			TreeMap<String, String> textDescription) {
		this.Temperature = temperature;
		this.BloodPressure = bloodPressure;
		this.HeartRate = heartRate;
		this.TextDescription = textDescription;
	}
// get patient's temperature.
	public float getTemperature() {
		return (Float) this.Temperature.lastEntry().getValue();
	}
	// get patient's systolic.
	public float getSystolic() {
		return (Float) this.BloodPressure.lastEntry().getValue()[0];
	}
	// get patient's diastolic.
	public float getDiastolic() {
		return (Float) this.BloodPressure.lastEntry().getValue()[1];
	}
	// get patient's heart rate.
	public float getHeartRate() {
		Log.d("das", this.HeartRate.lastEntry().getValue().toString());
		return (Integer) this.HeartRate.lastEntry().getValue();
		
	}
	// get patient's description.
	public String getTextDescription() {
		return this.TextDescription.lastEntry().getValue();
	}

}
