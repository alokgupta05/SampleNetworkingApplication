package sample.codetest.com.musicapplication.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sample.codetest.com.musicapplication.R;
import sample.codetest.com.musicapplication.beans.TrackBean;
import sample.codetest.com.musicapplication.network.ImageDownloaderTask;
import sample.codetest.com.musicapplication.utils.Constant;
import sample.codetest.com.musicapplication.utils.UtilHelper;

import static sample.codetest.com.musicapplication.utils.Constant.COLON;
import static sample.codetest.com.musicapplication.utils.Constant.INPUT_DATE;
import static sample.codetest.com.musicapplication.utils.Constant.OUTPUT_DATE;

/**
 * Created by Megha on 21-03-2017.
 */

public class TrackDetailFragment extends BaseFragment {

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = LayoutInflater.from(mContext).inflate(R.layout.fragment_track_detail
                ,null);
        TextView txtTrackName = (TextView) parentView.findViewById(R.id.textViewTrackName);
        TextView txtAlbumName = (TextView) parentView.findViewById(R.id.textViewAlbumName);
        TextView txtArtistName = (TextView) parentView.findViewById(R.id.textViewArtistName);
        TextView txtPrice = (TextView) parentView.findViewById(R.id.textViewPrice);
        TextView txtReleaseDate = (TextView) parentView.findViewById(R.id.textViewReleaseDate);
        Bundle bundle=  getArguments();

        if(bundle!=null && bundle.getParcelable(Constant.TRACK_BEAN)!=null){
            TrackBean trackBean =  bundle.getParcelable(Constant.TRACK_BEAN);
            txtTrackName.setText(COLON +trackBean.trackName);
            txtAlbumName.setText(COLON + trackBean.albumName);
            txtArtistName.setText(COLON + trackBean.artistName);
            if (new UtilHelper().isValidCurrency(trackBean.currency))
                 txtPrice.setText(COLON + UtilHelper.getCurrencySymbol(trackBean.currency)+trackBean.trackPrice);





            if(new UtilHelper().isThisDateValid(trackBean.releaseDate, INPUT_DATE)) {
                SimpleDateFormat src = new SimpleDateFormat(INPUT_DATE);
                Date date = null;
                try {
                    date = src.parse(trackBean.releaseDate);
                } catch (ParseException e) {
                    //handle exception
                    e.printStackTrace();
                }
                SimpleDateFormat dest = new SimpleDateFormat(OUTPUT_DATE);
                String result = dest.format(date);
                txtReleaseDate.setText(COLON + result);
            }
            new ImageDownloaderTask((ImageView) parentView.findViewById(R.id.imageViewArt),-1,null).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,trackBean.artworkUrl100);
        }

        return  parentView;
    }

}
