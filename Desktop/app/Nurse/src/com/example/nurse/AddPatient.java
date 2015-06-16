package com.example.nurse;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/*
 * Add patient to patient list.
 */
public class AddPatient extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitle("Add Patient");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_patient);
	}
// Add items to action bar if its present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_patient, menu);
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
// After adding patient, submit this information.
	public void submit(View view) throws IOException {
		EditText name = (EditText) findViewById(R.id.editText1);
		EditText bir = (EditText) findViewById(R.id.editText2);
		EditText HCN = (EditText) findViewById(R.id.editText3);
		try {
			Integer.parseInt(HCN.getText().toString());
		} catch (Exception e) {
			Toast.makeText(this, "Invalid health card number.",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (name.getText().toString().isEmpty()
				|| bir.getText().toString().isEmpty()
				|| HCN.getText().toString().isEmpty()) {
			Toast.makeText(this, "Please fill in all the information.",
					Toast.LENGTH_SHORT).show();
			return;
		} else if (HCN.getText().toString().length() != 6) {
			Toast.makeText(this, "Invalid health card number.",
					Toast.LENGTH_SHORT).show();
			return;
		} else if (bir.getText().toString().split("-").length != 3) {
			Toast.makeText(this, "Wrong birthday format.", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		for (String s : bir.getText().toString().split("-")) {
			try {
				Integer.parseInt(s);
			} catch (Exception e) {
				Toast.makeText(this, "Invalid birth day", Toast.LENGTH_SHORT)
						.show();
				return;
			}
		}
		Patient patient = new Patient(name.getText().toString(), bir.getText()
				.toString(), "-1", HCN.getText().toString());
		MainActivity.nurse.patientList.add(patient);
		MainActivity.nurse.saveData();

		Toast.makeText(this, "Patient added.", Toast.LENGTH_SHORT).show();
		MainActivity.instance.finish();
		MainActivity.instance.startActivity(MainActivity.instance.getIntent());
		finish();
	}
}
