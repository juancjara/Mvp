package com.tryhard.mvp.app.map;

import android.os.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.tryhard.mvp.app.BaseActivity;
import com.tryhard.mvp.app.R;

/**
 * Created by andreq on 9/26/14.
 */
public class MapActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.content_frame);
        Fragment fragment = new MapFragment();
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        HandlerThread thread = new HandlerThread("churrehandler");
        thread.start();
        Looper looper = thread.getLooper();
        Handler handler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                try {
                    switch (msg.what) {
                        case 0:
                            Thread.sleep(10000);
                            System.out.println("happy 10 segs");
                            break;
                        case 1:
                            Thread.sleep(5000);
                            System.out.println("happy 5 segs");
                            break;
                    }
                } catch (Exception e) {
                    // should not happen
                }
            }
        };
        handler.sendMessage(handler.obtainMessage(0));
        System.out.println("gg wp 0");
        handler.sendMessage(handler.obtainMessage(1));
        System.out.println("gg wp 1");
    }
}
