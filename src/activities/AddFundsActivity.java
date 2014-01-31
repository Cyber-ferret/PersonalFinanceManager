package activities;

import nonActivities.BalanceFunctions;

import org.apache.commons.lang3.StringUtils;

import sqllite.Table_AdditionalFunds;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.personalfinancemanager.R;

public class AddFundsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_funds);
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
	public void addFunds(View view)
	{
		TextView editText = (TextView) findViewById(R.id.display_value);
		String message = editText.getText().toString();
		
		Table_AdditionalFunds t = new Table_AdditionalFunds(this);
		EditText descriptionText = (EditText) findViewById(R.id.funding_description);
		
		Double funds;
		try {
			funds = Double.parseDouble(message);
		} catch (Exception e) {
			BalanceFunctions.raiseFailure("You must Input a valid number", this);
			return;
		}
		
		BalanceFunctions.addFunds(funds, this);
		
		double amount = Double.parseDouble(message);
		String description = descriptionText.getText().toString();
		
		t.addNew(amount, description);
		
		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.text_pop_up);
		TextView text = (TextView)dialog.findViewById(R.id.message_text);
		text.setText("Added $" + amount + " for " + description + ".");
		dialog.show();
	}
}
