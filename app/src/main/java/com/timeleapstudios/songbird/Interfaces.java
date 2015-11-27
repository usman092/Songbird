package com.timeleapstudios.songbird;

import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by Usman on 11/25/2015.
 */
public class Interfaces {
    public interface OnFragmentInteractionListener {
        public void onFragmentCreated (int number);
        public void onSongListPopulated(ArrayList<Song> song_list);
        public void onBroadcast(Intent intent);
    }

    public interface OnSongSelectionListener {
        public void onSongSelected (int number);
    }
}
