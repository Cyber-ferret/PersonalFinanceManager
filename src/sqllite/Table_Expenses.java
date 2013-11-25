package sqllite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Table_Expenses {
	private static final String DEFAULT_NAME = "misc";
	Context storedContext;
	
	public Table_Expenses(Context c)
	{
		storedContext = c;
	}
	
	public void addNew(double cost)
	{
		addNew(DEFAULT_NAME, cost, System.currentTimeMillis());
	}
	
	public void addNew(String category, double cost)
	{
		addNew(category, cost, System.currentTimeMillis());
	}
	
	public void addNew(String category, double cost, long dateTime)
	{
		ContentValues values = new ContentValues();
		SQLiteDatabase database = new Database(storedContext).getWritableDatabase();
		
	    values.put(Database.Expenses.CATEGORY_FIELD, category);
	    values.put(Database.Expenses.COST_FIELD, cost);
	    values.put(Database.Expenses.DATETIME_FIELD, dateTime);
	    
	    database.insert(Database.Expenses.TABLE_NAME, null, values);
	}
	
	public ArrayList<Row> getRows()
	{
		ArrayList<Row> rows = new ArrayList<Row>();
		String[] columns = {Database.Expenses.ID_FIELD, Database.Expenses.CATEGORY_FIELD, Database.Expenses.COST_FIELD, Database.Expenses.DATETIME_FIELD};
		SQLiteDatabase database = new Database(storedContext).getWritableDatabase();

	    Cursor c = database.query(Database.Expenses.TABLE_NAME, columns, null, null, null, null, null);

	    c.moveToFirst();
		while (!c.isAfterLast()) {
			Row row = new Row();
			row.ID = c.getInt(c.getColumnIndex(Database.Expenses.ID_FIELD));
			row.category = c.getString(c.getColumnIndex(Database.Expenses.CATEGORY_FIELD));
			row.cost = c.getDouble(c.getColumnIndex(Database.Expenses.COST_FIELD));
			row.datetime = c.getInt(c.getColumnIndex(Database.Expenses.DATETIME_FIELD));
			
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
		public String category;
		public double cost;
		public int datetime;
	}
}
