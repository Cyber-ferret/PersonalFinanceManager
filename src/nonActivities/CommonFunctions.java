package nonActivities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;



import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

public class CommonFunctions {
	public static String BALANCE_FILE_LOCATION = "Balance.txt";
	public static String INCOME_FILE_LOCATION = "Income.txt";  //TODO add income
	
	/**
	 * gets the text field then updates the balance via the
	 * updateBalance method.  It then displays the new balance.
	 */
	public static boolean deductFunds(String message, View view, Context context)
	{
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
			raiseFailure("Could not interpret " +  message + " as a dollar amount.", false, context);
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
		String line = "";
		try {
			FileInputStream s = context.openFileInput(BALANCE_FILE_LOCATION);
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
		
		writeToFile(newBalance.toString(), BALANCE_FILE_LOCATION, context, false);
	}
	
	public static void writeToFile(String data, String fileName, Context context, boolean append) {
	    try {
	    	FileOutputStream fos;
	    	if(append && context.getFileStreamPath(fileName).exists())
	    	{
	    		// Open for appending to file
	    		fos = context.openFileOutput(fileName, Context.MODE_PRIVATE | Context.MODE_APPEND);
	    	} 
	    	else
	    	{
	    		// Write new or write over
	    		fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
	    	}
	    	fos.write(data.getBytes());
	    	fos.close();
	    }
	    catch (IOException e) {
	    	raiseFailure("There was an IO error while trying to write to output file " + fileName, false, context);
	    } 
	}
}
