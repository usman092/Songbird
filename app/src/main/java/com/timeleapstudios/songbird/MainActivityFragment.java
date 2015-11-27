package com.timeleapstudios.songbird;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private String THIS_FILE = "MainActivityFragment";

    private ListView songView;
    private ArrayList<Song> songList;

    private Interfaces.OnFragmentInteractionListener listener;
    private FragmentBroadcastReceiver fragmentBroadcastReceiver = null;

    public class FragmentBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(THIS_FILE, "MainActivity communicated with fragment");
            // Extract data included in the Intent
            String jsonMyObject;
            Bundle extras = intent.getExtras();
            if (extras != null) {
                jsonMyObject = extras.getString("song_list");
                songList = new Gson().fromJson(jsonMyObject, new TypeToken<ArrayList<Song>>(){}.getType());
                if(songList !=null){
                    // Sort the songs alphabetically
                    Collections.sort(songList, new Comparator<Song>() {
                        public int compare(Song a, Song b) {
                            return a.getTitle().compareTo(b.getTitle());
                        }
                    });

                    listener.onSongListPopulated(songList);

                    SongAdapter songAdt = new SongAdapter(context, songList, getActivity());
                    songView.setAdapter(songAdt);
                }
            }
        }
    }

    public MainActivityFragment() {
        if(fragmentBroadcastReceiver == null){
            fragmentBroadcastReceiver = new FragmentBroadcastReceiver();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach(){
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(fragmentBroadcastReceiver);
        //register
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        songView = (ListView) v.findViewById(R.id.song_list);

        //SongAdapter songAdt = new SongAdapter(getActivity().getApplicationContext(), songList);
        //songView.setAdapter(songAdt);

        //setController();

        return v;
    }

    @Override
    public void onAttach(Activity activity){
        try {
            listener = (Interfaces.OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
        super.onAttach(activity);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(fragmentBroadcastReceiver,
                new IntentFilter(Constants.FRAGMENT_BROADCAST_RECEIVER));
        //LocalBroadcastManager.getInstance(getActivity()).registerReceiver(fragmentBroadcastReceiver,
        //        new IntentFilter(Constants.MUSIC_PLAYER));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // for the first page
            listener.onFragmentCreated(this.getId());
            Log.i("Fragment", "call activity: " + this.getId());
        }
    }
}
