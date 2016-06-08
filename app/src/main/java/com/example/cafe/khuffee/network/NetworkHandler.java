package com.example.cafe.khuffee.network;

import com.example.cafe.khuffee.dialogs.MyDialogListener;

import classes.User;

/**
 * Created by lee on 2016-06-07.
 */
public class NetworkHandler {
    private static final String SERVER_IP = "163.180.116.169";
    private static final int SERVER_PORT = 9871;

//    private NetworkHandlerInterface networkHandlerInterface;
    private boolean flgWorking;
    private boolean flgCheckId;
    private boolean flgInsertUser;
    private boolean flgSignin;
    private User user;
    private String message;

    public NetworkHandler() {
        flgWorking = false;
    }

    public void checkID(final String id) {
        flgWorking = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection = new Connection(SERVER_IP, SERVER_PORT);
                connection.setMessage("REQ:CHECKID");
                connection.pushSendBuffer(id);
                connection.run();

                while(connection.hasNext()) {
                    if(((String)connection.popReceiveBuffer()).compareTo("EXIST") == 0) {
                        flgCheckId = true;
                    }
                    else {
                        flgCheckId = false;
                    }
                }
                flgWorking = false;
            }
        }).start();
    }

    public void insertUser(final User user) {
        flgWorking = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection = new Connection(SERVER_IP, SERVER_PORT);
                connection.setMessage("INSERT:USER");
                connection.pushSendBuffer(user);
                connection.run();

                while(connection.hasNext()) {
                    if(((String)connection.popReceiveBuffer()).compareTo("OK") == 0) {
                        flgInsertUser = true;
                    }
                    else {
                        flgInsertUser = false;
                    }
                }
                flgWorking = false;
            }
        }).start();
    }

    public void signin(final String id, final String pass) {
        flgWorking = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection = new Connection(SERVER_IP, SERVER_PORT);
                connection.setMessage("REQ:SIGNIN");
                connection.pushSendBuffer(id);
                connection.pushSendBuffer(pass);
                connection.run();

                while(connection.hasNext()) {
                    String str = (String)connection.popReceiveBuffer();
                    if(str.compareTo("ACCEPT") == 0) {
                        user = (User)connection.popReceiveBuffer();
                        flgSignin = true;
                    }
                    else if(str.compareTo("NONE") == 0) {
                        message = "존재하지 않는 아이디입니다.";
                        flgSignin = false;

                    }
                    else if(str.compareTo("REJECT") == 0) {
                        message = "비밀번호가 일치하지 않습니다.";
                        flgSignin = false;
                    }
                }
                flgWorking = false;
            }
        }).start();
    }

    public boolean isWorking() {
        return flgWorking;
    }

    public boolean getCheckId() {
        return flgCheckId;
    }

    public boolean getInsertUser() {
        return flgInsertUser;
    }

    public boolean getSignIn() {
        return flgSignin;
    }

    public String getMessge() {
        return message;
    }

    public User getUser() {
        return user;
    }
}

