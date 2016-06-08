package com.example.cafe.khuffee.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cafe.khuffee.R;
import com.example.cafe.khuffee.activities.MainActivity;

import java.util.ArrayList;
import java.util.Date;

import classes.Menuitem;
import classes.Order;
import classes.User;


/**
 * Created by lee on 2016-05-29.
 */
public class OrderFragment extends Fragment implements View.OnClickListener{
    public static final String name = "Order";
    private User user;
    private boolean flg;
    private LinearLayout lnStranger;
    private Button btnSignin;
    private Button btnSignup;

    private LinearLayout lnUser;
    private Button btnOrder;

    private LinearLayout lnOrder;

    private ArrayList<Menuitem> menuitems;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       // Log.e(name, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Log.e(name, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // Log.e(name, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //Log.e(name, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Log.e(name, "onViewCreate");
        lnStranger = (LinearLayout)view.findViewById(R.id.ln_stranger_o);
        lnUser = (LinearLayout)view.findViewById(R.id.ln_user_o);

        ViewGroup orderViewGroup = (ViewGroup)view;

        if(((MainActivity)getActivity()).getUser() == null) {
            orderViewGroup.removeView(lnUser);
            btnSignin = (Button)view.findViewById(R.id.btn_sign_in_o);
            btnSignup = (Button)view.findViewById(R.id.btn_sign_up_o);
            btnSignin.setOnClickListener(this);
            btnSignup.setOnClickListener(this);
        }
        else {
            orderViewGroup.removeView(lnStranger);
//            btnOrder = (Button)view.findViewById(R.id.btn_order);
//            TextView textView1 = (TextView)view.findViewById(R.id.tv_menu1);
//            TextView textView2 = (TextView)view.findViewById(R.id.tv_menu2);
//
//            menuitems = new ArrayList<>();
//            menuitems = ((MainActivity) getActivity()).getMenuitems();
//
//            Menuitem menuitem1 = menuitems.get(4);
//            Menuitem menuitem2 = menuitems.get(40);
//
//            ArrayList<Menuitem> orderItems = new ArrayList<>();
//            orderItems.add(menuitem1);
//            orderItems.add(menuitem2);



        }

    }

    @Override
    public void onStart() {
        super.onStart();
       // Log.e(name, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
       //Log.e(name, "onResume");
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_sign_in_o) {
           ((MainActivity) getActivity()).getMyDialog().show();
        }
        else if(v.getId() == R.id.btn_sign_up_o) {
            ((MainActivity)getActivity()).startSignupActivity();
        }
    }
}
