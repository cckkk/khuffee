package com.example.cafe.khuffee.dialogs;


import classes.User;

/**
 * Created by ckkk on 2016-05-30.
 */
public  interface MyDialogListener {
    void cancelDialog();
    void signinDialog(User user);
    void signupDialog();
}
