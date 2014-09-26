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
    public final static String  IMAGE = "image";
    private String mText = "";
    private TouchImageView image;

    public RouteDetailFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mText = getArguments().getString(TEXT);
        int imageId = getArguments().getInt(IMAGE);
        View v = inflater.inflate(R.layout.route_detail_fragment, container, false);
        TextView tv = (TextView) v.findViewById(R.id.route_detail_textV);
        image = (TouchImageView) v.findViewById(R.id.route_detail_touchImageV);
        image.setImageResource(imageId);
        tv.setText(mText);

        return v;
    }

}
