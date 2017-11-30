package com.example.app_ex3;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * A placeholder fragment containing a simple view.
 */
public class Rick_or_morty_activityFragment extends Fragment {

    private static final List<String> Morty = asList("morty1", "morty2", "morty3");
    private static final List<String> Rick = asList("rick1", "rick2", "rick3");


    public Rick_or_morty_activityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rick_or_morty_activity, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rick_and_morty_recyclerView);
        String result = (String) getActivity().getIntent().getSerializableExtra("fragmetToLoad");
        System.out.println(result);
        myAdapter chosen = new myAdapter(getContext(), result.equals("rick") ? Rick : Morty);

        recyclerView.setAdapter(chosen);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
