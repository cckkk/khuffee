package com.example.cafe.khuffee.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cafe.khuffee.R;
import com.example.cafe.khuffee.activities.MainActivity;
import classes.Menuitem;

import java.util.ArrayList;



/**
 * Created by lee on 2016-05-29.
 */
public class MenuFragment extends Fragment {
    public static final String name = "Menu";
    private ArrayList<Menuitem> menuitems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_menu, container, false);
        menuitems = new ArrayList<>();
        menuitems = ((MainActivity) getActivity()).getMenuitems();
        return view;
    }
}
