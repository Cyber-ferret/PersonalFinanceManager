package stats;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import sqllite.Table_Expenses;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.personalfinancemanager.R;

public class SpendingHabits extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spending_habits);
		
		populateSpinner();
		Spinner spinner = (Spinner) findViewById(R.id.time_period);
		updateHabitsView(spinner.getSelectedItemPosition());
	}

	public void populateSpinner()
	{
		List<String> array = new ArrayList<String>();

		array.add("Past Day");
		array.add("Past Week");
		array.add("Past Month");
		array.add("Past 3 Months");
		array.add("Past 6 Months");
		array.add("Past Year");
		array.add("All");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    final Spinner spinner = (Spinner) findViewById(R.id.time_period);
	    spinner.setAdapter(adapter);
	    spinner.setSelection(spinner.getBottom());
	    
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        updateHabitsView(spinner.getSelectedItemPosition());
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }
		});
	}
	
	public void updateHabitsView(int selectedPosition)
	{
		clearTextViews();
		int numDaysBack = 0;
		switch (selectedPosition)
		{
			case 0:
				numDaysBack = 1;
				break;
			case 1:
				numDaysBack = 7;
				break;
			case 2:
				numDaysBack = 30;
				break;
			case 3:
				numDaysBack = 91;
				break;
			case 4:
				numDaysBack = 182;
				break;
			case 5:
				numDaysBack = 365;
				break;
			case 6:
				numDaysBack = Integer.MAX_VALUE;
				break;
		}
		
		Table_Expenses t = new Table_Expenses(this);
		ArrayList<Table_Expenses.Row> rows = t.getRows();
		
		HashMap<String, Double> map = new HashMap<String, Double>();
		DateTime now = new DateTime();
		for(int i = 0; i < rows.size(); i++)
		{
			DateTime storedDate = new DateTime(rows.get(i).datetime);
			if(Days.daysBetween(storedDate, now).getDays() < numDaysBack)
			{
				addStats(rows.get(i), map);
			}
		}
		
		createStatsChart(map);
	}
	
	
	public void addStats(Table_Expenses.Row row, HashMap<String, Double> map)
	{
		Double value = map.get(row.category);
		Double newValue = (value == null) ? row.cost : value + row.cost;
		
		map.put(row.category, newValue);		
	}
	
	public void createStatsChart(HashMap<String, Double> map)
	{
		double sum = 0;
		List<Double> values = new ArrayList<Double>(map.values());
		List<String> keys = new ArrayList<String>(map.keySet());
		for(int i=0; i<values.size(); i++)
		{
			sum += values.get(i);
		}
		
		HashMap<String, Integer> percentages = new HashMap<String, Integer>(); // Do these in whole numbers
		for(int i=0; i<keys.size(); i++)
		{
			double numerator = map.get(keys.get(i));
			percentages.put(keys.get(i), (int)(100. * numerator / sum));
		}
		
		for(int i=0; i<keys.size(); i++)
		{
			String key = keys.get(i);
			BigDecimal cost = new BigDecimal(map.get(key)).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal formattedSum = new BigDecimal(sum).setScale(2, BigDecimal.ROUND_HALF_UP);
			addTextView(key + ": " + percentages.get(key) + "%   ($" + cost + " of of $" + formattedSum + ")");
		}
	}
	
	private void addTextView(String informationLine)
	{
		LinearLayout r = (LinearLayout) findViewById(R.id.spending_habits_linearlayout);
	    r.setOrientation(LinearLayout.VERTICAL);
	    TextView text = new TextView(this);
	    
	    text.setText(informationLine);
	    text.setWidth(LayoutParams.MATCH_PARENT);
	    r.addView(text);
	}
	
	private void clearTextViews()
	{
		LinearLayout layout = (LinearLayout) this.findViewById(R.id.spending_habits_linearlayout);
		for(int i = 0; i < layout.getChildCount(); i++)
		{
			if(layout.getChildAt(i) instanceof TextView)
			{
				layout.getChildAt(i).setVisibility(View.GONE);
			}
		}
	}
}
