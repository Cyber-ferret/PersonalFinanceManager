package info;

import java.util.ArrayList;

import nonActivities.BalanceFunctions;
import sqllite.Table_ExpenseCategories;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.personalfinancemanager.R;

public class ManageExpenseCategoriesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_expense_categories);
		
		Table_ExpenseCategories t = new Table_ExpenseCategories(this);

		ArrayList<Table_ExpenseCategories.Row> rows = t.getRows();
		
		for(int i=0; i<rows.size(); i++)
		{
			this.addButton(rows.get(i).categoryName, rows.get(i).ID);
		}
	}

	TextView nameInput;
	Dialog prompt;
	public void showNewCategoryPrompt(View view)
	{
		LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(
	            LAYOUT_INFLATER_SERVICE);
	    ContextThemeWrapper mTheme = new ContextThemeWrapper(this,
	            R.style.AppTheme);

	    view = inflater.inflate(R.layout.category_prompt, null);
	    
	    nameInput = (TextView) view.findViewById(R.id.name_input);
	    
	    prompt = new Dialog(mTheme);
	    prompt.getWindow().setTitle("Category Info");
	    prompt.setContentView(view);
	    prompt.show();
	}
	
	SpecificButton categoryBeingModified;
	public void showEditCategoryPrompt(View view)
	{
		categoryBeingModified = (SpecificButton) view;
		
		LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(
	            LAYOUT_INFLATER_SERVICE);
	    ContextThemeWrapper mTheme = new ContextThemeWrapper(this,
	            R.style.AppTheme);

	    view = inflater.inflate(R.layout.category_edit_prompt, null);
	    
	    nameInput = (TextView) view.findViewById(R.id.name_input);
	    nameInput.setText(categoryBeingModified.getText());
	    
	    prompt = new Dialog(mTheme);
	    prompt.getWindow().setTitle("Category Info");
	    prompt.setContentView(view);
	    prompt.show();
	}
	
	public void addNewCategory(View view)
	{
		prompt.dismiss();  // Close our dialog box
		
	    String name = nameInput.getText().toString();
	    
	    if(name.trim().length() <= 0) {
	    	BalanceFunctions.raiseFailure("Your name field was empty.  Cannot add", this);
	    	return;
	    }
	    
	    Table_ExpenseCategories  t = new Table_ExpenseCategories(this);
	    long newID = t.addNew(name);
	    
	    addButton(name, newID);
	}
	
	public void modifyCategory(View view)
	{
		prompt.dismiss();  // Close our dialog box
		
	    String newName = nameInput.getText().toString();
	    
	    if(newName.trim().length() <= 0) {
	    	BalanceFunctions.raiseFailure("Your name field was empty.  Cannot add", this);
	    	return;
	    }
	    
	    Table_ExpenseCategories  t = new Table_ExpenseCategories(this);
	    t.update(categoryBeingModified.elementID, newName);
	    
	    categoryBeingModified.setText(newName);
	}
	
	public void deleteCategory(View view)
	{
		prompt.dismiss();  // Close our dialog box
		
		Table_ExpenseCategories  t = new Table_ExpenseCategories(this);
	    t.delete(categoryBeingModified.elementID);
	    
	    categoryBeingModified.setVisibility(View.GONE);
	}
	
	private void addButton(String name, long ID)
	{
		LinearLayout r = (LinearLayout) findViewById(R.id.manage_categories_linearlayout);
	    r.setOrientation(LinearLayout.VERTICAL);
	    SpecificButton newButton = new SpecificButton(this, ID);
	    newButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				showEditCategoryPrompt(view);
			}
	    });
	    newButton.setText(name);
	    newButton.setWidth(LayoutParams.MATCH_PARENT);
	    r.addView(newButton);
	}
	
	public class SpecificButton extends Button
	{
		public long elementID;
		public SpecificButton(Context context, long ID) {
			super(context);
			this.elementID = ID;
		}
	}
}
