package com.example.nurse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/*
 * this class is used to modify symptoms of patients.
 */
public class ModifyVitalSign extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Modify Vital Sign/Arrival Time");
		setContentView(R.layout.activity_modify_vital_sign);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modifysymptoms, menu);
		return true;
	}

	public void SetSymptoms(View view) throws NumberFormatException,
			IOException {
		Intent intent = getIntent();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String time = dateFormat.format(date);
		String HCN = (String) intent.getSerializableExtra("patientkey");
		EditText heartRate = (EditText) findViewById(R.id.editText1);
		EditText diastolic = (EditText) findViewById(R.id.editText2);
		EditText temp = (EditText) findViewById(R.id.editText3);
		EditText systolic = (EditText) findViewById(R.id.editText4);
		Patient patient = MainActivity.nurse.selectByCard(HCN);
		if (patient.getArrivalTime().equals("-1")) {
			Toast.makeText(this, "Add failed. The patient hasen't arrived yet",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (temp.getText().toString().isEmpty()
				|| systolic.getText().toString().isEmpty()
				|| diastolic.getText().toString().isEmpty()
				|| heartRate.getText().toString().isEmpty()) {
			toast("Incomplete information. Please try again");
			return;
		}
		try {
			MainActivity.nurse.recordVitalSign(patient, time,
					Float.parseFloat(temp.getText().toString()),
					Float.parseFloat(systolic.getText().toString()),
					Float.parseFloat(diastolic.getText().toString()),
					Integer.parseInt(heartRate.getText().toString()));
		} catch (Exception e) {
			toast("Invalid input. Please try again.");
			return;
		}
		toast("Vital sign recorded");
	}
// set arriving time.
	public void setAT(View view) throws IOException {
		Intent intent = getIntent();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String arrivalTime = dateFormat.format(date);
		String HCN = (String) intent.getSerializableExtra("patientkey");
		Patient patient = MainActivity.nurse.selectByCard(HCN);
		MainActivity.nurse.recordArriveTime(patient, arrivalTime);
		toast("Arrival time recorded");
	}

	private void toast(String str) {
		// TODO Auto-generated method stub
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
