package activities;

import info.InformationMenu;
import nonActivities.BalanceFunctions;
import nonActivities.DailyUpdateThread;
import stats.StatisticsMenu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.personalfinancemanager.R;

public class HomeScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		
		TextView t = (TextView) findViewById(R.id.balance_display);
		t.setText("$" + BalanceFunctions.getBalance(this));
		
		DailyUpdateThread updater = new DailyUpdateThread(this);
		updater.update();
	}
	
	@Override
	public void onResume()
    {
		super.onResume();

		TextView t = (TextView) findViewById(R.id.balance_display);
		t.setText("$" + BalanceFunctions.getBalance(this));
		
		DailyUpdateThread updater = new DailyUpdateThread(this);
		updater.update();
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
		case R.id.edit_information:
			i = new Intent(this, InformationMenu.class);
			startActivity(i);
			break;
		case R.id.statistics:
			i = new Intent(this, StatisticsMenu.class);
			startActivity(i);
			break;
		case R.id.user_guide:
			i = new Intent(this, UserGuide.class);
			startActivity(i);
			break;
		}
	}

}
