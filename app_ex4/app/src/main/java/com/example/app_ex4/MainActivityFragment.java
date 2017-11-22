package com.example.app_ex4;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityFragment extends Fragment {

    int number = 5;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main_screen = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView num_minutes_textview = (TextView) main_screen.findViewById(R.id.text_num_minutes);

        Button buttonToast = (Button) main_screen.findViewById(R.id.button_num_minutes);
        buttonToast.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CharSequence num_minutes = num_minutes_textview.getText();
                try {
                    number = Integer.parseInt(num_minutes.toString());
                } catch (Exception e) {
                    Toast toast = Toast.makeText(view.getContext(), "Next time enter a number \n I chose 5", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                Intent i = MyintService.newIntent(getActivity());
                PendingIntent pi = PendingIntent.getService(getActivity(), 0, i, 0);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0,  number*1000, pi);

            }
        });
        return main_screen;

    }
}
