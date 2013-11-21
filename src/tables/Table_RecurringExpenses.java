package tables;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Table_RecurringExpenses extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "Finances.db";
	private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Recurring_Expenses";
    
    private static final String RECURRING_EXPENSE_ID = "ID";
    private static final String RECURRING_EXPENSE_NAME = "Name";
    private static final String RECURRING_EXPENSE_COST = "Cost";
    private static final String RECURRING_EXPENSE_OCCURRENCE = "Occurence";
    
    private static final String TABLE_CREATE_QUERY =
                "CREATE TABLE " + TABLE_NAME + " (" +
                RECURRING_EXPENSE_ID + " integer primary key autoincrement, " +
        		RECURRING_EXPENSE_NAME + " TEXT, " +
        		RECURRING_EXPENSE_COST + " REAL, " +
        		RECURRING_EXPENSE_OCCURRENCE + " INTEGER);";

    public Table_RecurringExpenses(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { 
        db.execSQL(TABLE_CREATE_QUERY); // Called if DB is accessed but not yet created
    }

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// called, if the database version is increased in your application code. This method 
		// allows you to update an existing database schema or to drop the existing database 
		// and recreate it via the onCreate() method. 
		// TODO Auto-generated method stub
		
	}
	
	public void addNew(String name, double cost, int occurrence)
	{
		ContentValues values = new ContentValues();
		SQLiteDatabase database = this.getWritableDatabase();
		
	    values.put(RECURRING_EXPENSE_NAME, name);
	    values.put(RECURRING_EXPENSE_COST, cost);
	    values.put(RECURRING_EXPENSE_OCCURRENCE, occurrence);
	    
	    database.insert(TABLE_NAME, null, values);
	}
	
	public ArrayList<Row> getRows()
	{
		ArrayList<Row> rows = new ArrayList<Row>();
		String[] columns = {RECURRING_EXPENSE_ID, RECURRING_EXPENSE_NAME, 
				RECURRING_EXPENSE_COST, RECURRING_EXPENSE_OCCURRENCE};
		SQLiteDatabase database = this.getWritableDatabase();

	    Cursor c = database.query(TABLE_NAME, columns, null, null, null, null, null);

	    c.moveToFirst();
		while (!c.isAfterLast()) {
			Row row = new Row();
			row.ID = c.getInt(c.getColumnIndex(RECURRING_EXPENSE_ID));
			row.name = c.getString(c.getColumnIndex(RECURRING_EXPENSE_NAME));
			row.cost = c.getDouble(c.getColumnIndex(RECURRING_EXPENSE_COST));
			row.occurrence = c.getInt(c.getColumnIndex(RECURRING_EXPENSE_OCCURRENCE));
			
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
