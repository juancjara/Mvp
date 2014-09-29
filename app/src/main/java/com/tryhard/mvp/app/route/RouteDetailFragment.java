package com.tryhard.mvp.app.route;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tryhard.mvp.app.R;
import com.tryhard.mvp.app.TouchImageView;

/**
 * Created by juancarlos on 25/09/14.
 */

public class RouteDetailFragment extends Fragment implements ActionBar.TabListener {
    public final static String KEY_SEL = "key";
    public final static String TEXT = "text";
    private String mText = "";
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;

    public RouteDetailFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mText = getArguments().getString(TEXT);

        View v = inflater.inflate(R.layout.route_detail_fragment, container, false);


        viewPager = (ViewPager) v.findViewById(R.id.view_pager);
        mAdapter = new TabsPagerAdapter(getFragmentManager());
        viewPager.setAdapter(mAdapter);
        actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * on swipe select the respective tab
             * */
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) { }

            @Override
            public void onPageScrollStateChanged(int arg0) { }
        });

        //Add New Tab
        actionBar.addTab(actionBar.newTab().setText("Mapa").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Horario").setTabListener(this));
        return v;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) { }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) { }
}
