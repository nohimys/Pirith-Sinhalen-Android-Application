package com.nohimys.pirithsinhalen.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nohimys.pirithsinhalen.R;
import com.nohimys.pirithsinhalen.models.TrackListItem;

import java.util.List;

/**
 * Created by Nohim Sandeepa on 10/8/2017.
 */

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.MyViewHolder> {

    private List<TrackListItem> trackListItemList;

    public TrackListAdapter(List<TrackListItem> trackListItemList){
        this.trackListItemList = trackListItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_track_list, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TrackListItem trackListItem = trackListItemList.get(position);
        holder.trackName.setText(trackListItem.getTrackName());
    }


    @Override
    public int getItemCount() {
        return trackListItemList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView trackName;

        public MyViewHolder(View view) {
            super(view);

            trackName = (TextView) view.findViewById(R.id.text_view_trak_list_track_name);
        }
    }
}
