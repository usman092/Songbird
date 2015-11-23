package com.timeleapstudios.songbird;

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

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
            // signal your service to stop playback
            // (via an Intent, for instance)
            intent.setAction(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY);
            // add data
            intent.putExtra(Constants.AUDIO_NOISY, true);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
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
                    intent.setAction(Intent.ACTION_HEADSET_PLUG);
                    // add data
                    intent.putExtra(Constants.HEADSET_STATE, true);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    break;
                default:
                    Log.d(THIS_FILE, "Unknown headset state");
            }
        }

    }
}
