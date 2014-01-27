package nonActivities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import sqllite.Table_RecurringExpenses;

import android.content.Context;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class DailyUpdateThread {	
	public static String LAST_UPDATE_FILE_LOCATION = "LastUpdate.txt";
	private static int DAYS_IN_YEAR = 365;
	Context context;
	
	public DailyUpdateThread(Context context)
	{
		this.context = context;
	}
	
	public void update()
	{
		DateTime now = new DateTime();
		DateTime storedDate = getSavedDate(context); // Open file, get last update
		
		int daysBetween = Days.daysBetween(new DateTime(storedDate), new DateTime(now)).getDays();
		
		if(now.millisOfDay().get() < storedDate.millisOfDay().get())
		{
			daysBetween++; // So if the time of day now is earlier than the stored, we must add a day
			// i.e. if last update was 10pm last night, daysbetween will return 0 if called at 9am today
		}
		
		double totalMoneyToAdd = daysBetween * getDailyAllowance();
		
		BalanceFunctions.addFunds(totalMoneyToAdd+"", context); // Update balance accordingly
		
		updateLastDate(now.toString(), context); // And update the file
	}
	
	public double getDailyAllowance()
	{
		double annualIncome = Double.parseDouble(getIncome());
		double annualCost = getRecurringExpensesAnnualTotal();
		double dailyAllowance = (annualIncome - annualCost) / DAYS_IN_YEAR; // not rounded
		
		// now truncate it to 2 decimals
		int truncated = (int) (dailyAllowance * 100.);
		dailyAllowance = ((double) truncated) / 100.;
		return dailyAllowance;
	}
	
	// check
	private double getRecurringExpensesAnnualTotal()
	{
		Table_RecurringExpenses t = new Table_RecurringExpenses(context);
		ArrayList<Table_RecurringExpenses.Row> rows = t.getRows();
		
		double annualSum = 0;
		
		for(int i = 0; i < rows.size(); i++)
		{
			Table_RecurringExpenses.Row singleRow = rows.get(i);
			double cost = singleRow.cost;
			double annualCost = cost * getNumAnnualOccurrences(singleRow.occurrence);
			annualSum += annualCost;
		}
		
		return annualSum;
	}
	
	// check
	private int getNumAnnualOccurrences(int spinnerSelectionPosition)
	{
		switch (spinnerSelectionPosition)
		{
			case 0:
				return 365;
			case 1:
				return 52;
			case 2:
				return 26;
			case 3:
				return 12;
			case 4:
				return 2;
			case 5:
				return 1;
			default:
				return 0;
		}
	}
	
	// check
	private String getIncome()
	{
		String filename = "income.txt";
		FileInputStream in;

		try {
		    in = this.context.openFileInput(filename);
		    String income = convertStreamToString(in);
		    in.close();        
		    return income;
		  
		} catch (Exception e) {
			return "0";
		}
	}
	
	// check
	private static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	      sb.append(line).append("\n");
	    }
	    reader.close();
	    return sb.toString();
	}
	
	// check
	private static DateTime getSavedDate(Context context)
	{
		try {
			FileInputStream s = context.openFileInput(LAST_UPDATE_FILE_LOCATION);
			String line = (new Scanner(s)).nextLine();
			return new DateTime(line);
		} catch (FileNotFoundException e) {
			return new DateTime();
		} catch (Exception e) {
			return new DateTime();
		}
	}
	
	// check
	private static void updateLastDate(String newDate, Context c)
	{
		FileOutputStream outputStream;

		try {
			outputStream = c.openFileOutput(LAST_UPDATE_FILE_LOCATION, Context.MODE_PRIVATE);
			outputStream.write((newDate).getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
