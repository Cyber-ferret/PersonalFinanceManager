package stats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

import sqllite.Table_AdditionalFunds;
import sqllite.Table_Expenses;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.personalfinancemanager.R;

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
			long expenseTime = expensesLocation < 0 ? Long.MIN_VALUE : expensesList.get(expensesLocation).datetime;
			long fundsTime = fundsLocation < 0 ? Long.MIN_VALUE : fundsList.get(fundsLocation).dateTime;
			
			// Load whichever is more recent
			if(expenseTime > fundsTime) {
				// Add a textview for the expense
				double cost = expensesList.get(expensesLocation).cost;
				String category = expensesList.get(expensesLocation).category;
				DateTime date = new DateTime(expensesList.get(expensesLocation).datetime);
				
				addTextView(category, cost, date.toDate(), true);
				expensesLocation--;
			} else {
				// Add a textview for the addition
				double amount = fundsList.get(fundsLocation).amount;
				String description = fundsList.get(fundsLocation).description;
				DateTime date = new DateTime(fundsList.get(fundsLocation).dateTime);
				
				addTextView(description, amount, date.toDate(), false);
				fundsLocation--;
			}
			
			numLoaded++;
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	private void addTextView(String description, double amount, Date date, boolean isExpense)
	{
		LinearLayout r = (LinearLayout) findViewById(R.id.transaction_history_linearlayout);
	    r.setOrientation(LinearLayout.VERTICAL);
	    TextView text = new TextView(this);

	    String plusOrMinus = isExpense ? "-" : "+";
	    
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-yy hh:mm:ss a");
	    
	    text.setText(plusOrMinus + amount + ": " + description + "   (" + dateFormat.format(date) + ")");
	    text.setWidth(LayoutParams.MATCH_PARENT);
	    r.addView(text);
	}
}
