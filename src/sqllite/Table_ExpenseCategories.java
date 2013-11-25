package sqllite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Table_ExpenseCategories {
	Context storedContext;
	
	public Table_ExpenseCategories(Context c)
	{
		storedContext = c;
	}
	
	public long addNew(String name)
	{
		ContentValues values = new ContentValues();
		SQLiteDatabase database = new Database(storedContext).getWritableDatabase();
		
	    values.put(Database.ExpenseCategories.NAME_FIELD, name);
	    
	    return database.insert(Database.ExpenseCategories.TABLE_NAME, null, values);
	}
	
	public void update(long ID, String newName)
	{
		ContentValues values = new ContentValues();
		SQLiteDatabase database = new Database(storedContext).getWritableDatabase();
		
		String whereClause = Database.ExpenseCategories.ID_FIELD + "=" + ID;
	    values.put(Database.ExpenseCategories.NAME_FIELD, newName);
	    
		database.update(Database.ExpenseCategories.TABLE_NAME, values, whereClause, null);
	}
	
	public void delete(long ID)
	{
		SQLiteDatabase database = new Database(storedContext).getWritableDatabase();
		String whereClause = Database.ExpenseCategories.ID_FIELD + "=" + ID;
		database.delete(Database.ExpenseCategories.TABLE_NAME, whereClause, null);
	}
	
	public ArrayList<Row> getRows()
	{
		ArrayList<Row> rows = new ArrayList<Row>();
		String[] columns = {Database.ExpenseCategories.ID_FIELD, Database.ExpenseCategories.NAME_FIELD};
		SQLiteDatabase database = new Database(storedContext).getWritableDatabase();

		//if(database.)
		Cursor  c = null;
		try
		{
			c = database.query(Database.ExpenseCategories.TABLE_NAME, columns, null, null, null, null, null);
		}
		catch (Exception e)
		{
			System.out.println("Asdf");
		}

	    c.moveToFirst();
		while (!c.isAfterLast()) {
			Row row = new Row();
			row.ID = c.getInt(c.getColumnIndex(Database.ExpenseCategories.ID_FIELD));
			row.categoryName = c.getString(c.getColumnIndex(Database.ExpenseCategories.NAME_FIELD));
			
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
