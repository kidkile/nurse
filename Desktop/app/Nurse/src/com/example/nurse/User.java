package com.example.nurse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;

import android.util.Log;
/*
 * We use User class to save and load data. Besides, 
 * we also do the login and logout process in this class.
 *  Nurse class inherits User class.
 */
public class User {
	TreeSet<Patient> patientList = new TreeSet<Patient>();

	public void initData(BufferedReader br) throws IOException {
		String patientInfo = br.readLine();
		while (!(patientInfo == null)) {
			String[] patientInfoArray = patientInfo.split(",");
			String healthCardNo = patientInfoArray[0];
			String name = patientInfoArray[1];
			String birthday = patientInfoArray[2];
			patientInfo = br.readLine();
			Patient patient = new Patient(name, birthday, "-1", healthCardNo);
			this.patientList.add(patient);
		}
		br.close();
		this.saveData();
	}
// Loading data.
	public void loadData(BufferedReader br) throws IOException {
		patientList.clear();
		String patientInfo = br.readLine();
		while (!(patientInfo == null)) {
			String[] patientInfoArray = patientInfo.split(";");
			String healthCardNo = patientInfoArray[0];
			String name = patientInfoArray[1];
			String birthday = patientInfoArray[2];
			String arrivalTime = patientInfoArray[3];
			String seenDoc = patientInfoArray[10];
			String seenDocTime = patientInfoArray[11];
			Integer urgency = Integer.parseInt(patientInfoArray[12]);
			String[] updateTime = patientInfoArray[5].split(",");
			String[] temp = patientInfoArray[6].split(",");
			String[] pressure = patientInfoArray[7].split(",");
			String[] heartR = patientInfoArray[8].split(",");
			String[] description = patientInfoArray[9].split(",");
			String[] descriptionUpdateTime = patientInfoArray[13].split(",");
			TreeMap<String, Float> temperature = new TreeMap<String, Float>();
			TreeMap<String, Float[]> bloodPressure = new TreeMap<String, Float[]>();
			TreeMap<String, Integer> heartRate = new TreeMap<String, Integer>();
			TreeMap<String, String> textDescription = new TreeMap<String, String>();
			for (int i = 1; i < updateTime.length; i++) {
				temperature.put(updateTime[i], Float.parseFloat(temp[i]));
				heartRate.put(updateTime[i], Integer.parseInt(heartR[i]));
				Float[] bloodF = new Float[] {
						Float.parseFloat(pressure[i].split("-")[0]),
						Float.parseFloat(pressure[i].split("-")[1]) };
				bloodPressure.put(updateTime[i], bloodF);
			}
			for (int i = 1; i < descriptionUpdateTime.length; i++) {
				Log.d("as", ((Integer) descriptionUpdateTime.length).toString()
						+ ((Integer) description.length).toString());
				textDescription.put(descriptionUpdateTime[i], description[i]);
			}
			Patient patient = new Patient(name, birthday, arrivalTime,
					healthCardNo);
			patient.VitalSign = new VitalSign(temperature, bloodPressure,
					heartRate, textDescription);
			patient.Urgency = urgency;
			if (seenDoc.equals("yes")) {
				patient.seenDoc = true;
				patient.seenDocTime = seenDocTime;
			} else {
				patient.seenDoc = false;
			}
			patientList.add(patient);
			patientInfo = br.readLine();
		}
		br.close();
	}
// Saving data.
	public void saveData() throws IOException {
		Log.d("here1", "1");
		File file = new File("/data/data/com.example.nurse/patient_data.txt");
		Log.d("here1", "2");
		FileOutputStream fos = new FileOutputStream(file);
		Log.d("here1", "3");

		for (Patient patient : patientList) {
			String health = patient.getHCN();
			String name = patient.getName();
			String birth = patient.getBirthday().toString();
			String arrivalTime = patient.getArrivalTime();
			String age = patient.getAge().toString();
			String dataUpdatedTimes = new String("0,");
			String descriptions = new String("0,");
			String descriptionUpdatedTimes = new String("0,");
			String bloodPressures = new String("0,");
			String seenDoc = new String();
			String seenDocTime = new String();
			for (String time : patient.VitalSign.Temperature.keySet()) {
				dataUpdatedTimes += time.toString() + ",";
			}
			String temps = new String("0,");
			for (Float temp : patient.VitalSign.Temperature.values()) {
				temps += temp.toString() + ",";
			}

			for (Float[] pressure : patient.VitalSign.BloodPressure.values()) {
				bloodPressures += pressure[0].toString() + "-"
						+ pressure[1].toString() + ",";
			}
			String heartRates = new String("0,");
			for (Integer heartRate : patient.VitalSign.HeartRate.values()) {
				heartRates += heartRate.toString() + ",";
			}
			Log.d("adas", ((Integer) patient.VitalSign.TextDescription.keySet()
					.size()).toString());
			for (String time : patient.VitalSign.TextDescription.keySet()) {
				descriptionUpdatedTimes += time + ",";
			}
			Log.d("adasdd", ((Integer) patient.VitalSign.TextDescription
					.values().size()).toString());

			for (String description : patient.VitalSign.TextDescription.values()) {
				descriptions += description + ",";

			}
			if (patient.seenDoc) {
				seenDoc = "yes";
				seenDocTime = patient.seenDocTime.toString();
			} else {
				seenDoc = "no";
				seenDocTime = "-1";
			}
			String urgency = new String(patient.Urgency.toString());
			fos.write((health + ";" + name + ";" + birth + ";" + arrivalTime
					+ ";" + age + ";" + dataUpdatedTimes + ";" + temps + ";"
					+ bloodPressures + ";" + heartRates + ";" + descriptions
					+ ";" + seenDoc + ";" + seenDocTime + ";" + urgency + ";"
					+ descriptionUpdatedTimes + "\n").getBytes());
		}
		fos.close();
	}
// get patient list.
	public TreeSet<Patient> getPatientList() {
		return this.patientList;
	}
}
