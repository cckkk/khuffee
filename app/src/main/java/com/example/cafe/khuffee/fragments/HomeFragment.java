package com.example.cafe.khuffee.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cafe.khuffee.R;

/**
 * Created by lee on 2016-05-29.
 */
public class HomeFragment extends Fragment  {
    public static final String name = "Home";
    private ImageView imgKhuffee;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgKhuffee = (ImageView)view.findViewById(R.id.img_khuffee);

        Display display = view.getDisplay();
        Point size = new Point();
        display.getSize(size);

        Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.khuffee);
        int h = bitmapImage.getHeight();
        int w = bitmapImage.getWidth();
        int imgWidth = size.x;
        int imgHeight = (int)Math.floor((double)h * ((double) imgWidth / (double) w));

        Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmapImage, imgWidth, imgHeight, true);
        imgKhuffee.setImageBitmap(resizeBitmap);
    }
}
