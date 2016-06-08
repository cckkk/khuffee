package com.example.cafe.khuffee.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import com.example.cafe.khuffee.R;
import com.example.cafe.khuffee.adapters.MyPageAdapter;
import com.example.cafe.khuffee.dialogs.MyDialog;
import com.example.cafe.khuffee.dialogs.MyDialogListener;
import com.example.cafe.khuffee.network.Connection;

import java.util.ArrayList;

import classes.Menuitem;
import classes.User;


public class MainActivity extends FragmentActivity {
    private static final int code_FROM_SIGNUP_ACTIVITY = 90;        //SignuoActivity code

    private User user;
    private ArrayList<Menuitem> menuitems;
    private ViewPager viewPager;
    private MyPageAdapter pagerAdapter;
    private PagerTabStrip pagerTabStrip;

    private MyDialog signinDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SplashActivity로부터 온 data 받기
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        menuitems = (ArrayList<Menuitem>)intent.getSerializableExtra("menuitems");

        //MainActivity swipe view setting
        pagerAdapter = new MyPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        //MainActivity tab setting
        pagerTabStrip = (PagerTabStrip)findViewById(R.id.pager_tab);
        pagerTabStrip.setTextColor(Color.WHITE);

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Menuitem> getMenuitems() {
        return menuitems;
    }

    public MyDialog getMyDialog() {
        if(signinDialog == null) {
            signinDialog = new MyDialog(MainActivity.this, "Sign in");

            //다이어로그 interface 구현
            signinDialog.setDialogListener(new MyDialogListener() {
                @Override
                public void cancelDialog() {
                }

                @Override
                public void signinDialog(User user) {
                    MainActivity.this.setUser(user);
                    refreshFragment();
                }

                @Override
                public void signupDialog() {
                    startSignupActivity();
                }
            });
        }
        return signinDialog;
    }

    public void startSignupActivity() {
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivityForResult(intent, code_FROM_SIGNUP_ACTIVITY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case  code_FROM_SIGNUP_ACTIVITY:
                user = (User)data.getSerializableExtra("user");
                refreshFragment();
                break;
        }
    }

    private void refreshFragment() {
        Fragment fr = pagerAdapter.getRegisteredFragment(2);                        //order fragment
        Fragment fr2 = pagerAdapter.getRegisteredFragment(3);                       //coupon fragment

        //fragment refresh
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(fr);
        ft.attach(fr);
        ft.detach(fr2);
        ft.attach(fr2);
        ft.commit();
    }
}
