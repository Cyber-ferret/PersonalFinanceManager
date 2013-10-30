package com.example.personalfinancemanager;

import com.example.personalfinancemanager.MainActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.SuppressLint;
import android.content.Intent;

public class DisplayMessageActivity extends Activity {

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        // Set the text view as the activity layout
        setContentView(textView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
