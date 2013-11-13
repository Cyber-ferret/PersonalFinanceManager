package nonActivities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

import nonActivities.RecurringExpense.timePeriod;



import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

public class CommonFunctions {
	
	private static enum DataFile
	{
		BALANCE, RECURRING_EXPENSES, EXPENSES
	}
	
	private static HashMap<DataFile, String> mapping = new HashMap<DataFile, String>(){
		private static final long serialVersionUID = 1L;
		{
			put(DataFile.BALANCE, "Balance.txt");
			put(DataFile.RECURRING_EXPENSES, "RecurringExpenses.txt");
			put(DataFile.EXPENSES, "Expenses.txt");
		}};
	
	/**
	 * gets the text field then updates the balance via the
	 * updateBalance method.  It then displays the new balance.
	 */
	public static boolean deductFunds(String message, View view, Context context)
	{
		//TODO add charge to a CSV
		try 
		{
			double value = Double.parseDouble(message);
			updateBalance(-value, context);
			return true;
		}
		catch (NumberFormatException e)
		{
			// Do Nothing
			return false;
		}
	}
	
	/**
	 * gets the text view then updates the balance via the
	 * updateBalance method.  It then displays the new balance.
	 */
	public static boolean addFunds(String message, View view, Context context)
	{
		//TODO add addition to a CSV
		try 
		{
			double value = Double.parseDouble(message);
			updateBalance(value, context);
			return true;
		}
		catch (NumberFormatException e)
		{
			//TODO show message
			return false;
		}
	}
	
	/**
	 * Pops up a dialogue stating Error: message.
	 * is closeApp is true, it will close the application.
	 */
	public static void raiseFailure(String message, boolean closeApp, Context c)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
		 
		// set title
		alertDialogBuilder.setTitle("Error");
 
		// set dialog message
		alertDialogBuilder.setMessage(message).setCancelable(true);
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();
		
		if(closeApp)
		{
			//TODO something...
		}
	}
	
	/** 
	 * Called when the user clicks the Show Balance button 
	 */
	public static void showBalance(View view, Context context) {
		//Intent intent = new Intent(this, DisplayMessageActivity.class);
		//intent.putExtra("", "Your balance is currently " + EXTRA_MESSAGE);
		//startActivity(intent);
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
 
		// set title
		alertDialogBuilder.setTitle("Balance");
 
		// set dialog message
		String message = "$" + getBalance(context);
		alertDialogBuilder.setMessage(message).setCancelable(true);
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();
	}
	
	public static String getBalance(Context context)
	{
		String fileName = mapping.get(DataFile.BALANCE);
		String line = "";
		try {
			FileInputStream s = context.openFileInput(fileName);
			line = (new Scanner(s)).nextLine();
			return line;
		} catch (FileNotFoundException e) {
			//raiseFailure("Could not find the file " + fileName, false);
			return null;
		} catch (Exception e) {
			return null;  // No file found, just return 0
		}
	}
	
	private static void updateBalance(double value, Context context)
	{
		BigDecimal newBalance = new BigDecimal(getBalance(context)); // Get your new balance

		newBalance = newBalance.add(new BigDecimal(value));
		
		newBalance = newBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		
		writeToFile(newBalance.toString(), mapping.get(DataFile.BALANCE), context);
	}
	
	public static void addRecurringExpense(String name, String value, timePeriod occurance)
	{
		
	}
	
	private static void writeToFile(String data, String fileName, Context context) {
	    try {
	    	FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
	    	fos.write(data.getBytes());
	    	fos.close();
	    }
	    catch (IOException e) {
	    	//raiseFailure("There was an IO error while trying to write to output file " + fileName, false);
	    } 
	}
	
	public static String[] parseCSVLine(String line)
	{
		String[] values = line.split("\",\"");
		int size = values.length;
		
		if(values[0].startsWith("\""))
		{
			values[0] = values[0].substring(1);
		}
		if(values[size-1].endsWith("\""))
		{
			int stringSize = values[size-1].length();
			values[size-1] = values[size-1].substring(0, stringSize-1);
		}
		
		return values;
	}
}
