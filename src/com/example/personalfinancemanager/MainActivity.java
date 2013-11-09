package com.example.personalfinancemanager;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Scanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public class MainActivity extends Activity {
	public static String EXTRA_MESSAGE = "f";

	private enum DataFile
	{
		BALANCE, SPENDING_BREAKDOWN, SETTINGS, SPENDING_AREAS
	}
	
	private HashMap<DataFile, String> mapping = new HashMap<DataFile, String>(){
		private static final long serialVersionUID = 1L;
		{
			put(DataFile.BALANCE, "Balance.txt");
			put(DataFile.SPENDING_BREAKDOWN, "SpendingBreakdown.txt");
			put(DataFile.SETTINGS, "Settings.txt");
			put(DataFile.SPENDING_AREAS, "SpendingAreas.txt");
		}};

	/**
	 * Default method
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * Default method
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** 
	 * Called when the user clicks the Show Balance button 
	 */
	public void showBalance(View view) {
		//Intent intent = new Intent(this, DisplayMessageActivity.class);
		//intent.putExtra("", "Your balance is currently " + EXTRA_MESSAGE);
		//startActivity(intent);
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
 
		// set title
		alertDialogBuilder.setTitle("Balance");
 
		// set dialog message
		String message = "$" + getBalance();
		alertDialogBuilder.setMessage(message).setCancelable(true);
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();
	}
	
	public void raiseFailure(String message, boolean closeApp)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		 
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
	 * gets the text field then updates the balance via the
	 * updateBalance method.  It then displays the new balance.
	 */
	public void deductFunds(View view)
	{
		//TODO add charge to a CSV
		TextView editText = (TextView) findViewById(R.id.display_value);
		String message = editText.getText().toString();
		try 
		{
			double value = Double.parseDouble(message);
			updateBalance(-value);
		}
		catch (NumberFormatException e)
		{
			// Do Nothing
		}
		showBalance(view);
	}
	
	/**
	 * gets the text view then updates the balance via the
	 * updateBalance method.  It then displays the new balance.
	 */
	public void addFunds(View view)
	{
		//TODO add charge to a CSV
		TextView editText = (TextView) findViewById(R.id.display_value);
		String message = editText.getText().toString();
		try 
		{
			int value = Integer.parseInt(message);
			updateBalance(value);
		}
		catch (NumberFormatException e)
		{
			raiseFailure("I was unable to interpret \"" + message + "\" as an Integer", false);
		}
		showBalance(view);
	}
	
	public void keyPress(View view)
	{
		Button buttonPressed = (Button) view;
		
		if(buttonPressed.getText().equals("C"))
		{
			// Delete most recent Character
			TextView editText = (TextView) findViewById(R.id.display_value);
			String newValue = editText.getText().toString();
			if(newValue.length() > 0)
			{
				newValue = newValue.substring(0, newValue.length() - 1);
				editText.setText(newValue);
			}
		}
		else
		{
			// Add the character of whatever key was pressed
			TextView editText = (TextView) findViewById(R.id.display_value);
			String newValue = editText.getText().toString();
			newValue += buttonPressed.getText();
			
			boolean cond1 = StringUtils.countMatches(newValue, ".")  <= 1;
			boolean cond2 = StringUtils.substringAfter(newValue, ".").length() <= 2;
			if(cond1 && cond2)
			{
				editText.setText(newValue);
			}
		}
	}
	
	private String getBalance()
	{
		String fileName = mapping.get(DataFile.BALANCE);
		String line = "";
		try {
			FileInputStream s = openFileInput(fileName);
			line = (new Scanner(s)).nextLine();
			return line;
		} catch (FileNotFoundException e) {
			//raiseFailure("Could not find the file " + fileName, false);
			return null;
		} catch (Exception e) {
			return null;  // No file found, just return 0
		}
	}
	
	private void updateBalance(double value)
	{
		BigDecimal newBalance = new BigDecimal(getBalance()); // Get your new balance

		newBalance = newBalance.add(new BigDecimal(value));
		
		newBalance = newBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		
		writeToFile(newBalance.toString(), mapping.get(DataFile.BALANCE));
	}
	
	private void writeToFile(String data, String fileName) {
	    try {
	    	FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
	    	fos.write(data.getBytes());
	    	fos.close();
	    }
	    catch (IOException e) {
	    	//raiseFailure("There was an IO error while trying to write to output file " + fileName, false);
	    } 
	}
}
