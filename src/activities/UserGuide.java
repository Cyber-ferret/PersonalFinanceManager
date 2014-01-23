package activities;

import com.example.personalfinancemanager.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UserGuide extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_guide);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_guide, menu);
		return true;
	}

}
