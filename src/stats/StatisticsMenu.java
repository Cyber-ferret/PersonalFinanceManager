package stats;

import com.example.personalfinancemanager.R;
import com.example.personalfinancemanager.R.layout;
import com.example.personalfinancemanager.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class StatisticsMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistics_menu, menu);
		return true;
	}
	
	public void openSelected(View view)
	{
		Intent i;
		switch(view.getId()) {
		case R.id.view_transaction_history:
			//i = new Intent(this, IncomeActivity.class);
			//startActivity(i);
			break;
		case R.id.spending_pie_graph:
			//i = new Intent(this, IncomeActivity.class);
			//startActivity(i);
			break;
		case R.id.bill_due_dates:
			//i = new Intent(this, IncomeActivity.class);
			//startActivity(i);
			break;
		}
	}

}
