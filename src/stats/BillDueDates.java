package stats;

import java.util.ArrayList;

import org.joda.time.DateTime;

import com.example.personalfinancemanager.R;
import com.example.personalfinancemanager.R.layout;
import com.example.personalfinancemanager.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BillDueDates extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill_due_dates);
		
		// Just do a simple LinearLayout with name and dates
		ArrayList<BillInformation> bills = getAllBills();
		for (int i=0; i<bills.size(); i++)
		{
			addTextView(bills.get(i).billName + ": " + bills.get(i).nextDueDate);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bill_due_dates, menu);
		return true;
	}

	public ArrayList<BillInformation> getAllBills()
	{
		return new ArrayList<BillInformation>();
	}
	
	public ArrayList<BillInformation> sortBills()
	{
		return new ArrayList<BillInformation>();
	}
	
	private class BillInformation 
	{
		public String billName;
		public DateTime nextDueDate;
	}
	
	private void addTextView(String text)
	{
		LinearLayout r = (LinearLayout) findViewById(R.id.transaction_history_linearlayout);
	    r.setOrientation(LinearLayout.VERTICAL);
	    TextView textView = new TextView(this);
	    
	    textView.setText(text);
	    textView.setWidth(LayoutParams.MATCH_PARENT);
	    r.addView(textView);
	}
}
