package com.tryhard.mvp.app.map;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.mapbox.mapboxsdk.events.MapListener;
import com.mapbox.mapboxsdk.events.ScrollEvent;
import com.mapbox.mapboxsdk.events.ZoomEvent;
import com.mapbox.mapboxsdk.overlay.PathOverlay;
import com.mapbox.mapboxsdk.views.MapView;
import com.tryhard.mvp.app.R;
import com.tryhard.mvp.app.structs.BusStop;
import com.tryhard.mvp.app.structs.Path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by andreq on 9/26/14.
 */
public class MapFragment extends Fragment {

    private BusStopListener fromListener;
    private BusStopListener toListener;
    private RouteManager routeManager;
    private RouteSearchListener routeSearchListener;
    public MapFragment() {}
    ResourceManager.ResultListener<List<ResourceManager.Route>> resultCallback;

    interface RouteSearchListener {
        void onSearchDisplayRequest(List<ResourceManager.Route> routes);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        routeSearchListener = (MapActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment, container, false);
        final AutoCompleteTextView fromAutoComplete =
                (AutoCompleteTextView) v.findViewById(R.id.map_fragment_from_auto_complete);
        final AutoCompleteTextView toAutoComplete =
                (AutoCompleteTextView) v.findViewById(R.id.map_fragment_to_auto_complete);
        MapView mapView = (MapView)v.findViewById(R.id.map_fragment_map_view);
        fromListener = new BusStopListener();
        toListener = new BusStopListener();
        routeManager = new RouteManager(mapView);

        ResourceManager.getInstance().getBusStops(new ResourceManager.ResultListener<Collection<BusStop>>() {
            @Override
            public void callback(boolean error, final Collection<BusStop> busStops) {
                if (error) return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        routeManager.drawBusStops(busStops);
                    }
                });
            }
        });

        routeManager.setBusStopTapListener(new RouteManager.BusStopTapListener() {
            @Override
            public boolean onSingleTap(BusStop busStop) {
                boolean fromFocused = fromAutoComplete.isFocused();
                boolean toFocused = toAutoComplete.isFocused();
                BusStopListener listener = null;
                if (fromFocused) {
                    listener = fromListener;
                } else if (toFocused) {
                    listener = toListener;
                }
                if (listener != null) {
                    listener.setSelection(busStop);
                }
                return false;
            }
        });

        resultCallback = new ResourceManager.ResultListener<List<ResourceManager.Route>>() {
            @Override
            public void callback(boolean error, final List<ResourceManager.Route> resource) {
                if (error) {
                    Toast.makeText(getActivity(),
                            "Ruta no encontrada",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            routeSearchListener.onSearchDisplayRequest(resource);
                        }
                    });
                }
            }
        };

        routeManager.centerMap();
        Button search = (Button)v.findViewById(R.id.map_fragment_search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if (!fromListener.validSelection() ||
                    !toListener.validSelection()) {
                    Toast.makeText(getActivity(), "Seleccione los paraderos", Toast.LENGTH_SHORT);
                    return;
                }
                ResourceManager.getInstance().getPath(
                        fromListener.selection.id,
                        toListener.selection.id,
                        resultCallback
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
        busStopListener.setView(view);
        view.setAdapter(adapter);
        view.setOnItemClickListener(busStopListener);
        view.addTextChangedListener(busStopListener);
    }

    final class BusStopAutoCompleteAdapter extends ArrayAdapter<BusStop> {

        private Object mLock = new Object();
        private Filter mFilter;
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
            if (mFilter == null) {
                mFilter = new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        FilterResults filterResults = new FilterResults();
                        if (constraint != null) {
                            synchronized (mLock) {
                                results =
                                        ResourceManager.getInstance().getBusStopMatches(constraint.toString());
                                filterResults.values = results;
                                filterResults.count = results.size();
                            }
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
            }
            return mFilter;
        }
    }

    final class BusStopListener implements AdapterView.OnItemClickListener, TextWatcher {

        public BusStop selection;
        public AutoCompleteTextView view;
        void clearSelection() {
            selection = null;
        }
        public void setSelection(BusStop busStop) {
            selection = busStop;
            view.setText(busStop.title);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BusStop selected = (BusStop)parent.getItemAtPosition(position);
            setSelection(selected);
            Toast.makeText(view.getContext(), selected.title, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (validSelection() && s.toString().compareTo(selection.title) != 0) {
                clearSelection();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}

        public boolean validSelection() {
            return selection != null;
        }

        public void setView(AutoCompleteTextView view) {
            this.view = view;
        }
    }



}
