package com.example.supersimplemusicplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    PlayerControlView pcv;
    List<MusicItem> musicList;
    String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    ListView mlv;
    ExoPlayer player;
    List<MediaItem>playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicList = loadMusic();
        PlayerNamsic playerNamsic = (PlayerNamsic) getApplication();
        player = playerNamsic.getPlayer();
        playlist = new ArrayList<>();

        pcv = findViewById(R.id.main_pcv);
        mlv = findViewById(R.id.main_mlv);
        LvMusicAdapter adapter = new LvMusicAdapter(this, musicList);
        mlv.setAdapter(adapter);
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                playlist.clear();
                for(int idx = i; idx < adapter.getCount(); idx++) {
                    MusicItem tmp = (MusicItem) adapter.getItem(idx);
                    playlist.add(MediaItem.fromUri(tmp.uri));
                }
                player.setMediaItems(playlist);
                player.prepare();
                player.setPlayWhenReady(true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        pcv.setPlayer(player);
    }

    private ArrayList<MusicItem> loadMusic(){
        ArrayList<MusicItem> res = new ArrayList<>();
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
        };

        Cursor cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                MediaStore.Audio.Media.IS_MUSIC + "!= 0",
                null,
                MediaStore.Audio.Media.TITLE + " ASC"
        );
        if(cursor == null)
            return res;
        while(cursor.moveToNext()) {
            MusicItem item = new MusicItem();
            item.uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cursor.getInt(0));
            item.title = cursor.getString(1);
            item.artist = cursor.getString(2);
            res.add(item);
        }
        return res;
    }
}