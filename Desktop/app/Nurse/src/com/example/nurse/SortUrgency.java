package com.example.nurse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
/*
 * sorting patients by urgency
 */
public class SortUrgency extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitle("Patient List By Urgency");
		SimpleAdapter UrgencyListAdapter;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sort_urgency);
		ListView list = (ListView) findViewById(R.id.SortUrgency);
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		TreeMap<Integer, TreeSet<Patient>> urgencyMap = MainActivity.nurse
				.getPatientListByUrgency();
		for (Integer i = 3; i >= 0; i--) {
			for (Patient patient : urgencyMap.get(i)) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("ItemTitle", patient.getName());
				map.put("ItemText", "Urgency: " + i.toString());
				mylist.add(map);
			}
		}

		UrgencyListAdapter = new SimpleAdapter(this, mylist,
				R.layout.list_item, new String[] { "ItemTitle", "ItemText" },
				new int[] { R.id.ItemTitle, R.id.ItemText });
		list.setAdapter(UrgencyListAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sort_urgency, menu);
		return true;
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
