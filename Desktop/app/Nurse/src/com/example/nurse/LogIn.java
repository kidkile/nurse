package com.example.nurse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends Activity {
/*
 * Login to interface depend on different identities.
 */
	private HashMap<String, String> userNameAndPassword = new HashMap<String, String>();
	private HashMap<String, String> userIdentity = new HashMap<String, String>();

	public void init() throws FileNotFoundException, IOException {
		AssetManager am = this.getAssets();
		InputStream is = am.open("passwords.txt");
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		String line = r.readLine();
		while (!(line == null)) {
			String[] userPass = line.split(",");
			this.userNameAndPassword.put(userPass[0], userPass[1]);
			this.userIdentity.put(userPass[0], userPass[2]);
			line = r.readLine();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			this.init();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		setTitle("Log In");
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

	private void toast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}
// Determining the account and passwords by the given file.
	public void onClickLogIn(View v) {
		EditText inputNameEditText = (EditText) findViewById(R.id.userNameInput);
		EditText inputPasswordEditText = (EditText) findViewById(R.id.passwordInput);
		String inputName = inputNameEditText.getText().toString();
		String inputPassword = inputPasswordEditText.getText().toString();
		if (inputName.equals("")) {// if there is no input on user name, toast a message.
			toast("Please enter a user name.");
			return;
		} else if (inputPassword.equals("")) {// if there is no input on passwords, toast a message.
			toast("Please enter a password.");
			return;
		}// if both user name and password are correct, go to another main interface.
		if (this.userNameAndPassword.keySet().contains(inputName)) {
			if (this.userNameAndPassword.get(inputName).equals(inputPassword)) {
				toast("Welcome, " + inputName);
				Intent in = new Intent(this, MainActivity.class);
				in.putExtra("identity", this.userIdentity.get(inputName));
				startActivity(in);
			} else {// if password is wrong, toast a message to remain.
				toast("Wrong password");
				return;
			}
		} else {// if user name is wrong, toast a message to remain.
			toast("No user found.");
			return;
		}
	}
}
