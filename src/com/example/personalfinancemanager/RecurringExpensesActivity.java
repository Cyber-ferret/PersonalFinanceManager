package com.example.personalfinancemanager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RecurringExpensesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recurring_expenses);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recurring_expenses, menu);
		return true;
	}

	
}
