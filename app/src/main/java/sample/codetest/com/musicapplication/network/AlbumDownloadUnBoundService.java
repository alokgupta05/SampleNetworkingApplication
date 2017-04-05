package sample.codetest.com.musicapplication.network;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

import sample.codetest.com.musicapplication.beans.TrackBean;
import sample.codetest.com.musicapplication.listeners.ResponseCallback;
import sample.codetest.com.musicapplication.utils.Constant;

/**
 * Created by 482127 on 3/31/2017.
 */

public class AlbumDownloadUnBoundService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String  url = intent.getStringExtra(Constant.IMAGE_LIST_URL) ;
        new AlbumDownloadAsyncTask(new ResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                ArrayList<TrackBean> trackBeanList = (ArrayList<TrackBean>) response;
                Intent intentSend  = new Intent();
                intentSend.setAction(Constant.INTENT_TRACK_LIST);
                intentSend.putParcelableArrayListExtra(Constant.TRACK_LIST,trackBeanList);
                LocalBroadcastManager.getInstance(AlbumDownloadUnBoundService.this).sendBroadcast(intentSend);
            }

            @Override
            public void onError(Object error) {

                Intent intentSend  = new Intent();
                intentSend.setAction(Constant.INTENT_TRACK_LIST);
                LocalBroadcastManager.getInstance(AlbumDownloadUnBoundService.this).sendBroadcast(intentSend);
            }
        }).execute(url);
        return START_NOT_STICKY;
    }
}
