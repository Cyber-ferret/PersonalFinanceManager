package activities;

import com.example.personalfinancemanager.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class HomeScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return true;
	}
	
	public void openSelected(View view)
	{
		Intent i;
		switch(view.getId()) {
		case R.id.add_expense:
			i = new Intent(this, ExpenseActivity.class);
			startActivity(i);
			break;
		case R.id.add_funds:
			i = new Intent(this, AddFundsActivity.class);
			startActivity(i);
			break;
		case R.id.edit_income:
			i = new Intent(this, IncomeActivity.class);
			startActivity(i);
			break;
		case R.id.edit_recurring_expenses:
			i = new Intent(this, RecurringExpensesActivity.class);
			startActivity(i);
			break;
		case R.id.edit_expense_categories:
			i = new Intent(this, ManageExpenseCategoriesActivity.class);
			startActivity(i);
			break;
		case R.id.user_guide:
			i = new Intent(this, UserGuide.class);
			startActivity(i);
			break;
		case R.id.settings:
			//i = new Intent(this, IncomeActivity.class);
			//startActivity(i);
			break;
		}
	}

}
