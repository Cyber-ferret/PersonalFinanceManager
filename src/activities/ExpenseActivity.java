package activities;

import java.util.ArrayList;
import java.util.List;

import nonActivities.BalanceFunctions;

import org.apache.commons.lang3.StringUtils;

import sqllite.Table_ExpenseCategories;
import sqllite.Table_Expenses;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.personalfinancemanager.R;

public class ExpenseActivity extends Activity {
	/**
	 * Default method
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deduct_funds);
		
		// Populate the expense category spinner with the categories
		List<String> array = new ArrayList<String>();
		List<Table_ExpenseCategories.Row> spinnerArray =  (new Table_ExpenseCategories(this)).getRows();
		
		for (int i=0; i < spinnerArray.size(); i++)
		{
			array.add(spinnerArray.get(i).categoryName);
		}

	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    Spinner spinner = (Spinner) findViewById(R.id.spending_category);
	    spinner.setAdapter(adapter);
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
		TextView editText = (TextView) findViewById(R.id.display_value);
		String message = editText.getText().toString();
		
		Table_Expenses t = new Table_Expenses(this);
		Spinner categorySpinner = (Spinner) findViewById(R.id.spending_category);
		
		String category;
		Double cost;
		try {
			cost = Double.parseDouble(message);
		} catch (Exception e) {
			BalanceFunctions.raiseFailure("You must Input a valid number", this);
			return;
		}
		
		try {
			category = categorySpinner.getSelectedItem().toString();
		} catch (Exception e) {
			category = "Miscellaneous"; // In case there is a null input
		}
		
		BalanceFunctions.deductFunds(cost, view, this); // Now deduct the funds
		
		t.addNew(category, cost);
		
		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.text_pop_up);
		TextView text = (TextView)dialog.findViewById(R.id.message_text);
		text.setText("Deducted $" + cost + " for " + category + ".");
		dialog.show();
	}
}
