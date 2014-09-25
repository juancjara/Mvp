package com.tryhard.mvp.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by juancarlos on 25/09/14.
 */

public class RouteDetailFragment extends Fragment{
    public final static String KEY_SEL = "key";
    public final static String TEXT = "text";
    private String mText = "";
    private TouchImageView image;

    public RouteDetailFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String position = getArguments().getString(KEY_SEL);
        mText = getArguments().getString(TEXT);
        View v = inflater.inflate(R.layout.route_detail_fragment, container, false);
        TextView tv = (TextView) v.findViewById(R.id.route_detail_textV);
        image = (TouchImageView) v.findViewById(R.id.route_detail_touchImageV);
        int imageId = R.drawable.m_301;
        if (Integer.parseInt(position) > 0) {
            imageId = R.drawable.map2;
        }
        image.setImageResource(imageId);
        tv.setText(mText);

        return v;
    }

}
