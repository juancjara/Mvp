package com.tryhard.mvp.app.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.tryhard.mvp.app.R;
import com.tryhard.mvp.app.structs.Tweet;

import java.util.ArrayList;

/**
 * Created by juancarlos on 09/10/14.
 */
public class TweetAdapter extends BaseAdapter{
    public final Context context;
    public final ArrayList<Tweet> values;

    public TweetAdapter(Context context, ArrayList<Tweet> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;

        if (convertView == null) {

            view = new View(context);

            // get layout from mobile.xml
            view = inflater.inflate(R.layout.feeder_item, null);
            Tweet item = values.get(position);
            // set value into textview
            TextView tvAuthor = (TextView) view
                    .findViewById(R.id.feeder_item_author);
            tvAuthor.setText(item.author);
            TextView tvText = (TextView) view
                    .findViewById(R.id.feeder_item_text);
            tvText.setText(item.text);
            TextView tvTime = (TextView) view
                    .findViewById(R.id.feeder_item_time);
            tvTime.setText(item.dateTime);

        } else {
            view = (View) convertView;
        }

        return view;
    }
}
