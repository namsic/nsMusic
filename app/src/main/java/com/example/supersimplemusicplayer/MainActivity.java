package com.example.supersimplemusicplayer;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ExoPlayer player;
    PlayerControlView pcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pcv = findViewById(R.id.main_pcv);
        initPlayer();

    }

    private void initPlayer() {
        if(player != null)
            return;
        player = new ExoPlayer.Builder(this).build();
        pcv.setPlayer(player);

        List<MediaItem> mediaItemList = new ArrayList<>();
        makePlayList(mediaItemList);

        player.setMediaItems(mediaItemList);
        player.prepare();
        player.setPlayWhenReady(true);
    }

    private void makePlayList(List<MediaItem> mediaitems){
        MediaItem mediaitem;
        Field[] raws = R.raw.class.getFields();
        for(int count=0; count < raws.length; count++){
            Log.e("Raw Asset", raws[count].getName());
            int musicID = this.getResources().getIdentifier(raws[count].getName(), "raw", this.getPackageName());
            Uri musicUri = RawResourceDataSource.buildRawResourceUri(musicID);
            mediaitem = MediaItem.fromUri(musicUri);
            mediaitems.add(mediaitem);
        }
    }
}