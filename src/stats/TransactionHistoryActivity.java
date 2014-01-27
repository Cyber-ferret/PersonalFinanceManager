package stats;

import java.util.ArrayList;

import sqllite.Table_AdditionalFunds;
import sqllite.Table_Expenses;

import com.example.personalfinancemanager.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TransactionHistoryActivity extends Activity {
	private int fundsLocation, expensesLocation;
	ArrayList<Table_AdditionalFunds.Row> fundsList;
	ArrayList<Table_Expenses.Row> expensesList;
	private int numLoaded = 0;
	private int maxLoadable = 10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_history);
		
		//Populate transaction history
		Table_AdditionalFunds funds = new Table_AdditionalFunds(this);
		Table_Expenses expenses = new Table_Expenses(this);
		
		fundsList = funds.getRows();
		expensesList = expenses.getRows();
		
		fundsLocation = fundsList.size() - 1;
		expensesLocation = expensesList.size() - 1;
		
		load();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction_history, menu);
		return true;
	}
	
	public void loadMore(View view)
	{
		maxLoadable += 10;
		load();
	}
	
	public void load()
	{
		while(numLoaded < maxLoadable && (fundsLocation >= 0 || expensesLocation >= 0))
		{
			// Load a textview from the most recent one
			long expenseTime = expensesLocation < 0 ? Integer.MIN_VALUE : expensesList.get(expensesLocation).datetime;
			long fundsTime = fundsLocation < 0 ? Integer.MIN_VALUE : fundsList.get(fundsLocation).dateTime;
			
			// Load whichever is more recent
			if(expenseTime > fundsTime) {
				// Add a textview for the expense
				double cost = expensesList.get(expensesLocation).cost;
				String category = expensesList.get(expensesLocation).category;
				
				addTextView(category, cost, true);
				expensesLocation--;
			} else {
				// Add a textview for the addition
				double amount = fundsList.get(fundsLocation).amount;
				String description = fundsList.get(fundsLocation).description;
				
				addTextView(description, amount, false);
				fundsLocation--;
			}
			
			numLoaded++;
		}
	}
	
	private void addTextView(String description, double amount, boolean isExpense)
	{
		LinearLayout r = (LinearLayout) findViewById(R.id.transaction_history_linearlayout);
	    r.setOrientation(LinearLayout.VERTICAL);
	    TextView text = new TextView(this);

	    String plusOrMinus = isExpense ? "-" : "+";
	    
	    text.setText(plusOrMinus + amount + ": " + description);
	    text.setWidth(LayoutParams.MATCH_PARENT);
	    r.addView(text);
	}
}
