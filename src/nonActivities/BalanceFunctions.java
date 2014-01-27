package nonActivities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Scanner;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

public class BalanceFunctions {
	public static String BALANCE_FILE_LOCATION = "Balance.txt";
	
	/**
	 * gets the text field then updates the balance via the
	 * updateBalance method.  It then displays the new balance.
	 */
	public static boolean deductFunds(String message, View view, Context context)
	{
		try 
		{
			BigDecimal value = new BigDecimal(message);
			BigDecimal balance = getBalance(context);
			updateBalance(balance.subtract(value), context);
			return true;
		}
		catch (NumberFormatException e)
		{
			raiseFailure("Could not interpret " +  message + " as a dollar amount.", false, context);
			return false;
		}
	}
	
	/**
	 * gets the text view then updates the balance via the
	 * updateBalance method.  It then displays the new balance.
	 */
	public static boolean addFunds(String message, Context context)
	{
		try 
		{
			BigDecimal value = new BigDecimal(message);
			BigDecimal balance = getBalance(context);
			updateBalance(balance.add(value), context);
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
	
	public static BigDecimal getBalance(Context context)
	{
		String line = "";
		try {
			FileInputStream s = context.openFileInput(BALANCE_FILE_LOCATION);
			line = (new Scanner(s)).nextLine();
			return new BigDecimal(line);
		} catch (Exception e) {
			return new BigDecimal(0);
		}
	}
	
	private static void updateBalance(BigDecimal newAmount, Context c)
	{
		newAmount = newAmount.setScale(2, BigDecimal.ROUND_FLOOR);
		FileOutputStream outputStream;

		try {
			outputStream = c.openFileOutput(BALANCE_FILE_LOCATION, Context.MODE_PRIVATE);
			outputStream.write((newAmount+"").getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
