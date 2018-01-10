package com.example.ex10;

import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by RoniBM on 07/01/2018.
 */

public class StockApplication extends Application {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constant.SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
