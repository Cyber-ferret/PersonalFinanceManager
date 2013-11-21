package tables;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Intended to hold the types of expenses one can have
 * (i.e. gas, eating out, groceries, etc)
 */
public class Table_ExpenseCategories extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "Finances.db";
	private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Expense_Categories";
    
    private static final String EXPENSE_ID = "ID";
    private static final String EXPENSE_CATEGORY_NAME = "Category_Name";
    
    private static final String TABLE_CREATE_QUERY =
                "CREATE TABLE " + TABLE_NAME + " (" +
                EXPENSE_ID + " integer primary key autoincrement, " +
                EXPENSE_CATEGORY_NAME + " TEXT);";

    public Table_ExpenseCategories(Context context) {
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
	
	public void addNew(String name)
	{
		ContentValues values = new ContentValues();
		SQLiteDatabase database = this.getWritableDatabase();
		
	    values.put(EXPENSE_CATEGORY_NAME, name);
	    
	    database.insert(TABLE_NAME, null, values);
	}
	
	public ArrayList<Row> getRows()
	{
		ArrayList<Row> rows = new ArrayList<Row>();
		String[] columns = {EXPENSE_ID, EXPENSE_CATEGORY_NAME};
		SQLiteDatabase database = this.getWritableDatabase();

	    Cursor c = database.query(TABLE_NAME, columns, null, null, null, null, null);

	    c.moveToFirst();
		while (!c.isAfterLast()) {
			Row row = new Row();
			row.ID = c.getInt(c.getColumnIndex(EXPENSE_ID));
			row.categoryName = c.getString(c.getColumnIndex(EXPENSE_CATEGORY_NAME));
			
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
		public String categoryName;
	}
}
