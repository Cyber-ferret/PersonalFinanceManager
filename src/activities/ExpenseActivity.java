package activities;

import nonActivities.CommonFunctions;

import org.apache.commons.lang3.StringUtils;

import com.example.personalfinancemanager.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExpenseActivity extends Activity {
	/**
	 * Default method
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deduct_funds);
	}

	/**
	 * Default method
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Implement me!
		return true;
	}
	
	
	public void keyPress(View view)
	{
		Button buttonPressed = (Button) view;
		
		if(buttonPressed.getText().equals("C"))
		{
			// Delete most recent Character
			TextView editText = (TextView) findViewById(R.id.display_value);
			String newValue = editText.getText().toString();
			if(newValue.length() > 0)
			{
				newValue = newValue.substring(0, newValue.length() - 1);
				editText.setText(newValue);
			}
		}
		else
		{
			// Add the character of whatever key was pressed
			TextView editText = (TextView) findViewById(R.id.display_value);
			String newValue = editText.getText().toString();
			newValue += buttonPressed.getText();
			
			boolean cond1 = StringUtils.countMatches(newValue, ".")  <= 1;
			boolean cond2 = StringUtils.substringAfter(newValue, ".").length() <= 2;
			if(cond1 && cond2)
			{
				editText.setText(newValue);
			}
		}
	}
	
	/**
	 * gets the text view then updates the balance via the
	 * updateBalance method.  It then displays the new balance.
	 */
	public void deductFunds(View view)
	{
		//TODO add addition to a CSV
		TextView editText = (TextView) findViewById(R.id.display_value);
		String message = editText.getText().toString();
		CommonFunctions.deductFunds(message, view, this);
		CommonFunctions.showBalance(view, this);
	}
}
