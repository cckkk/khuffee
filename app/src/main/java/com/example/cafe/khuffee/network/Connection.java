package com.example.cafe.khuffee.network;

import android.os.Handler;
import android.util.Log;

import classes.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by lee on 2016-06-04.
 */
public class Connection extends Thread {
    private static final String END_MESSAGE = "THEEND";

    private String ip;
    private int port;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private String message;
    private ArrayList<Object> sendBuffer;
    private ArrayList<Object> receiveBuffer;

    public Connection(String ip, int port) {
        this.ip = ip;
        this.port = port;
        sendBuffer = new ArrayList<>();
        receiveBuffer = new ArrayList<>();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void pushSendBuffer(Object o) {
        sendBuffer.add(o);
    }

    public boolean hasNext() {
        if(receiveBuffer.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }

    public Object popReceiveBuffer() {
        if(hasNext()) {
            Object o = receiveBuffer.get(0);
            receiveBuffer.remove(0);
            return o;
        }
        return null;
    }

    @Override
    public void run() {
        super.run();

        try {
            if (message == null || message.isEmpty()) {
                Log.e("Connection", "message is none");
                return;
            }

            socket = new Socket(ip, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());
            Log.e("Connection", "connected to server");

            oos.writeUTF(message);

            while(!sendBuffer.isEmpty()) {
                oos.writeObject(sendBuffer.get(0));
                sendBuffer.remove(0);
            }

            Object received;
            while(true) {
                received = ois.readObject();
                if(received instanceof User) {
                    Log.e("Connection", ((User) received).getmId());
                }
                else if(((String)received).compareTo(END_MESSAGE) == 0) {
                    break;
                }
                receiveBuffer.add(received);
            }
            oos.writeUTF(END_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
                if(socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
