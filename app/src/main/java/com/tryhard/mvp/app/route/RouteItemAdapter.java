package com.tryhard.mvp.app.route;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tryhard.mvp.app.R;
import com.tryhard.mvp.app.route.BusStopInformation;

/**
 * Created by juancarlos on 24/09/14.
 */
public class RouteItemAdapter extends BaseAdapter {
    private Context context;
    private final BusStopInformation[] values;

    public RouteItemAdapter(Context context, BusStopInformation[] values) {
        this.context = context;
        this.values = values;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.route_item, null);

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.route_item_label);
            textView.setText(values[position].getName());
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.route_item_image_view);
            imageView.setImageResource(values[position].getImageId());
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
