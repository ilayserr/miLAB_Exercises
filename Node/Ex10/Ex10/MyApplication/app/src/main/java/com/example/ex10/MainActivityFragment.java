package com.example.ex10;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    //public static final String SERVER_URL = "https://ex10-milab.herokuapp.com/";
    Button getNotificationsButton;
    EditText nameOfStockEtidText;
    TextView stockQuotesTextView;
    private  Socket mSocket;

    public MainActivityFragment() {
    }

//    private Socket mSocket;
//    {
//        try {
//            mSocket = IO.socket(SERVER_URL);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        Log.d("MainActivityFragment", "socket");
        StockApplication app = (StockApplication)getActivity().getApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("new quotes", onNewQuotes);
        mSocket.connect();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_main, container, false);

        return mainView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button getNotificationsButton = (Button)view.findViewById(R.id.buttonGetNotifications);
        final EditText nameOfStockEtidText = (EditText)view.findViewById(R.id.editTextStockName);
        final TextView stockQuotesTextView = (TextView)view.findViewById(R.id.textViewStockQuotes);

        getNotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stockName = nameOfStockEtidText.getText().toString().trim();
//                if (TextUtils.isEmpty(stockName)) {
//                    return;
//                }
                Log.d("MainActivityFragment", "click text - " + stockName);
                nameOfStockEtidText.setText("");
                mSocket.emit("new quotes", stockName);
            }
        });

    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {////
                    Log.d("MainActivityFragment", "run: Connection Established");
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("MainActivityFragment", "run: Connection Error");
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("MainActivityFragment", "run: Disconnected");
                }
            });
        }
    };

    private Emitter.Listener onNewQuotes = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    JSONObject data = (JSONObject) args[0];
//                    String username;
                    String message = args[0].toString();;
                    Log.d("MainActivityFragment", "Response - " + message);
//                    try {
//                        username = data.getString("username");
//                        message = data.getString("message");
//                    } catch (JSONException e) {
//                        return;
//                    }

                    stockQuotesTextView.setText(message);
                }
            });
        }
    };

    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("new quotes", onNewQuotes);
    }

}
