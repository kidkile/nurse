package com.example.nurse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/*
 *  Main interface.
 */
public class MainActivity extends Activity {
	static Nurse nurse = new Nurse();
	EditText inputSearch;
	SimpleAdapter mSchedule;
	static String identity = new String();
	static String patientKey = new String();
	public static MainActivity instance = null;
// Set patient data in patient_data file.
	public void init() throws FileNotFoundException, IOException {
		AssetManager am = this.getAssets();
		File file = new File("/data/data/com.example.nurse/patient_data.txt");
		if (!file.exists()) {
			InputStream is = am.open("patient_records.txt");
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			nurse.initData(r);
		} else {
			BufferedReader r = new BufferedReader(new FileReader(file));
			nurse.loadData(r);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitle("Patient List");
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.activity_main);
		ListView list = (ListView) findViewById(R.id.listView1);
		try {
			this.init();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		for (Patient patient : MainActivity.nurse.patientList) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("ItemTitle", patient.getName());
			map.put("ItemText", patient.getHCN());
			mylist.add(map);
		}
		Intent intent = getIntent();
		identity = (String) intent.getSerializableExtra("identity");
		if (identity.equals("physician")) {
			findViewById(R.id.button1).setVisibility(8);
			findViewById(R.id.button2).setVisibility(8);
		}
		mSchedule = new SimpleAdapter(this, mylist, R.layout.list_item,
				new String[] { "ItemTitle", "ItemText" }, new int[] {
						R.id.ItemTitle, R.id.ItemText });
		list.setAdapter(mSchedule);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MainActivity.this, PatientInfo.class);
				if (inputSearch.getText().toString().length() == 0) {
					patientKey = mylist.get((int) id).get("ItemText");
					startActivity(intent);
				} else if (inputSearch.getText().toString().length() == 6) {
					patientKey = inputSearch.getText().toString();
					startActivity(intent);
				} else {
					{
						Toast.makeText(MainActivity.this,
								"Please enter complete health card number",
								Toast.LENGTH_SHORT).show();
						return;
					}
				}
			}
		});

		inputSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				MainActivity.this.mSchedule.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void modifySymptoms(View view) {
		Intent intent = new Intent(this, ModifyVitalSign.class);
		EditText HCN = (EditText) findViewById(R.id.inputSearch);
		if (HCN.getText().toString().length() != 6) {
			Toast.makeText(this, "Please enter complete health card number",
					Toast.LENGTH_SHORT).show();
			return;
		}
		intent.putExtra("patientkey", HCN.getText().toString());
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClickAddPatient(View v) {
		Intent in = new Intent(this, AddPatient.class);
		startActivity(in);
	}
// Connect to add patient.
	public void onClickSortUrgency(View v) {
		Intent in = new Intent(this, SortUrgency.class);
		startActivity(in);
	}

}
