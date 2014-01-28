package stats;

import com.example.personalfinancemanager.R;
import com.example.personalfinancemanager.R.layout;
import com.example.personalfinancemanager.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SpendingHabits extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spending_habits);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spending_habits, menu);
		return true;
	}

	/*
	 * Lets make the options:
	 * Past Day
	 * Past Week
	 * Past Month
	 * Past 6 Months
	 * Past year
	 * All  (default)
	 */
	public void populateSpinner()
	{
		
	}
	
	public void updateHabitsView()
	{
		
	}
}
