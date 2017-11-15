package com.example.app_ex3;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main_screen = inflater.inflate(R.layout.fragment_main, container, false);

        Button rick = (Button) main_screen.findViewById(R.id.rick);
        Button morty = (Button) main_screen.findViewById(R.id.morty);
        rick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Rick_or_morty_activity.class);
                intent.putExtra("fragmetToLoad", "rick");
                startActivity(intent);
            }
        });
        morty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Rick_or_morty_activity.class);
                intent.putExtra("fragmetToLoad", "morty");
                startActivity(intent);
            }
        });

        return main_screen;
    }
}
