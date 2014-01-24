package info;

import java.util.ArrayList;

import com.example.personalfinancemanager.R;

import sqllite.Table_RecurringExpenses;

import nonActivities.BalanceFunctions;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
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
		
		Table_RecurringExpenses  t = new Table_RecurringExpenses(this);

		ArrayList<Table_RecurringExpenses.Row> rows = t.getRows();
		
		for(int i=0; i<rows.size(); i++)
		{
			this.addButton(rows.get(i).name, rows.get(i).ID);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recurring_expenses, menu);
		return true;
	}
	
	TextView nameInput, costInput;
    Spinner occurrenceInput, dayInput, monthInput, yearInput;
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
	    dayInput = (Spinner) view.findViewById(R.id.day_input);
	    monthInput = (Spinner) view.findViewById(R.id.month_input);
	    yearInput = (Spinner) view.findViewById(R.id.year_input);
	    
	    newRecurringPrompt = new Dialog(mTheme);
	    populateSpinner(view, R.id.occurance_input, R.array.occurance_array);
	    populateSpinner(view, R.id.day_input, R.array.day_array);
	    populateSpinner(view, R.id.month_input, R.array.month_array);
	    populateSpinner(view, R.id.year_input, R.array.year_array);
	    newRecurringPrompt.getWindow().setTitle("Input the following information");
	    newRecurringPrompt.setContentView(view);
	    newRecurringPrompt.show();
	}
	
	private Spinner populateSpinner(View v, int spinnerId, int arrayId)
	{
		Spinner spinner = (Spinner) v.findViewById(spinnerId);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				 arrayId, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		return spinner;
	}
	
	public void addNewExpense(View view)
	{
		newRecurringPrompt.dismiss();  // Close our dialog box
		
	    String name = nameInput.getText().toString();
	    String cost = costInput.getText().toString();
	    int occurrence = occurrenceInput.getSelectedItemPosition();
	    int day = dayInput.getSelectedItemPosition();
	    int month = monthInput.getSelectedItemPosition();
	    int year = yearInput.getSelectedItemPosition();
	    
	    if(name.trim().length() <= 0) {
	    	BalanceFunctions.raiseFailure("Your name field was empty.  Cannot add", false, this);
	    	return;
	    } else if(cost.trim().length() <= 0) {
	    	BalanceFunctions.raiseFailure("Your cost field was empty.  Cannot add", false, this);
	    	return;
	    } 
	    
	    Table_RecurringExpenses  t = new Table_RecurringExpenses(this);
	    long newID = t.addNew(name, Double.parseDouble(cost), occurrence, day, month, year);
	    
	    addButton(name, newID);
	}
	
	SpecificButton recurringExpenseBeingModified;
	public void showEditRecurringExpensePrompt(View view)
	{
		recurringExpenseBeingModified = (SpecificButton) view;
		
		LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(
	            LAYOUT_INFLATER_SERVICE);
	    ContextThemeWrapper mTheme = new ContextThemeWrapper(this,
	            R.style.AppTheme);

	    view = inflater.inflate(R.layout.reucrring_expense_edit_prompt, null);
	    
	    nameInput = (TextView) view.findViewById(R.id.name_input);
	    costInput = (TextView) view.findViewById(R.id.cost_input);
	    occurrenceInput = (Spinner) view.findViewById(R.id.occurance_input);
	    dayInput = (Spinner) view.findViewById(R.id.day_input);
	    monthInput = (Spinner) view.findViewById(R.id.month_input);
	    yearInput = (Spinner) view.findViewById(R.id.year_input);
	    
	    Table_RecurringExpenses t = new Table_RecurringExpenses(this);
	    Table_RecurringExpenses.Row row = t.getSingle(recurringExpenseBeingModified.elementID);
	    nameInput.setText(row.name);
	    costInput.setText(row.cost+"");
	    
	    newRecurringPrompt = new Dialog(mTheme);
	    populateSpinner(view, R.id.occurance_input, R.array.occurance_array);
	    populateSpinner(view, R.id.day_input, R.array.day_array);
	    populateSpinner(view, R.id.month_input, R.array.month_array);
	    populateSpinner(view, R.id.year_input, R.array.year_array);
	    occurrenceInput.setSelection(row.occurrence);
	    dayInput.setSelection(row.dayDue);
	    monthInput.setSelection(row.monthDue);
	    yearInput.setSelection(row.yearDue);
	    newRecurringPrompt.getWindow().setTitle("Category Info");
	    newRecurringPrompt.setContentView(view);
	    newRecurringPrompt.show();
	}
	
	public void modifyRecurringExpense(View view)
	{
		newRecurringPrompt.dismiss();  // Close our dialog box
		
	    String newName = nameInput.getText().toString();
	    String newCost = costInput.getText().toString();
	    int newOccurrence = occurrenceInput.getSelectedItemPosition();
	    int newDay = dayInput.getSelectedItemPosition();
	    int newMonth = monthInput.getSelectedItemPosition();
	    int newYear = yearInput.getSelectedItemPosition();
	    
	    if(newName.trim().length() <= 0) {
	    	BalanceFunctions.raiseFailure("Your name field was empty.  Cannot add", false, this);
	    	return;
	    } else if(newName.trim().length() <= 0) {
	    	BalanceFunctions.raiseFailure("Your cost field was empty.  Cannot add", false, this);
	    	return;
	    }
	    
	    Table_RecurringExpenses  t = new Table_RecurringExpenses(this);
	    t.update(recurringExpenseBeingModified.elementID, newName, newCost, newOccurrence, newDay, newMonth, newYear);
	    
	    recurringExpenseBeingModified.setText(newName);
	}
	
	public void deleteRecurringExpense(View view)
	{
		newRecurringPrompt.dismiss();  // Close our dialog box
		
		Table_RecurringExpenses  t = new Table_RecurringExpenses(this);
	    t.delete(recurringExpenseBeingModified.elementID);
	    
	    recurringExpenseBeingModified.setVisibility(View.GONE);
	}
	
	private void addButton(String name, long ID)
	{
		LinearLayout r = (LinearLayout) findViewById(R.id.add_recurring_expense_linearlayout);
	    r.setOrientation(LinearLayout.VERTICAL);
	    SpecificButton newButton = new SpecificButton(this, ID);
	    newButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				showEditRecurringExpensePrompt(view);
			}
	    });
	    newButton.setText(name);
	    newButton.setWidth(LayoutParams.MATCH_PARENT);
	    r.addView(newButton);
	}
	
	public class SpecificButton extends Button
	{
		public long elementID;
		public SpecificButton(Context context, long ID) {
			super(context);
			this.elementID = ID;
		}
	}
}
