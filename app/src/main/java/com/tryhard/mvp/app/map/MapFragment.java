package com.tryhard.mvp.app.map;

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
import com.mapbox.mapboxsdk.overlay.PathOverlay;
import com.mapbox.mapboxsdk.views.MapView;
import com.tryhard.mvp.app.R;
import com.tryhard.mvp.app.structs.BusStop;
import com.tryhard.mvp.app.structs.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreq on 9/26/14.
 */
public class MapFragment extends Fragment {

    private BusStopListener fromListener;
    private BusStopListener toListener;
    private RouteManager routeManager;
    public MapFragment() {}
    ResourceManager.ResultListener<List<Path>> resultListener;

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
        routeManager = new RouteManager((MapView)v.findViewById(R.id.map_fragment_map_view));
        resultListener =  new ResourceManager.ResultListener<List<Path>>() {
            @Override
            public void callback(boolean error, List<Path> resource) {
                if (error) {
                    Toast.makeText(getActivity(),
                            "Ruta no encontrada",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    final RouteResult routeResult = new RouteResult();
                    routeResult.pathList = resource;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            routeManager.drawResult(routeResult);
                        }
                    });
                }
            }
        };

        routeManager.centerMap();

        Button search = (Button)v.findViewById(R.id.map_fragment_search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fromListener.validSelection() ||
                    !toListener.validSelection()) {
                    Toast.makeText(getActivity(), "Seleccione los paraderos", Toast.LENGTH_SHORT);
                    return;
                }
                ResourceManager.getInstance().getPath(
                        fromListener.selection.id,
                        toListener.selection.id,
                        resultListener
                );
            }
        });
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

    final class BusStopAutoCompleteAdapter extends ArrayAdapter<BusStop> {

        private List<BusStop> results = new ArrayList<BusStop>();
        private LayoutInflater inflater;
        public BusStopAutoCompleteAdapter(Context context, int resource) {
            super(context, resource);
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                view = inflater.inflate(android.R.layout.simple_list_item_1, null);
            }
            TextView textView = (TextView) view;
            textView.setText(getItem(position).title);
            return textView;
        }

        @Override
        public BusStop getItem(int position) {
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
                        results = ResourceManager.getInstance().getBusStopMatches(constraint.toString());
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

        public BusStop selection;

        void clearSelection() {
            selection = null;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BusStop selected = (BusStop)parent.getItemAtPosition(position);
            selection = selected;
            Toast.makeText(view.getContext(), selected.title, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            clearSelection();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {}

        public boolean validSelection() {
            return selection != null;
        }
    }

}
