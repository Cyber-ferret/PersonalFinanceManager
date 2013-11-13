package nonActivities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import nonActivities.RecurringExpense.timePeriod;

import android.content.Context;



public class Expense {
	private static final int NUM_FIELDS = 2;  // Make sure this matches the number of fields below
	BigDecimal value; 	// 1
	Date date;			// 2
	//ExpenseCategory category; //TODO add expense categories
	
	public Expense(String value)
	{
		this.date = new Date();
		
		try
		{
			this.value = new BigDecimal(value);
		}
		catch(NumberFormatException e)
		{
			CommonFunctions.raiseFailure("Could not interpret " + value + " as a number value", false, null);
			return;
		}
	}
	
	public Expense(String value, String date)
	{
		try {
			this.date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(date);
		} catch (ParseException e1) {
			CommonFunctions.raiseFailure(e1.getMessage(), false, null);
		}
		
		try
		{
			this.value = new BigDecimal(value);
		}
		catch(NumberFormatException e)
		{
			CommonFunctions.raiseFailure("Could not interpret " + value + " as a number value", false, null);
			return;
		}
	}
	
	/*public static Expense[] enumerateExpenses(String fileName, Context context)
	{
		ArrayList<Expense> returnVal = new ArrayList<Expense>();
		
		try {
			FileInputStream s = context.openFileInput(fileName);
			Scanner scanner = new Scanner(s);
			while(scanner.hasNext())
			{
				String line = scanner.nextLine();
				String[] list = line.split(",");
			
				if(list.length != NUM_FIELDS)
				{
					CommonFunctions.raiseFailure("Could not find the file " + fileName + " while trying to read recurring expenses", false, context);
					return null;
				}
				
				timePeriod timePeriodFromIndex = timePeriod.values()[Integer.parseInt(list[2])];
				returnVal.add(new Expense(list[0], list[1], timePeriodFromIndex));
			}
		} catch (FileNotFoundException e) {
			CommonFunctions.raiseFailure("Could not find the file " + fileName + " while trying to read recurring expenses", false, context);
			return null;
		}
		
		return (Expense[]) returnVal.toArray();
	}*/
	
	public String getCSVString()
	{
		return ("\"" + value + "\",\"" + date.toString() + "\"");
	}
}
