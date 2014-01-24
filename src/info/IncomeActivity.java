package info;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.example.personalfinancemanager.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class IncomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_income);
		
		EditText t = (EditText) this.findViewById(R.id.income);
		t.setText(this.getIncome()); // Set the income (defaults to 0)
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.income, menu);
		return true;
	}
	
	public void updateIncome(View view)
	{
		EditText t = (EditText) this.findViewById(R.id.income);
		
		try
		{
			String val = t.getText().toString();
			double amount = Double.parseDouble(val);
			writeToFile(amount);
		} catch (Exception e) {
			
		}
	}

	public void writeToFile(double amount)
	{
		String filename = "income.txt";
		FileOutputStream outputStream;

		try {
			outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write((amount+"").getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getIncome()
	{
		String filename = "income.txt";
		FileInputStream in;

		try {
		    in = openFileInput(filename);
		    String income = convertStreamToString(in);
		    in.close();        
		    return income;
		  
		} catch (Exception e) {
			return "0";
		}
	}
	
	public static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	      sb.append(line).append("\n");
	    }
	    reader.close();
	    return sb.toString();
	}
}
