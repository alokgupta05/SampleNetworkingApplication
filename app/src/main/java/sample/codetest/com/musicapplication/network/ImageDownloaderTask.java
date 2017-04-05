package sample.codetest.com.musicapplication.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import sample.codetest.com.musicapplication.R;
import sample.codetest.com.musicapplication.adapter.TrackListAdapter;

/**
 * Created by Megha on 21-03-2017.
 */

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;

    private int mPosition;
    private TrackListAdapter.TrackViewHolder mTrackViewHolder;

    public ImageDownloaderTask(ImageView imageView,int position, TrackListAdapter.TrackViewHolder holder) {
        imageViewReference = new WeakReference<ImageView>(imageView);
        mPosition= position;
        mTrackViewHolder = holder;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        return downloadBitmap(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                if ((bitmap != null && mTrackViewHolder!=null && mPosition == mTrackViewHolder.position) || (bitmap!=null && mPosition==-1) ) {
                    //TODO : Images can be saved in Memory cache with LRU based technique

                    imageView.setImageBitmap(bitmap);
                } else {
                    Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.no_image_available);
                    imageView.setImageDrawable(placeholder);
                }
            }

        }
    }

    private Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            Log.d("URLCONNECTIONERROR", e.toString());
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            Log.w("ImageDownloader", "Error downloading image from " + url);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();

            }
        }
        return null;
    }
}