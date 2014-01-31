package nonActivities;

import info.IncomeActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import sqllite.Table_RecurringExpenses;

import android.content.Context;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class DailyUpdateThread {	
	public static String LAST_UPDATE_FILE_LOCATION = "LastUpdate.txt";
	private static int DAYS_IN_YEAR = 365;
	private static int WEEKS_IN_YEAR = 52;
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
		
		Double totalMoneyToAdd = daysBetween * getDailyAllowance().doubleValue();
		
		BalanceFunctions.addFunds(totalMoneyToAdd, context); // Update balance accordingly
		
		updateLastDate(now.toString(), context); // And update the file
	}
	
	public BigDecimal getDailyAllowance()
	{
		BigDecimal annualIncome = new BigDecimal(IncomeActivity.getIncome(context));
		BigDecimal annualCost = getRecurringExpensesAnnualTotal();
		BigDecimal annualSavings = getAnnualSavings();
		BigDecimal dailyAllowance = annualIncome.subtract(annualCost);
		dailyAllowance = dailyAllowance.subtract(annualSavings);
		dailyAllowance = dailyAllowance.divide(new BigDecimal(DAYS_IN_YEAR), 2, BigDecimal.ROUND_FLOOR); // not rounded
		
		// now truncate it to 2 decimals
		dailyAllowance = dailyAllowance.setScale(2, BigDecimal.ROUND_FLOOR);
		return dailyAllowance;
	}
	
	// check
	private BigDecimal getRecurringExpensesAnnualTotal()
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
		
		return new BigDecimal(annualSum);
	}
	
	private BigDecimal getAnnualSavings()
	{
		String filename = "Savings.txt";

		try {
			FileInputStream s = context.openFileInput(filename);
			BigDecimal weeklySavings = new BigDecimal((new Scanner(s)).nextLine());
			BigDecimal annualSavings = weeklySavings.multiply(new BigDecimal(WEEKS_IN_YEAR));
			return annualSavings;
		} catch (Exception e) {
			return new BigDecimal(0);
		}
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
