package com.nohimys.pirithsinhalen.models;

import java.util.concurrent.TimeUnit;

/**
 * Created by Nohim Sandeepa on 10/8/2017.
 */

public class SimpleTime {
    //These two together gives the full value for the object
    private int minutes;
    private int seconds;

    //This along has the full value for the object
    private long miliSecondsCompleteValue;

    //region Constructors

    public SimpleTime(SimpleTime simpleTime){
        this.minutes = simpleTime.getOnlyMinutes();
        this.seconds = simpleTime.getOnlySeconds();
        this.miliSecondsCompleteValue = simpleTime.getAllMiliSeconds();
    }

    public SimpleTime(long miliSecondsCompleteValue){
        this.miliSecondsCompleteValue = miliSecondsCompleteValue;

        //Conver the above value to minutes and seconds and store
        convertAndStoreToMinutesAndSeconds(miliSecondsCompleteValue);
    }

    public SimpleTime(int minutes, int seconds){
        this.minutes = minutes;
        this.seconds = seconds;

        //Conver the above value to mili seconds and store
        convertAndStoreToMiliSeconds(minutes,seconds);
    }

    //endregion

    //region Private Convertion Methods

    private void convertAndStoreToMiliSeconds(int minutes, int seconds){

        this.miliSecondsCompleteValue = TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
    }

    private void convertAndStoreToMinutesAndSeconds(long miliSecondsCompleteValue){

        this.minutes = (int) TimeUnit.MILLISECONDS.toMinutes(miliSecondsCompleteValue);
        long remainedMiliSeconds = miliSecondsCompleteValue - TimeUnit.MINUTES.toMillis(this.minutes);
        this.seconds = (int) TimeUnit.MILLISECONDS.toMinutes(remainedMiliSeconds);
    }

    //endregion

    //region Getter Methods

    public int getOnlyMinutes(){
        return this.minutes;
    }

    public int getOnlySeconds(){
        return this.seconds;
    }

    public long getAllMiliSeconds(){
        return this.miliSecondsCompleteValue;
    }

    public String getTimeAsString(){
        return this.minutes + ":" + this.seconds;
    }

    //endregion
}
