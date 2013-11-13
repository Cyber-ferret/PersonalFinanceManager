package com.example.personalfinancemanager;

import nonActivities.Expense;
import nonActivities.RecurringExpense;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class RecurringExpensesActivity extends Activity {
	final Context context = this;
	
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
	
	TextView nameInput;
	TextView costInput;
	Spinner occuranceInput;
	Dialog newRecurringPrompt;
	public void addRecurringExpense(View view)
	{
		LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(
	            LAYOUT_INFLATER_SERVICE);
	    ContextThemeWrapper mTheme = new ContextThemeWrapper(this,
	            R.style.AppTheme);

	    view = inflater.inflate(R.layout.reucrring_expense_prompt, null);
	    
	    nameInput = (TextView) view.findViewById(R.id.name_input);
	    costInput = (TextView) view.findViewById(R.id.cost_input);
	    occuranceInput = (Spinner) view.findViewById(R.id.occurance_input);
	    
	    newRecurringPrompt = new Dialog(mTheme);
	    populateOccuranceSpinner(R.id.occurance_input, view);
	    newRecurringPrompt.getWindow().setTitle("Input the following information");
	    newRecurringPrompt.setContentView(view);
	    newRecurringPrompt.show();
	}
	
	private Spinner populateOccuranceSpinner(int id, View v)
	{
		Spinner spinner = (Spinner) v.findViewById(id);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.occurance_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		return spinner;
	}
	
	public void addNewExpense(View view)
	{
		//TODO bug if name contains the substring "," (from CSV delimiter)
	    String name = nameInput.getText().toString();
	    String cost = costInput.getText().toString();
	    String occurrence = occuranceInput.getSelectedItem().toString();
	    
	    //TODO dynamically add new buttons
	    LinearLayout r = (LinearLayout) findViewById(R.id.add_recurring_expense_linearlayout);
	    r.setOrientation(LinearLayout.VERTICAL);
	    Button newButton = new Button(this);
	    newButton.setText(name);
	    newButton.setWidth(LayoutParams.MATCH_PARENT);
	    r.addView(newButton);
	    
	    newRecurringPrompt.dismiss();  // Close our dialog box
	    //RecurringExpense e = new RecurringExpense(nameInput.getText().toString(), costInput.getText().toString(), occuranceInput);
	}
}
