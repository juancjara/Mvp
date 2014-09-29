package com.tryhard.mvp.app.route;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by juancarlos on 28/09/14.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new SimpleMapFragment();
            case 1:
                return new SimpleMapFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
