package com.example.nurse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;
/*
 * Set patient's information
 */
public class PatientInfo extends Activity {

	SimpleAdapter mSchedule;
	private ExpandableListView exList;
	String HCN = new String();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitle("Patient Info");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_info);
		
		exList = (ExpandableListView) findViewById(R.id.expandableListView1);
		String[] properties = new String[] { "Heath Card Number", "Name",
				"Age", "Birthday", "Arrival Time", "Vital Sign",
				"Text Description/Prescription", "Seen a Doctor", "Urgency" };
		HCN = MainActivity.patientKey;
		Patient patient = MainActivity.nurse.selectByCard(HCN);
		List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
		// Set first level list.
		List<Map<String, String>> gruops = new ArrayList<Map<String, String>>();
		for (String category : properties) {
			Map<String, String> cate = new HashMap<String, String>();
			cate.put("group", category);
			gruops.add(cate);
		}
		if (MainActivity.identity.equals("physician")) {
			findViewById(R.id.button2).setVisibility(8);
		}
		// Set second level list.
		// content 1.
		Map<String, String> vitalSignMap = new HashMap<String, String>();
		Map<String, String> nameMap = new HashMap<String, String>();
		Map<String, String> ageMap = new HashMap<String, String>();
		Map<String, String> birthMap = new HashMap<String, String>();
		Map<String, String> HCNMap = new HashMap<String, String>();
		Map<String, String> urgencyMap = new HashMap<String, String>();
		Map<String, String> arrivalTimeMap = new HashMap<String, String>();
		Map<String, String> descriptionMap = new HashMap<String, String>();
		Map<String, String> seenDocMap = new HashMap<String, String>();
		
		urgencyMap.put("child", patient.getUrgency().toString());
		HCNMap.put("child", patient.getHCN());
		birthMap.put("child", patient.getBirthday());
		ageMap.put("child", patient.getAge().toString());
		nameMap.put("child", patient.getName());
		// content 2.
		List<Map<String, String>> childs_1 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> childs_2 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> childs_3 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> childs_4 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> childs_5 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> childs_6 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> childs_7 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> childs_8 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> childs_9 = new ArrayList<Map<String, String>>();
		
		childs_1.add(HCNMap);
		childs_2.add(nameMap);
		childs_3.add(ageMap);
		childs_4.add(birthMap);
		childs_5.add(arrivalTimeMap);
		childs_8.add(seenDocMap);
		childs_9.add(urgencyMap);
		
		if (patient.getArrivalTime().equals("-1")) {
			arrivalTimeMap.put("child", "No data recorded");
			vitalSignMap.put("child", "No data recorded");
			seenDocMap.put("child", "No data recorded");
			childs_6.add(vitalSignMap);
		} else {
			arrivalTimeMap.put("child", "Last arrival time: \n" + patient.getArrivalTime());
			if (!patient.VitalSign.BloodPressure.keySet().isEmpty()) {
				for (String time : patient.VitalSign.BloodPressure.keySet()) {
					if (time.equals("0")) {
						continue;
					}
					String info = new String("");
					info += "Updated time: \n"
							+ time
							+ "\n\n"
							+ "Systolic: "
							+ patient.VitalSign.BloodPressure.get(time)[0]
							+ " mmHg\nDistolic: "
							+ patient.VitalSign.BloodPressure.get(time)[1]
							+ " mmHg\n\n"
							+ "HeartRate: "
							+ patient.VitalSign.HeartRate.get(time)
							+ " bpm\n\n"
							+ "Temperature: "
							+ patient.VitalSign.Temperature.get(time)
									.toString() + " °C";
					vitalSignMap = new HashMap<String, String>();
					vitalSignMap.put("child", info);
					childs_6.add(vitalSignMap);
				}
			} else {
				vitalSignMap.put("child", "No data recorded");
				childs_6.add(vitalSignMap);
			}
		}
		if (!patient.VitalSign.TextDescription.keySet().isEmpty()) {
			for (String time : patient.VitalSign.TextDescription.keySet()) {
				if (time.equals("0"))
					continue;
				String info = new String("");
				info += "Updated time: \n" + time
						+ "\n\nDescription/Prescription:\n"
						+ patient.VitalSign.TextDescription.get(time);
				descriptionMap = new HashMap<String, String>();
				descriptionMap.put("child", info);
				childs_7.add(descriptionMap);
			}
		} else {
			descriptionMap = new HashMap<String, String>();
			descriptionMap.put("child", "No data recorded");
			childs_7.add(descriptionMap);
		}
		if (patient.seenDoc == true) {
			String seenDocInfo = "This patient has seen a doctor.\n\n"
					+ "Last see doctor time:\n" + patient.seenDocTime;
			seenDocMap.put("child", seenDocInfo);
		} else {
			seenDocMap.put("child", "This patient hasn't seen a doctor yet");
		}
// Store two contents in order to show in list.
		childs.add(childs_1);
		childs.add(childs_2);
		childs.add(childs_3);
		childs.add(childs_4);
		childs.add(childs_5);
		childs.add(childs_6);
		childs.add(childs_7);
		childs.add(childs_8);
		childs.add(childs_9);

		SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(

		this, gruops, android.R.layout.simple_expandable_list_item_1,
				new String[] { "group" }, new int[] { android.R.id.text1 },
				childs, R.layout.list_item_2, new String[] { "child" },
				new int[] { android.R.id.text1 });
		exList.setAdapter(adapter);
		exList.setOnChildClickListener(listener);
	}

	private OnChildClickListener listener = new OnChildClickListener() {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			toast("_(:3」∠)_");
			return false;
		}
	};
// Add description.
	public void addDescription(View view) throws NumberFormatException,
			IOException {
		EditText TD = (EditText) findViewById(R.id.editText1);
		if (TD.getText().toString().isEmpty()) {
			Toast.makeText(this, "Please enter a description",
					Toast.LENGTH_SHORT).show();
			return;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String time = dateFormat.format(date);
		Patient patient = MainActivity.nurse
				.selectByCard(MainActivity.patientKey);

		MainActivity.nurse.recordDiscription(patient, TD.getText().toString(),
				time);
		Toast.makeText(this, "Description added", Toast.LENGTH_SHORT).show();
		this.finish();
		startActivity(this.getIntent());
	}
// Toast a message when text is too short
	private void toast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);

	}
// Add patient who have seen a doctor.
	public void addSeenDocTime(View v) throws NumberFormatException,
			IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String seenDocTime = dateFormat.format(date);
		Patient patient = MainActivity.nurse
				.selectByCard(MainActivity.patientKey);
		MainActivity.nurse.recordSeenDoc(patient, seenDocTime);
		Toast.makeText(this, "Time added", Toast.LENGTH_SHORT).show();
		this.finish();
		this.startActivity(this.getIntent());
	}

}