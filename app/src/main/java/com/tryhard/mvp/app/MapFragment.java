package com.tryhard.mvp.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by andreq on 9/26/14.
 */
public class MapFragment extends Fragment {

    private BusStopListener fromListener;
    private BusStopListener toListener;

    public MapFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment, container, false);
        AutoCompleteTextView fromAutoComplete =
                (AutoCompleteTextView) v.findViewById(R.id.map_fragment_from_auto_complete);
        AutoCompleteTextView toAutoComplete =
                (AutoCompleteTextView) v.findViewById(R.id.map_fragment_to_auto_complete);

        fromListener = new BusStopListener();
        toListener = new BusStopListener();
        setAutoCompleteAdapter(fromAutoComplete, fromListener);
        setAutoCompleteAdapter(toAutoComplete, toListener);
        return v;
    }

    private void setAutoCompleteAdapter(AutoCompleteTextView view,
                                        BusStopListener busStopListener) {
        BusStopAutoCompleteAdapter adapter =
               new BusStopAutoCompleteAdapter(view.getContext(),
                                              android.R.layout.simple_list_item_1);
        view.setAdapter(adapter);
        view.setOnItemClickListener(busStopListener);
        view.addTextChangedListener(busStopListener);
    }

    final class BusStopAutoCompleteAdapter extends ArrayAdapter<String> {

        private ArrayList<String> results = new ArrayList<String>();

        public BusStopAutoCompleteAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public String getItem(int position) {
            return results.get(position);
        }

        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        results.clear();
                        for (int i = 0; i < 10; i++) {
                             results.add(constraint + " " + i);
                        }
                        filterResults.values = results;
                        filterResults.count = results.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null || results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

    final class BusStopListener implements AdapterView.OnItemClickListener, TextWatcher {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String selected = (String)parent.getItemAtPosition(position);
            Toast.makeText(view.getContext(), selected, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {}
    }

}
