package com.github.zhukic.weekview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements WeekView.WeekEventClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeekView weekView = (WeekView) findViewById(R.id.weekView);

        WeekEvent weekEvent1 = new WeekEvent("Mistsubishi", 11, 14, 0);
        WeekEvent weekEvent2 = new WeekEvent("WATT", 18, 20, 3);
        WeekEvent weekEvent3 = new WeekEvent("Timber", 15, 17, 5);

        weekView.setWeekEvents(Arrays.asList(weekEvent1, weekEvent2, weekEvent3));
        weekView.setWeekEventClickListener(this);

    }

    @Override
    public void onWeekEventClick(WeekEvent weekEvent) {
        Toast.makeText(this, weekEvent.getName(), Toast.LENGTH_SHORT).show();
    }

}
