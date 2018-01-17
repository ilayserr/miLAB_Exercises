package com.example.ex9;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main_screen = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView time_slot = (TextView) main_screen.findViewById(R.id.time_slot);

        Button button_search = (Button) main_screen.findViewById(R.id.button_refresh);
        button_search.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                final ProgressDialog loading = new ProgressDialog(view.getContext());
                loading.setMessage("getting the time, please wait...");
                loading.show();
                final RequestQueue queue = Volley.newRequestQueue(getContext());
                String url = "https://ex-milab.herokuapp.com/getTime";

                /*
                // Uncomment to use local host
                url = "http://10.0.2.2:5000/getTime";
                */
                StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("MainActivityFragment", "Response - " + response);
                        loading.hide();
                        time_slot.setText(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivityFragment", "Encountered error - " + error);
                    }
                });
                queue.add(req);

            }
        });

        return main_screen;
    }

}
