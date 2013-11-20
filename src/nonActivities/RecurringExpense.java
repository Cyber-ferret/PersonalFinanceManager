package nonActivities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;


import android.content.Context;
import android.widget.Spinner;

public class RecurringExpense {
	public static String RECURRING_EXPENSES = "RecurringExpenses.txt";
	private static final int NUM_FIELDS = 3;  // Make sure this matches the number of fields below
	String name; 			// 1
	BigDecimal value;		// 2
	timePeriod occurance;	// 3
	
	public String getName() {
		return name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public timePeriod getOccurance() {
		return occurance;
	}
	
	/**
	 * Allowed time periods which expenses can be repeated
	 */
	public static enum timePeriod
	{
		DAILY, WEEKLY, BI_WEEKLY, MONTHLY, BI_ANNUALLY, ANNUALLY
	}
	
	public static boolean alreadyExists(String name, Context c)
	{
		ArrayList<RecurringExpense> expenses = RecurringExpense.enumerateRecurringExpenses(CommonFunctions.mapping.get(CommonFunctions.DataFile.RECURRING_EXPENSES), c);
		
		for(int i=0; i<expenses.size(); i++)
		{
			if(expenses.get(i).getName().equalsIgnoreCase(name))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Used when creating objects from user input
	 */
	public RecurringExpense(String name, String value, Spinner occurance, Context c)
	{
		this.name = name;
		this.occurance = getTimePeriod(occurance);
		
		try
		{
			this.value = new BigDecimal(value);
		}
		catch(NumberFormatException e)
		{
			CommonFunctions.raiseFailure("Could not interpret " + value + " as a dollar value", false, null);
		}
		
		saveToFile(c);
	}
	
	public void saveToFile(Context c)
	{
		CommonFunctions.writeToFile("\n" + this.getCSVString(), RECURRING_EXPENSES, c, true);
	}
	
	/**
	 * Used when creating objects from file
	 */
	public RecurringExpense(String name, String value, String occurance, Context c)
	{
		this.name = name;
		this.occurance = timePeriod.values()[Integer.parseInt(occurance)];  // TODO throws array index out of bounds and number format
		
		try
		{
			this.value = new BigDecimal(value);
		}
		catch(NumberFormatException e)
		{
			CommonFunctions.raiseFailure("Could not interpret " + value + " as a dollar value", false, null);
		}
		saveToFile(c);
	}
	
	public static ArrayList<RecurringExpense> enumerateRecurringExpenses(String fileName, Context context)
	{
		ArrayList<RecurringExpense> returnVal = new ArrayList<RecurringExpense>();
		
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
				}
				
				returnVal.add(new RecurringExpense(list[0], list[1], list[2], context));
			}
		} catch (FileNotFoundException e) {
			// Don't do anything
		}

		return returnVal;
	}
	
	public String getCSVString()
	{
		return "\"" + this.name + "\",\"" + this.value + "\",\"" + this.occurance + "\""; 
	}
	
	/**
	 * Self included method to create a CSV of an array of this object (that can be read
	 * by the enumerate method included in this object).
	 */
	public String getCSVFileString(RecurringExpense[] expenses)
	{
		StringBuilder b = new StringBuilder();
		for(int i=0; i<expenses.length; i++)
		{
			b.append(expenses[i] + "\n");
		}
		
		return b.toString();
	}
	
	/**
	 * TODO make this better later....
	 */
	public static timePeriod getTimePeriod(Spinner s)
	{
		String selected = s.getSelectedItem().toString();
		if(selected.equals("Daily"))
		{
			return timePeriod.DAILY;
		}
		else if(selected.equals("Weekly"))
		{
			return timePeriod.WEEKLY;
		}
		else if(selected.equals("Bi-Weekly"))
		{
			return timePeriod.BI_WEEKLY;
		}
		else if(selected.equals("Monthly"))
		{
			return timePeriod.MONTHLY;
		}
		else if(selected.equals("Bi-Annually"))
		{
			return timePeriod.BI_ANNUALLY;
		}
		else if(selected.equals("Annually"))
		{
			return timePeriod.ANNUALLY;
		}
		return null;
	}
}
