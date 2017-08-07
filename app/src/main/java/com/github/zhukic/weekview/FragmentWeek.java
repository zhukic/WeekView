package com.github.zhukic.weekview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Arrays;

/**
 * @author Vladislav Zhukov (https://github.com/zhukic)
 */

public class FragmentWeek extends Fragment implements WeekView.WeekEventClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        WeekView weekView = (WeekView) view.findViewById(R.id.weekView);

        WeekEvent weekEvent1 = new WeekEvent("Mistsubishi", 11, 14, 0);
        WeekEvent weekEvent2 = new WeekEvent("WATT", 18, 20, 3);
        WeekEvent weekEvent3 = new WeekEvent("Timber", 15, 17, 5);

        weekView.setWeekEvents(Arrays.asList(weekEvent1, weekEvent2, weekEvent3));
        weekView.setWeekEventClickListener(this);

    }

    @Override
    public void onWeekEventClick(WeekEvent weekEvent) {
        Toast.makeText(getContext(), weekEvent.getName(), Toast.LENGTH_SHORT).show();
    }

}
