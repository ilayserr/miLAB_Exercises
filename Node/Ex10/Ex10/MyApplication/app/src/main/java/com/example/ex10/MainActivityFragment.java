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
    TextView coinQuotesTextView;
    private  Socket mSocket;
    private String coinName;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        Log.d("MainActivityFragment", "socket");
        CoinApplication app = (CoinApplication)getActivity().getApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("postRate", onNewQuotes);
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
        final EditText nameOfCoinEditText = (EditText)view.findViewById(R.id.editTextCoinName);
        final TextView coinQuotesTextView = (TextView)view.findViewById(R.id.textViewCoinsQuotes);

        getNotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coinName = nameOfCoinEditText.getText().toString();
                Log.d("MainActivityFragment", "the coin name is - " + coinName);
                nameOfCoinEditText.setText("");
               //mSocket.emit("new quotes", coinName);
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
                    mSocket.emit("postRate", coinName);
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

                    String message = args[0].toString();
                    Log.d("MainActivityFragment", "Response - " + message);

                    coinQuotesTextView.setText(message);
                }
            });
        }
    };

    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("postRate", onNewQuotes);
    }

}
