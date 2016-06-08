package com.example.cafe.khuffee.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.cafe.khuffee.R;
import com.example.cafe.khuffee.activities.MainActivity;

import classes.User;

/**
 * Created by lee on 2016-05-29.
 */
public class CouponFragment extends Fragment implements View.OnClickListener{
    public static final String name = "Coupon";
    private User user;
    private boolean flg;
    private LinearLayout lnStranger;
    private Button btnSignin;
    private Button btnSignup;

    private LinearLayout lnUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lnStranger = (LinearLayout)view.findViewById(R.id.ln_stranger_c);
        lnUser = (LinearLayout)view.findViewById(R.id.ln_user_c);

        if(((MainActivity)getActivity()).getUser() == null) {
            ((ViewGroup) view).removeView(lnUser);
            btnSignin = (Button)view.findViewById(R.id.btn_sign_in_c);
            btnSignup = (Button)view.findViewById(R.id.btn_sign_up_c);
            btnSignin.setOnClickListener(this);
            btnSignup.setOnClickListener(this);
        }
        else {
            ((ViewGroup) view).removeView(lnStranger);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_sign_in_c) {
            ((MainActivity) getActivity()).getMyDialog().show();
        }
        else if(v.getId() == R.id.btn_sign_up_c) {
            ((MainActivity)getActivity()).startSignupActivity();
        }

    }
}
