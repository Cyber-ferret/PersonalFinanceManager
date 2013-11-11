package com.example.personalfinancemanager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.Context;

public class RecurringExpense {
	private static final int NUM_FIELDS = 3;  // Make sure this matches the number of fields below
	String name; 			// 1
	BigDecimal value;		// 2
	timePeriod occurance;	// 3
	
	/**
	 * Allowed time periods which expenses can be repeated
	 */
	public static enum timePeriod
	{
		DAILY, WEEKLY, BI_WEEKLY, MONTHLY, BI_ANNUALLY, ANNUALLY
	}
	
	RecurringExpense(String name, String value, timePeriod occurance)
	{
		this.name = name;
		this.occurance = occurance;
		
		try
		{
			this.value = new BigDecimal(value);
		}
		catch(NumberFormatException e)
		{
			CommonFunctions.raiseFailure("Could not interpret " + value + " as a dollar value", false, null);
		}
	}
	
	public static RecurringExpense[] enumerateRecurringExpenses(String fileName, Context context)
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
					return null;
				}
				
				timePeriod timePeriodFromIndex = timePeriod.values()[Integer.parseInt(list[2])];
				returnVal.add(new RecurringExpense(list[0], list[1], timePeriodFromIndex));
			}
		} catch (FileNotFoundException e) {
			CommonFunctions.raiseFailure("Could not find the file " + fileName + " while trying to read recurring expenses", false, context);
			return null;
		}
		
		return (RecurringExpense[]) returnVal.toArray();
	}
	
	public String getCSVString()
	{
		return "\"" + this.name + "\",\"" + this.value + "\",\"" + this.occurance + "\""; 
	}
	
	/**
	 * Self included method to create a CSV of an array of this object (that can be read
	 * by the enumerate method included in this object).
	 */
	public String createCSVFile(RecurringExpense[] expenses)
	{
		StringBuilder b = new StringBuilder();
		for(int i=0; i<expenses.length; i++)
		{
			b.append(expenses[i] + "\n");
		}
		
		return b.toString();
	}
}
