package com.example.personalfinancemanager;

import nonActivities.CommonFunctions;

import org.apache.commons.lang3.StringUtils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddFundsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_funds);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		//switch(item.getItemId()) {
		//case R.string.menu_item1:
			Intent intent = new Intent(this, AddFundsActivity.class);
			startActivity(intent);
		//	break;
		//case R.string.menu_item2:
		//	break;
		//case R.string.menu_item3:
		//	break;
		//case R.string.menu_item4:
		//	break;
		//}
		
		return true;
	}*/
	
	
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
	public void addFunds(View view)
	{
		//TODO add addition to a CSV
		TextView editText = (TextView) findViewById(R.id.display_value);
		String message = editText.getText().toString();
		CommonFunctions.addFunds(message, view, this);
		CommonFunctions.showBalance(view, this);
	}
}
