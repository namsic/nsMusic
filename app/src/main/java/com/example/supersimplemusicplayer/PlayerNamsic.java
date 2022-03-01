package com.example.supersimplemusicplayer;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;

import java.util.ArrayList;
import java.util.List;

public class PlayerNamsic extends Application {
    private ExoPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();

        if (player == null) {
            player = new ExoPlayer.Builder(this).build();
            Log.d("mylog", "new player");
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        if (player != null) {
            player.release();
            player = null;
        }
    }

    public ExoPlayer getPlayer() {
        return player;
    }
}