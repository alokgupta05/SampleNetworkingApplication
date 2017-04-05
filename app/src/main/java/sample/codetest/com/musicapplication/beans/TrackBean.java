package sample.codetest.com.musicapplication.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Megha on 20-03-2017.
 */
public class TrackBean implements Parcelable{
    /*
    Making all variables public to avoid boiler plate due to getter and setter methods
     */
    public String artistName;
    public String trackId;
    public String trackName;
    public String artworkUrl100;
    public String artworkUrl30;
    public String artworkUrl60;
    public String albumName;
    public String trackPrice;
    public String releaseDate;
    public String currency;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
