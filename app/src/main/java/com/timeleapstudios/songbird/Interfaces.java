package com.timeleapstudios.songbird;

import java.util.ArrayList;

/**
 * Created by Usman on 11/25/2015.
 */
public class Interfaces {
    public interface OnFragmentInteractionListener {
        public void onFragmentCreated (int number);
        public void onSongListPopulated(ArrayList<Song> song_list);
    }

    public interface OnSongSelectionListener {
        public void onSongSelected (int number);
    }
}
