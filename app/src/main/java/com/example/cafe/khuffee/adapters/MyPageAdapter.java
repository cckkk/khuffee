package com.example.cafe.khuffee.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.cafe.khuffee.fragments.CouponFragment;
import com.example.cafe.khuffee.fragments.HomeFragment;
import com.example.cafe.khuffee.fragments.MenuFragment;
import com.example.cafe.khuffee.fragments.OrderFragment;

/**
 * Created by lee on 2016-05-29.
 */
public class MyPageAdapter extends FragmentPagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<>();
    public MyPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new MenuFragment();
            case 2:
                return new OrderFragment();
            case 3:
                return new CouponFragment();
        }
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = HomeFragment.name;
                break;
            case 1:
                title = MenuFragment.name;
                break;
            case 2:
                title = OrderFragment.name;
                break;
            case 3:
                title = CouponFragment.name;
                break;
        }
        return title;
    }
}