package sqllite;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "FinanceManager.db";
	private static final int DATABASE_VERSION = 2;

	public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(generateTableCreateString(ADDITIONAL_FUNDS_NAME, additionalFundsFields));
    	db.execSQL(generateTableCreateString(ExpenseCategories.TABLE_NAME, ExpenseCategories.fields));
    	db.execSQL(generateTableCreateString(Expenses.TABLE_NAME, Expenses.fields));
    	db.execSQL(generateTableCreateString(RecurringExpenses.TABLE_NAME, RecurringExpenses.fields));
    }

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// called, if the database version is increased in your application code. This method 
		// allows you to update an existing database schema or to drop the existing database 
		// and recreate it via the onCreate() method. 
		// TODO Auto-generated method stub
	}
	
	/*private static final String ADDITIONAL_FUNDS_NAME = "Additional_Funds";
	HashMap<String, String> additionalFundsFields = new HashMap<String, String>()
	{ private static final long serialVersionUID = 1L; {
		put("ID", "integer primary key autoincrement");
		put("Name", "TEXT");
	}};*/
	
	public static final class ExpenseCategories
	{
		public static final String TABLE_NAME = "Expense_Categories";
		public static final String ID_FIELD = "ID";
		public static final String NAME_FIELD = "Name";
		
		public static final HashMap<String, String> fields = new HashMap<String, String>()
		{ private static final long serialVersionUID = 1L; {
			put(ID_FIELD, "integer primary key autoincrement");
			put(NAME_FIELD, "TEXT");
		}};
	}
	
	public static final class Expenses
	{
		public static final String TABLE_NAME = "Expenses";
		public static final String ID_FIELD = "ID";
		public static final String CATEGORY_FIELD = "Category";
		public static final String COST_FIELD = "Cost";
		public static final String DATETIME_FIELD = "DateTime";
		
		public static final HashMap<String, String> fields = new HashMap<String, String>()
		{ private static final long serialVersionUID = 1L; {
			put(ID_FIELD, "integer primary key autoincrement");
			put(CATEGORY_FIELD, "TEXT");
			put(COST_FIELD, "REAL");
			put(DATETIME_FIELD, "INTEGER");
		}};
	}
	
	public static final class RecurringExpenses
	{
		public static final String TABLE_NAME = "Recurring_Expenses";
		public static final String ID_FIELD = "ID";
		public static final String NAME_FIELD = "Name";
		public static final String COST_FIELD = "Cost";
		public static final String OCCURRENCE_FIELD = "Occurrence";
		
		public static final HashMap<String, String> fields = new HashMap<String, String>()
		{ private static final long serialVersionUID = 1L; {
			put(ID_FIELD, "integer primary key autoincrement");
			put(NAME_FIELD, "TEXT");
			put(COST_FIELD, "REAL");
			put(OCCURRENCE_FIELD, "INTEGER");
		}};
	}
	
	private String generateTableCreateString(String tableName, HashMap<String, String> fields)
	{
		StringBuilder b = new StringBuilder();
		Iterator<?> iterator = fields.entrySet().iterator();
		
		b.append("CREATE TABLE " + tableName + " (");
		
		while(iterator.hasNext())
		{
			Map.Entry<?,?> pairs = (Map.Entry<?,?>)iterator.next();
			String ending = (iterator.hasNext()) ? ", " : ");" ;
			b.append(pairs.getKey() + " " + pairs.getValue() + ending);
		}
		
		return b.toString();
	}
	
	public Cursor query(String tableName, String[] columns)
	{
		try
		{
			return this.getWritableDatabase().rawQuery("Select * FROM " + tableName, null);
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
