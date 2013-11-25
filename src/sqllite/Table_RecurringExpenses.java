package sqllite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Table_RecurringExpenses {
	Context storedContext;
	
	public Table_RecurringExpenses(Context c)
	{
		storedContext = c;
	}
	
	public void addNew(String name, double cost, int occurrence)
	{
		ContentValues values = new ContentValues();
		SQLiteDatabase database = new Database(storedContext).getWritableDatabase();
		
	    values.put(Database.RecurringExpenses.NAME_FIELD, name);
	    values.put(Database.RecurringExpenses.COST_FIELD, cost);
	    values.put(Database.RecurringExpenses.OCCURRENCE_FIELD, occurrence);
	    
	    database.insert(Database.RecurringExpenses.TABLE_NAME, null, values);
	}
	
	public ArrayList<Row> getRows()
	{
		ArrayList<Row> rows = new ArrayList<Row>();
		String[] columns = {Database.RecurringExpenses.ID_FIELD, Database.RecurringExpenses.NAME_FIELD, 
				Database.RecurringExpenses.COST_FIELD, Database.RecurringExpenses.OCCURRENCE_FIELD};

		Database db = new Database(storedContext);

		Cursor c = db.query(Database.ExpenseCategories.TABLE_NAME, columns);

	    c.moveToFirst();
		while (!c.isAfterLast()) {
			Row row = new Row();
			row.ID = c.getInt(c.getColumnIndex(Database.RecurringExpenses.ID_FIELD));
			row.name = c.getString(c.getColumnIndex(Database.RecurringExpenses.NAME_FIELD));
			row.cost = c.getDouble(c.getColumnIndex(Database.RecurringExpenses.COST_FIELD));
			row.occurrence = c.getInt(c.getColumnIndex(Database.RecurringExpenses.OCCURRENCE_FIELD));
			
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
		public String name;
		public double cost;
		public int occurrence;
	}
	
	
	/*
	 * 
	 * 	
	 * Allowed time periods which expenses can be repeated
	 *
	public static enum timePeriod
	{
		DAILY, WEEKLY, BI_WEEKLY, MONTHLY, BI_ANNUALLY, ANNUALLY
	}
	
	public static boolean alreadyExists(String name, Context c)
	{
		//TODO port
	}
	
	/**
	 * TODO make this better later....
	 *
	public static timePeriod getTimePeriod(Spinner s)
	{
		String selected = s.getSelectedItem().toString();
		if(selected.equals("Daily"))
		{
			return timePeriod.DAILY;
		}
		else if(selected.equals("Weekly"))
		{
			return timePeriod.WEEKLY;
		}
		else if(selected.equals("Bi-Weekly"))
		{
			return timePeriod.BI_WEEKLY;
		}
		else if(selected.equals("Monthly"))
		{
			return timePeriod.MONTHLY;
		}
		else if(selected.equals("Bi-Annually"))
		{
			return timePeriod.BI_ANNUALLY;
		}
		else if(selected.equals("Annually"))
		{
			return timePeriod.ANNUALLY;
		}
		return null;
	}
	 */
}
