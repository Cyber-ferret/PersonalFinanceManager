package info;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Scanner;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.personalfinancemanager.R;

public class SavingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_savings);
		
		EditText t = (EditText) this.findViewById(R.id.savings);
		t.setText(this.getSavings().toString());
	}
	
	public void updateSavings(View view)
	{
		EditText t = (EditText) this.findViewById(R.id.savings);
		
		try
		{
			String val = t.getText().toString();
			BigDecimal amount = new BigDecimal(val);
			writeToFile(amount);
			
			Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.text_pop_up);
			TextView text = (TextView)dialog.findViewById(R.id.message_text);
			text.setText("Updated Savings to $" + amount + ".");
			dialog.show();
		} catch (Exception e) {
			
		}
	}

	public void writeToFile(BigDecimal savings)
	{
		String filename = "Savings.txt";
		FileOutputStream outputStream;
		
		savings = savings.setScale(2, BigDecimal.ROUND_FLOOR);

		try {
			outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write((savings+"").getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BigDecimal getSavings()
	{
		String filename = "Savings.txt";

		try {
			FileInputStream s = this.openFileInput(filename);
			return new BigDecimal((new Scanner(s)).nextLine());
		} catch (Exception e) {
			return new BigDecimal(0);
		}
	}

}
