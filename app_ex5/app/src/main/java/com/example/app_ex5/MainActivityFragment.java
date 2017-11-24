package com.example.app_ex5;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main_screen = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView textView_text = (TextView) main_screen.findViewById(R.id.editText);
        final TextView mTextView = (TextView) main_screen.findViewById(R.id.label);

        Button button_search = (Button) main_screen.findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final RequestQueue queue = Volley.newRequestQueue(getContext());

                String str = textView_text.getText().toString();
                String strNoSpaces = str.replace(" ", "+");

                // API key
                String key="AIzaSyBk08Kp2r8TOIbubxyzil3avFBSFxCzwsQ";

                // Engine ID
                String cx = "004891332082346772916:rl1yg0ivyec";

                String url = "https://www.googleapis.com/customsearch/v1?q=" + strNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json&fields=items/title"; ;
                StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("MainActivityFragment", "Response - " + response);
                        String final_result = "";
                        try {
                            JSONObject results = new JSONObject(response);
                            JSONArray arrJson = results.getJSONArray("items");
                            String[] arr = new String[arrJson.length()];
                            for(int i = 0; i < arrJson.length(); i++) {
                                arr[i] = arrJson.getString(i);
                                final_result += (i+1) + ")  " + arr[i].substring(10,arr[i].length() - 2) + "\n\n";
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mTextView.setText(final_result);

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
