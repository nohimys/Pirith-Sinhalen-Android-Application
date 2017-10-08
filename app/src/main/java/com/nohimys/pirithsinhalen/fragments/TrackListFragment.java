package com.nohimys.pirithsinhalen.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nohimys.pirithsinhalen.R;
import com.nohimys.pirithsinhalen.adapters.TrackListAdapter;
import com.nohimys.pirithsinhalen.decorations.DividerItemDecoration;
import com.nohimys.pirithsinhalen.listerners.RecyclerTouchListener;
import com.nohimys.pirithsinhalen.models.TrackListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackListFragment extends Fragment {

    @BindView(R.id.recycler_view_tracks)
    RecyclerView recyclerView;

    private TrackListAdapter trackListAdapter;
    private List<TrackListItem> listOfTracks;

    private OnTrackSelectedListerner onTrackSelectedListerner;

    public TrackListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrackListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackListFragment newInstance(String param1, String param2) {
        TrackListFragment fragment = new TrackListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View resultView =  inflater.inflate(R.layout.fragment_track_list, container, false);

        ButterKnife.bind(this, resultView);

        listOfTracks = new ArrayList<TrackListItem>();
        listOfTracks.add(new TrackListItem(getResources().getString(R.string.track_name_pansil_maluwa_1),getResources().getString(R.string.track_link_pansil_maluwa_1)));
        listOfTracks.add(new TrackListItem(getResources().getString(R.string.track_name_pansil_maluwa_2),getResources().getString(R.string.track_link_pansil_maluwa_2)));
        //listOfTracks.add(new TrackListItem(getResources().getString(R.string.track_name_pansil_maluwa_3),getResources().getString(R.string.track_link_pansil_maluwa_3)));
        //listOfTracks.add(new TrackListItem(getResources().getString(R.string.track_name_pansil_maluwa_4),getResources().getString(R.string.track_link_pansil_maluwa_4)));


        trackListAdapter = new TrackListAdapter(listOfTracks);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(resultView.getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //Add a divider to the Recycler List View
        recyclerView.addItemDecoration((new DividerItemDecoration(resultView.getContext(), LinearLayoutManager.VERTICAL)));

        //Set Click Listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(resultView.getContext().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TrackListItem trackListItem = listOfTracks.get(position);
                //Toast.makeText(resultView.getContext().getApplicationContext(), trackListItem.getTrackName() + " is selected!", Toast.LENGTH_SHORT).show();
                if(onTrackSelectedListerner != null){
                    onTrackSelectedListerner.onSelected(trackListItem);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView.setAdapter(trackListAdapter);

        trackListAdapter.notifyDataSetChanged();

        return resultView;
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnTrackSelectedListerner) {
            onTrackSelectedListerner = (OnTrackSelectedListerner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTrackSelectedListerner");
        }
    }

    /*
        Communication To The Activity
         */
    public interface OnTrackSelectedListerner{
        void onSelected(TrackListItem trackListItem);
    }

}
