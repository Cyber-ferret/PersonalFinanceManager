package stats;

import sqllite.Table_AdditionalFunds;
import sqllite.Table_Expenses;

import com.example.personalfinancemanager.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TransactionHistoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_history);
		
		//Populate transaction history
		
		Table_AdditionalFunds funds = new Table_AdditionalFunds(this);
		Table_Expenses expenses = new Table_Expenses(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction_history, menu);
		return true;
	}

}
