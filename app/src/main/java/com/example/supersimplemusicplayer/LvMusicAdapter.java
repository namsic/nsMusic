package com.example.supersimplemusicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LvMusicAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<MusicItem> arr = null;

    public LvMusicAdapter(Context context, List<MusicItem> musicItemList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        arr = musicItemList;
    }
    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View res = mLayoutInflater.inflate(R.layout.listview_item, null);
        TextView musicTitle = (TextView) res.findViewById(R.id.lv_item_title);
        TextView musicArtist = (TextView) res.findViewById(R.id.lv_item_artist);
        musicTitle.setText(arr.get(i).title);
        musicArtist.setText(arr.get(i).artist);
        return res;
    }
}
