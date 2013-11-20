package com.example.personalfinancemanager;

import org.w3c.dom.Comment;

import nonActivities.CommonFunctions;
import nonActivities.RecurringExpense;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class RecurringExpensesActivity extends Activity {
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recurring_expenses);
		
		tables.Table_RecurringExpenses  t = new tables.Table_RecurringExpenses(this);
		Cursor c = t.getRows();
		
		c.moveToFirst();
		while (!c.isAfterLast()) {
			String name = "name";
			//get the name
			name = c.getString(1);

		    c.moveToNext();
		    this.addButton(name);
		}
		
		//String file = CommonFunctions.mapping.get(CommonFunctions.DataFile.RECURRING_EXPENSES);
		//ArrayList<RecurringExpense> expenses = RecurringExpense.enumerateRecurringExpenses(file, context);
		//for(int i=0; i<expenses.size(); i++)
		//{
		//	addButton(expenses.get(i).getName());
		//}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recurring_expenses, menu);
		return true;
	}
	
	TextView nameInput;
	TextView costInput;
	Spinner occurrenceInput;
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
	    occurrenceInput = (Spinner) view.findViewById(R.id.occurance_input);
	    
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
		newRecurringPrompt.dismiss();  // Close our dialog box
		
		//TODO bug if name contains the substring "," (from CSV delimiter)
	    String name = nameInput.getText().toString();
	    String cost = costInput.getText().toString();
	    String occurrence = occurrenceInput.getSelectedItem().toString();
	    
	    if(name.trim().length() <= 0) {
	    	CommonFunctions.raiseFailure("Your name field was empty.  Cannot add", false, this);
	    	return;
	    } else if(cost.trim().length() <= 0) {
	    	CommonFunctions.raiseFailure("Your cost field was empty.  Cannot add", false, this);
	    	return;
	    } else if(occurrence.trim().length() <= 0) {
	    	CommonFunctions.raiseFailure("Your occurrence field was empty.  Cannot add", false, this);
	    	return;  // This one should never happen
	    }
	    //TODO make sure it doesn't already have that name
	    
	    //TODO add a hook to an edit function
	    //TODO add Recurring expense and write it to file
	    addButton(name);
	    
	    
	    // Used to be used for CSV
	    //RecurringExpense e = new RecurringExpense(name, cost, occurrenceInput, this); // This saves it to file
	    
	    
	    //TODO verify number inputs
	    tables.Table_RecurringExpenses  t = new tables.Table_RecurringExpenses(this);
	    t.addRecurringExpense(name, Double.parseDouble(cost), occurrenceInput.getSelectedItemPosition());
	}
	
	private void addButton(String name)
	{
		LinearLayout r = (LinearLayout) findViewById(R.id.add_recurring_expense_linearlayout);
	    r.setOrientation(LinearLayout.VERTICAL);
	    Button newButton = new Button(this);
	    newButton.setText(name);
	    newButton.setWidth(LayoutParams.MATCH_PARENT);
	    r.addView(newButton);
	}
}
