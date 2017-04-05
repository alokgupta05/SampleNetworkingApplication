package sample.codetest.com.musicapplication.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sample.codetest.com.musicapplication.beans.TrackBean;
import sample.codetest.com.musicapplication.listeners.ResponseCallback;
import sample.codetest.com.musicapplication.utils.UtilHelper;


/**
 * Created by Alok on 20-03-2017.
 */

public class AlbumDownloadAsyncTask extends AsyncTask<String,Void,List<TrackBean>> {


    private ResponseCallback mCallback;

    public AlbumDownloadAsyncTask(ResponseCallback callback) {
        setCallback(callback);
    }

    void setCallback(ResponseCallback callback) {
        mCallback = callback;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<TrackBean> doInBackground(String... urls) {
        List<TrackBean> trackBeanList = null;
        if (!isCancelled() && urls != null && urls.length > 0) {
            String urlString = urls[0];
            trackBeanList = new UtilHelper().getAlbumList(urlString);

        }
        return trackBeanList;
    }

    @Override
    protected void onPostExecute(List<TrackBean> albumListBean) {
        if(albumListBean!=null && mCallback!=null)
            mCallback.onSuccess(albumListBean);
        else if(mCallback!=null){
            mCallback.onError(new Exception());
        }

    }


}
