package tables;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Table_Expenses extends SQLiteOpenHelper {
	private static String DEFAULT_NAME = "Misc";
	public static final String DATABASE_NAME = "Finances.db";
	private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Expenses";
    
    private static final String EXPENSE_ID = "ID";
    private static final String EXPENSE_CATEGORY = "Category";
    private static final String EXPENSE_COST = "Cost";
    private static final String EXPENSE_DATETIME = "DateTime";
    
    private static final String TABLE_CREATE_QUERY =
                "CREATE TABLE " + TABLE_NAME + " (" +
                EXPENSE_ID + " integer primary key autoincrement, " +
                EXPENSE_CATEGORY + " TEXT, " +
                EXPENSE_COST + " REAL, " +
                EXPENSE_DATETIME + " INTEGER);";

    public Table_Expenses(Context context) {
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
		SQLiteDatabase database = this.getWritableDatabase();
		
	    values.put(EXPENSE_CATEGORY, category);
	    values.put(EXPENSE_COST, cost);
	    values.put(EXPENSE_DATETIME, dateTime);
	    
	    database.insert(TABLE_NAME, null, values);
	}
	
	public ArrayList<Row> getRows()
	{
		ArrayList<Row> rows = new ArrayList<Row>();
		String[] columns = {EXPENSE_ID, EXPENSE_CATEGORY, EXPENSE_COST, EXPENSE_DATETIME};
		SQLiteDatabase database = this.getWritableDatabase();

	    Cursor c = database.query(TABLE_NAME, columns, null, null, null, null, null);

	    c.moveToFirst();
		while (!c.isAfterLast()) {
			Row row = new Row();
			row.ID = c.getInt(c.getColumnIndex(EXPENSE_ID));
			row.category = c.getString(c.getColumnIndex(EXPENSE_CATEGORY));
			row.cost = c.getDouble(c.getColumnIndex(EXPENSE_COST));
			row.datetime = c.getInt(c.getColumnIndex(EXPENSE_DATETIME));
			
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
