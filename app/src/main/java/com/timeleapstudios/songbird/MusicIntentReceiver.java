package com.timeleapstudios.songbird;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by Usman on 11/22/2015.
 */
public class MusicIntentReceiver extends android.content.BroadcastReceiver {

    private String THIS_FILE = "MusicIntentReceiver";

    private Interfaces.OnFragmentInteractionListener listener;
    private boolean initialStart = false;

    private Activity act;

    public interface onReceiveEvents{
        public void onReceiveBroadcast(String event);
    }

    public MusicIntentReceiver(Activity activity){
        act = activity;
        try {
            listener = (Interfaces.OnFragmentInteractionListener) act;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
            // signal your service to stop playback
            // (via an Intent, for instance)
            listener.onBroadcast(intent);
            Log.d(THIS_FILE, "Audio has become noisy");
        }
        else if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    Log.d(THIS_FILE, "Headset is unplugged");
                    break;
                case 1:
                    Log.d(THIS_FILE, "Headset is plugged");
                    //intent.setAction(Intent.ACTION_HEADSET_PLUG);
                    // add data
                    //intent.putExtra(Constants.HEADSET_STATE, true);
                    //LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    listener.onBroadcast(intent);
                    break;
                default:
                    Log.d(THIS_FILE, "Unknown headset state");
            }
        }
        else if(intent.getAction().equals(Constants.MUSIC_PLAYER)){
            listener.onBroadcast(intent);
            Log.d(THIS_FILE, intent.getAction().toString());
        }


    }
}
