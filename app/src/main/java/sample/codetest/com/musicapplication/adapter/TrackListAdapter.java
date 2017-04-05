package sample.codetest.com.musicapplication.adapter;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sample.codetest.com.musicapplication.R;
import sample.codetest.com.musicapplication.beans.TrackBean;
import sample.codetest.com.musicapplication.listeners.OnListItemClickListener;
import sample.codetest.com.musicapplication.network.ImageDownloaderTask;

/**
 * Created by Megha on 21-03-2017.
 */


public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.TrackViewHolder>  implements View.OnClickListener{

    private List<TrackBean> trackBeanList = new ArrayList<>();
    private OnListItemClickListener onListItemClickListener;

    public TrackListAdapter(List<TrackBean> trackBeanList, OnListItemClickListener onListItemClickListener) {
        this.trackBeanList = trackBeanList;
        this.onListItemClickListener = onListItemClickListener;
    }

    public void setTrackBeanList(List<TrackBean> trackBeanList) {
        this.trackBeanList = trackBeanList;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_list_row, parent, false);
        //Setting OnclickListener
        itemView.setOnClickListener(this);
        return new TrackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        TrackBean trackBean = trackBeanList.get(position);
        holder.trackName.setText(trackBean.trackName);
        holder.artistName.setText(trackBean.artistName);
        holder.position = position;
        //TODO : We can also use LRU cache implementation for faster retreival of images
        //Download image using Async Task and also using threadpool executor for faster display of images
        new ImageDownloaderTask(holder.artImageTrack,position,holder).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,trackBean.artworkUrl60);
    }

    @Override
    public int getItemCount() {
        return trackBeanList!=null?trackBeanList.size():0;
    }

    @Override
    public void onClick(View view) {
        if(onListItemClickListener !=null){
            onListItemClickListener.onItemClick(view);
        }
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder {
        public ImageView artImageTrack;
        public TextView  trackName, artistName;
        public int position = -1;

        public TrackViewHolder(View view) {
            super(view);

            artImageTrack = (ImageView) view.findViewById(R.id.trackArtImageView);
            trackName = (TextView) view.findViewById(R.id.trackName);
            artistName = (TextView) view.findViewById(R.id.artistName);


        }
    }


}
