package info;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.personalfinancemanager.R;

public class InformationMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information_menu);
	}

	public void openSelected(View view)
	{
		Intent i;
		switch(view.getId()) {
		case R.id.edit_income:
			i = new Intent(this, IncomeActivity.class);
			startActivity(i);
			break;
		case R.id.edit_bills:
			i = new Intent(this, RecurringExpensesActivity.class);
			startActivity(i);
			break;
		case R.id.edit_spending_categories:
			i = new Intent(this, ManageExpenseCategoriesActivity.class);
			startActivity(i);
			break;
		case R.id.edit_savings:
			i = new Intent(this, SavingsActivity.class);
			startActivity(i);
			break;
		}
	}
}
