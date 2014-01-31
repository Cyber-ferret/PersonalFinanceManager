package stats;

import nonActivities.DailyUpdateThread;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.personalfinancemanager.R;

public class StatisticsMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics_menu);
	}
	
	public void openSelected(View view)
	{
		Intent i;
		switch(view.getId()) {
		case R.id.view_spending_history:
			i = new Intent(this, TransactionHistoryActivity.class);
			startActivity(i);
			break;
		case R.id.spending_habits:
			i = new Intent(this, SpendingHabits.class);
			startActivity(i);
			break;
		case R.id.bill_due_dates:
			i = new Intent(this, BillDueDates.class);
			startActivity(i);
			break;
		case R.id.view_daily_allowance:
			Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.text_pop_up);
			TextView text = (TextView)dialog.findViewById(R.id.message_text);
			DailyUpdateThread t = new DailyUpdateThread(this);
			text.setText("$" + t.getDailyAllowance() + " per day");
			dialog.show();
			break;
		}
	}

}
