package sample.codetest.com.musicapplication.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sample.codetest.com.musicapplication.beans.TrackBean;
import sample.codetest.com.musicapplication.utils.Constant;
import sample.codetest.com.musicapplication.utils.UtilHelper;

/**
 * Created by 482127 on 3/31/2017.
 */

public class AlbumDownloadIntentService extends IntentService {



    public AlbumDownloadIntentService() {
        super(AlbumDownloadIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String urlString = intent.getStringExtra(Constant.IMAGE_LIST_URL);
        final ArrayList<TrackBean> trackBeanList = (ArrayList<TrackBean>) new UtilHelper().getAlbumList(urlString);
        Intent intentSend  = new Intent();
        intentSend.setAction(Constant.INTENT_TRACK_LIST);
        intentSend.putParcelableArrayListExtra(Constant.TRACK_LIST,trackBeanList);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentSend);
    }

}
