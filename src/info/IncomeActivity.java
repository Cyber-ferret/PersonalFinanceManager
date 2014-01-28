package info;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

import com.example.personalfinancemanager.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class IncomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_income);
		
		EditText t = (EditText) this.findViewById(R.id.income);
		t.setText(getIncome(this)); // Set the income (defaults to 0)
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
			
			Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.text_pop_up);
			TextView text = (TextView)dialog.findViewById(R.id.message_text);
			text.setText("Updated Income to $" + amount + ".");
			dialog.show();
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
	
	public static String getIncome(Context c)
	{
		String filename = "income.txt";

		try {
			FileInputStream s = c.openFileInput(filename);
			return (new Scanner(s)).nextLine();
		} catch (Exception e) {
			return "0";
		}
	}
	
}
