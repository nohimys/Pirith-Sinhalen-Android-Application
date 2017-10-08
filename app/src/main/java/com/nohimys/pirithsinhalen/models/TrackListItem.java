package com.nohimys.pirithsinhalen.models;

/**
 * Created by Nohim Sandeepa on 10/8/2017.
 */

public class TrackListItem {
    private String trackName;
    private String trackLink;

    public TrackListItem(){

    }

    public TrackListItem(String trackName, String trackLink){
        this.trackLink = trackLink;
        this.trackName = trackName;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getTrackLink() {
        return trackLink;
    }
}

