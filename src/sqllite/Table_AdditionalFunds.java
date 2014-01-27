package sqllite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * This class is to track additional funds that are randomly added
 */
public class Table_AdditionalFunds {
	Context storedContext;
	
	public Table_AdditionalFunds(Context c)
	{
		storedContext = c;
	}
	
	public long addNew(double amount, String description)
	{
		ContentValues values = new ContentValues();
		SQLiteDatabase database = new Database(storedContext).getWritableDatabase();
		
	    values.put(Database.AdditionalFunds.AMOUNT, amount);
	    values.put(Database.AdditionalFunds.DESCRIPTION, description);
	    values.put(Database.AdditionalFunds.DATETIME_FIELD, System.currentTimeMillis());
	    
	    return database.insert(Database.AdditionalFunds.TABLE_NAME, null, values);
	}
	
	public ArrayList<Row> getRows()
	{
		ArrayList<Row> rows = new ArrayList<Row>();
		String[] columns = {Database.AdditionalFunds.ID_FIELD, Database.AdditionalFunds.AMOUNT, 
				Database.AdditionalFunds.DESCRIPTION, Database.AdditionalFunds.DATETIME_FIELD};
		SQLiteDatabase database = new Database(storedContext).getWritableDatabase();

		Cursor  c = null;
		try
		{
			c = database.query(Database.AdditionalFunds.TABLE_NAME, columns, null, null, null, null, null);
		}
		catch (Exception e)
		{
			System.out.println("Asdf");
		}

	    c.moveToFirst();
		while (!c.isAfterLast()) {
			Row row = new Row();
			row.ID = c.getInt(c.getColumnIndex(Database.AdditionalFunds.ID_FIELD));
			row.amount = c.getDouble(c.getColumnIndex(Database.AdditionalFunds.AMOUNT));
			row.description = c.getString(c.getColumnIndex(Database.AdditionalFunds.DESCRIPTION));
			row.dateTime = c.getLong(c.getColumnIndex(Database.AdditionalFunds.DATETIME_FIELD));
			
			rows.add(row);
			
		    c.moveToNext();
		}
	    
	    return rows;
	}

	/**
	 * This is serving as a struct to efficiently encapsulate data
	 */
	public static class Row
	{
		public int ID;
		public double amount;
		public String description;
		public long dateTime;
	}
}
