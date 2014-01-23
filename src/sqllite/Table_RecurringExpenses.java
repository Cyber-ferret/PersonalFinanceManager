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
	
	public ArrayList<Row> getRows()
	{
		ArrayList<Row> rows = new ArrayList<Row>();
		String[] columns = {Database.RecurringExpenses.ID_FIELD, Database.RecurringExpenses.NAME_FIELD, 
				Database.RecurringExpenses.COST_FIELD, Database.RecurringExpenses.OCCURRENCE_FIELD};

		Database db = new Database(storedContext);

		Cursor c = db.query(Database.RecurringExpenses.TABLE_NAME, columns);

	    c.moveToFirst();
		while (!c.isAfterLast()) {
			Row row = new Row();
			row.ID = c.getInt(c.getColumnIndex(Database.RecurringExpenses.ID_FIELD));
			row.name = c.getString(c.getColumnIndex(Database.RecurringExpenses.NAME_FIELD));
			row.cost = c.getDouble(c.getColumnIndex(Database.RecurringExpenses.COST_FIELD));
			row.occurrence = c.getInt(c.getColumnIndex(Database.RecurringExpenses.OCCURRENCE_FIELD));
			row.dayDue = c.getInt(c.getColumnIndex(Database.RecurringExpenses.DAY_DUE));
			row.monthDue = c.getInt(c.getColumnIndex(Database.RecurringExpenses.MONTH_DUE));
			row.yearDue = c.getInt(c.getColumnIndex(Database.RecurringExpenses.YEAR_DUE));
			
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
		public int occurrence;	// Spinner Selection Position
		public int dayDue; 		// Spinner Selection Position
		public int monthDue;	// Spinner Selection Position
		public int yearDue;		// Spinner Selection Position
	}
	
	public long addNew(String name, double cost, int occurrence, int day, int month, int year)
	{
		ContentValues values = new ContentValues();
		SQLiteDatabase database = new Database(storedContext).getWritableDatabase();
		
	    values.put(Database.RecurringExpenses.NAME_FIELD, name);
	    values.put(Database.RecurringExpenses.COST_FIELD, cost);
	    values.put(Database.RecurringExpenses.OCCURRENCE_FIELD, occurrence);
	    values.put(Database.RecurringExpenses.DAY_DUE, day);
	    values.put(Database.RecurringExpenses.MONTH_DUE, month);
	    values.put(Database.RecurringExpenses.YEAR_DUE, year);
	    
	    return database.insert(Database.RecurringExpenses.TABLE_NAME, null, values);
	}

	public void update(long ID, String newName, String newCost, int newOccurrence, int newDay, int newMonth, int newYear) {
		ContentValues values = new ContentValues();
		SQLiteDatabase database = new Database(storedContext).getWritableDatabase();
		
		String whereClause = Database.RecurringExpenses.ID_FIELD + "=" + ID;
	    values.put(Database.RecurringExpenses.NAME_FIELD, newName);
	    values.put(Database.RecurringExpenses.COST_FIELD, newCost);
	    values.put(Database.RecurringExpenses.OCCURRENCE_FIELD, newOccurrence);
	    values.put(Database.RecurringExpenses.DAY_DUE, newDay);
	    values.put(Database.RecurringExpenses.MONTH_DUE, newMonth);
	    values.put(Database.RecurringExpenses.YEAR_DUE, newYear);
	    
		database.update(Database.RecurringExpenses.TABLE_NAME, values, whereClause, null);
	}

	public void delete(long ID) {
		SQLiteDatabase database = new Database(storedContext).getWritableDatabase();
		String whereClause = Database.RecurringExpenses.ID_FIELD + "=" + ID;
		database.delete(Database.RecurringExpenses.TABLE_NAME, whereClause, null);
	}
	
	public Row getSingle(long ID)
	{
		ArrayList<Row> rows = getRows();
		
		for (int i=0; i<rows.size(); i++)
			if(rows.get(i).ID == ID)
				return rows.get(i);
		
		return null;
	}
}
