package com.example.nurse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
/*
 * Every single patient's information is recorded by this class.
 * It includes all the information hospital needs: HealthCardNumber(String),
 *  arrival time(int), patient's vital sign(HashMap), patient's description(HashMap)
 *   and their urgency(int) to hospital.PatientSeen inherits from this class.
 */
public class Patient implements Comparable<Object> {

	String Name;
	String Birthday;
	Boolean seenDoc;
	String seenDocTime;
	String ArrivalTime;
	String HCN;
	VitalSign VitalSign;
	Integer Age;
	Integer Urgency;

	TreeMap<String, Float> Temperature = new TreeMap<String, Float>();
	TreeMap<String, Float[]> BloodPressure = new TreeMap<String, Float[]>();
	TreeMap<String, Integer> HeartRate = new TreeMap<String, Integer>();
	TreeMap<String, String> TextDescription = new TreeMap<String, String>();

	public Patient(String Name, String Birthday, String ArrivalTime, String HCN) {
		this.Name = Name;
		this.ArrivalTime = ArrivalTime;
		this.HCN = HCN;
		this.VitalSign = new VitalSign(Temperature, BloodPressure, HeartRate,
				TextDescription);
		this.Birthday = Birthday;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String now = dateFormat.format(date).split("/")[0];
		this.Age = (int) Math.ceil((Integer.parseInt(now) - Integer
				.parseInt(this.Birthday.split("-")[0])));
		this.seenDoc = false;
		this.setUrgency();
	}
// Get patient's name.
	public String getName() {
		return this.Name;
	}
	// Get patient's Birthday.
	public String getBirthday() {
		return this.Birthday;
	}
	// Get patient's arriving time.
	public String getArrivalTime() {
		return this.ArrivalTime;
	}
	// Get patient's health card number.
	public String getHCN() {
		return this.HCN;
	}
	// Get patient's symptoms.
	public String getSymptoms() {
		return this.VitalSign.toString();
	}
	// Get patient's description.
	public String getDescription() {
		return this.VitalSign.getTextDescription();
	}
	// Get patient's age.
	public Integer getAge() {
		return this.Age;
	}
// Set patient's temperature
	public void setTemperature(Float temp, String time) {
		this.VitalSign.Temperature.put(time, temp);
		this.setUrgency();
	}
	// Set patient's arriving time.
	public void setArrivalTime(String time) {
		this.ArrivalTime = time;
	}
// get patient's information includes health card number, age,birthday.
	public String toString() {
		return this.Name + ": " + this.HCN + " " + this.Age + " "
				+ this.Birthday;
	}
	// Set patient's Blood pressure includes systolic and diastolic with time. 
	public void setPressure(Float systolic, Float diastolic, String time) {
		Float[] bloodPressure = new Float[] { systolic, diastolic };
		this.VitalSign.BloodPressure.put(time, bloodPressure);
	}
	// Set patient's heart rate with time.
	public void setHeartRate(String time, Integer rate) {
		this.VitalSign.HeartRate.put(time, rate);
		this.setUrgency();
	}
	// Set patient's urgency.
	public Integer getUrgency() {
		this.setUrgency();
		return this.Urgency;
	}
// Dertermine level of  Urgency.
	public void setUrgency() {
		Integer result = 0;
		if (this.Age < 2) {
			result += 1;
		}
		if (!this.VitalSign.HeartRate.isEmpty()) {
			if (this.VitalSign.getTemperature() >= 39.0) {
				result += 1;
			}
			if (this.VitalSign.getSystolic() >= 140
					|| this.VitalSign.getDiastolic() >= 90) {
				result += 1;
			}
			if (this.VitalSign.getHeartRate() >= 100
					|| this.VitalSign.getHeartRate() <= 50) {
				result += 1;
			}
		}
		this.Urgency = result;
	}
// update age.
	public void updateAge() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String now = dateFormat.format(date).split("/")[0];
		this.Age = (int) Math.ceil((Integer.parseInt(now) - Integer
				.parseInt(this.Birthday.split("-")[0])));
	}

	@Override
	public int compareTo(Object obj) {
		if (((Patient) obj).getHCN() == this.getHCN()) {
			return 0;
		} else if (Integer.parseInt(((Patient) obj).getHCN()) > Integer
				.parseInt(this.getHCN())) {
			return -1;
		} else {
			return 1;
		}
	}

}
