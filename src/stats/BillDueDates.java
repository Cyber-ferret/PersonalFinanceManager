package stats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;

import sqllite.Table_RecurringExpenses;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.personalfinancemanager.R;

public class BillDueDates extends Activity {

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill_due_dates);
		
		// Just do a simple LinearLayout with name and dates
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");
		ArrayList<Bill> bills = getAllBills();
		bills = sortBills(bills);
		for (int i=0; i<bills.size(); i++)
		{
			addTextView(bills.get(i).billName + ": " + dateFormat.format(bills.get(i).nextDueDate.toDate()));
		}
	}

	public ArrayList<Bill> getAllBills()
	{
		Table_RecurringExpenses table = new Table_RecurringExpenses(this);
		ArrayList<Table_RecurringExpenses.Row> rows = table.getRows();
		ArrayList<Bill> bills = new ArrayList<Bill>();
		
		for(int i=0; i<rows.size(); i++)
		{
			Table_RecurringExpenses.Row r = rows.get(i);
			int dayDue = r.dayDue + 1;
			int monthDue = r.monthDue; // 0 based
			int yearDue = r.yearDue + 2013;
			
			Calendar cal = Calendar.getInstance();
			cal.set(yearDue, monthDue, dayDue);
			
			DateTime nextDue = new DateTime(cal.getTime());
			DateTime today = new DateTime();
			
			if(nextDue.isBefore(today))
			{
				switch(r.occurrence)
				{
					case 0: // Daily
						nextDue = nextDue.plusDays(1 + Days.daysBetween(nextDue, today).getDays());
						break;
					case 1: // Weekly
						nextDue = nextDue.plusWeeks(1 + Weeks.weeksBetween(nextDue, today).getWeeks());
						break;
					case 2: // Bi-Weekly
						int weeksBetween = Weeks.weeksBetween(nextDue, today).getWeeks();
						weeksBetween = weeksBetween - (weeksBetween % 2);
						nextDue = nextDue.plusWeeks(2 + weeksBetween);
						break;
					case 3: // Monthly
						nextDue = nextDue.plusMonths(1 + Months.monthsBetween(nextDue, today).getMonths());
						break;
					case 4: // Bi-Annually
						int monthsBetween = Months.monthsBetween(nextDue, today).getMonths();
						monthsBetween = monthsBetween - (monthsBetween % 6);
						nextDue = nextDue.plusMonths(6 + monthsBetween);
						break;
					case 5: // Annually
						nextDue = nextDue.plusYears(1 + Years.yearsBetween(nextDue, today).getYears());
						break;
				}
			}
			Bill b = new Bill();
			b.billName = r.name;
			b.nextDueDate = nextDue;
			bills.add(b);
		}
		return bills;
	}
	
	public ArrayList<Bill> sortBills(ArrayList<Bill> bills)
	{
		for(int i=0; i< bills.size(); i++)
		{
			Bill billRightIndex = bills.get(i);
			for(int a=0; a<i; a++)
			{
				if(billRightIndex.nextDueDate.isBefore(bills.get(a).nextDueDate))
				{
					bills.remove(i);
					bills.add(a, billRightIndex);
					break;
				}
			}
		}
		return bills;
	}
	
	private class Bill 
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
