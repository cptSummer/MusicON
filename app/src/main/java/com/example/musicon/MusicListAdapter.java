package com.example.musicon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import com.example.easytutomusicapp.R;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    ArrayList<AudioModel> songsList;
    Context context;

    public MusicListAdapter(ArrayList<AudioModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MusicListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicListAdapter.ViewHolder holder, int position) {
        AudioModel songData = songsList.get(position);
        holder.titleTextView.setText(songData.getTitle());
        holder.durationView.setText(durationConverter(songData.getDuration()));
        holder.musicSize.setText(sizeConverter(songData.getSize()));


        if (MyMediaPlayer.currentIndex == position) {
            holder.titleTextView.setTextColor(Color.parseColor("#EEEEEE"));
        } else {
            holder.titleTextView.setTextColor(Color.parseColor("#EEEEEE"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to another acitivty

                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                Intent intent = new Intent(context, MusicPlayerActivity.class);
                intent.putExtra("LIST", songsList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    private String sizeConverter(String size) {
        if (size != null) {
            long sizeInBytes = Long.parseLong(size);
            long sizeInKB = sizeInBytes / 1024;
            long sizeInMB = sizeInKB / 1024;
            if (sizeInMB > 0) {
                return String.format("%dmb", sizeInMB);
            } else {
                return String.format("%dkb", sizeInKB);
            }
        }
        return "0mb";
    }

    private String durationConverter(String duration) {
        if (duration != null) {
            long durationMicroSec = Long.parseLong(duration);
            long durationSec = (durationMicroSec / 1000);
            long minutes = (durationSec % 3600) / 60;
            long seconds = durationSec % 60;
            if (minutes > 0) {
                return String.format("%d:%02d", minutes, seconds);
            } else {
                return String.format("0:%02d", seconds);
            }
        }

        return "00:00";
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        ImageView iconImageView;
        TextView durationView;
        TextView musicSize;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.music_title_text);
            iconImageView = itemView.findViewById(R.id.icon_view);
            durationView = itemView.findViewById(R.id.duration_item);
            musicSize = itemView.findViewById(R.id.music_size);
        }
    }
}
