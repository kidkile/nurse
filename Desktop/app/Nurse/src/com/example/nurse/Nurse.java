package com.example.nurse;

import java.io.IOException;

import java.util.TreeMap;
import java.util.TreeSet;
// Providing functions that nurese can do

public class Nurse extends User {

	private TreeMap<Integer, TreeSet<Patient>> urgencyList = new TreeMap<Integer, TreeSet<Patient>>();

	public Nurse() {
		super();
	}
// Recording arriving time.
	public void recordArriveTime(Patient patient, String time)
			throws IOException {
		patient.setArrivalTime(time);
		super.saveData();
	}
// Recording description.
	public void recordDiscription(Patient patient, String description,
			String time) throws IOException {
		patient.VitalSign.TextDescription.put(time, description);
		super.saveData();
	}
// Recording vital sign.
	public void recordVitalSign(Patient patient, String time, Float temp,
			Float systolic, Float diastolic, Integer heartRate)
			throws IOException {
		Float[] asd = new Float[2];
		asd[0] = systolic;
		asd[1] = diastolic;
		patient.VitalSign.BloodPressure.put(time, asd);
		patient.VitalSign.HeartRate.put(time, heartRate);
		patient.VitalSign.Temperature.put(time, temp);
		patient.setUrgency();
		super.saveData();

	}
// Recording patient who have seen a doctor.
	public void recordSeenDoc(Patient patient, String time) throws IOException {
		patient.seenDoc = true;
		patient.seenDocTime = time;
		super.saveData();
	}
// Get patient list by searching arriving time.
	public Patient selectByCard(String healthCardNo) {
		for (Patient patient : super.getPatientList()) {
			if (patient.getHCN().equals(healthCardNo)) {
				return patient;
			}
		}
		return (Patient) null;
	}
// Set patients' information.
	public TreeMap<Integer, TreeSet<Patient>> getPatientListByUrgency() {
		TreeSet<Patient> patientUrgencyZero = new TreeSet<Patient>();
		TreeSet<Patient> patientUrgencyOne = new TreeSet<Patient>();
		TreeSet<Patient> patientUrgencyTwo = new TreeSet<Patient>();
		TreeSet<Patient> patientUrgencyThree = new TreeSet<Patient>();
		this.urgencyList = new TreeMap<Integer, TreeSet<Patient>>();

		for (Patient patient : this.patientList) {
			if (patient.seenDoc) {
				continue;
			}
			switch (patient.getUrgency()) {
			case 0:
				patientUrgencyZero.add(patient);
				break;
			case 1:
				patientUrgencyOne.add(patient);
				break;
			case 2:
				patientUrgencyTwo.add(patient);
				break;
			case 3:
				patientUrgencyThree.add(patient);
				break;
			}
		}
		this.urgencyList.put(0, patientUrgencyZero);
		this.urgencyList.put(1, patientUrgencyOne);
		this.urgencyList.put(2, patientUrgencyTwo);
		this.urgencyList.put(3, patientUrgencyThree);
		return this.urgencyList;
	}
}
