package sample.codetest.com.musicapplication.fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sample.codetest.com.musicapplication.R;
import sample.codetest.com.musicapplication.adapter.TrackListAdapter;
import sample.codetest.com.musicapplication.beans.TrackBean;
import sample.codetest.com.musicapplication.listeners.OnListItemClickListener;
import sample.codetest.com.musicapplication.listeners.ResponseCallback;
import sample.codetest.com.musicapplication.network.AlbumDownloadAsyncTask;
import sample.codetest.com.musicapplication.network.AlbumDownloadBoundService;
import sample.codetest.com.musicapplication.network.AlbumDownloadIntentService;
import sample.codetest.com.musicapplication.network.AlbumDownloadUnBoundService;
import sample.codetest.com.musicapplication.utils.Constant;
import sample.codetest.com.musicapplication.utils.ServiceType;
import sample.codetest.com.musicapplication.utils.UtilHelper;

import static sample.codetest.com.musicapplication.utils.Constant.INTENT_TRACK_LIST;
import static sample.codetest.com.musicapplication.utils.Constant.TRACK_BEAN;

/**
 * Created by Megha on 21-03-2017.
 */

public class TrackListFragment extends BaseFragment implements ResponseCallback,OnListItemClickListener {


    private static  int SPAN = 3;
    private final ServiceType serviceType;
    private Context mContext;
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private TrackListAdapter mTrackListAdapter;
    private ProgressDialog mProgressDialog;
    private List<TrackBean> trackBeanList = new ArrayList<>();
    private TextView mEmptyView;
    private Handler mHandler = new Handler();
    public TrackListFragment(ServiceType serviceType){
        this.serviceType = serviceType;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View parentView = LayoutInflater.from(mContext).inflate(R.layout.fragment_track_list,null);
        mSearchView = (SearchView) parentView.findViewById(R.id.search_view);
        mSearchView.setIconifiedByDefault(false);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // do something on text submit
                mSearchView.clearFocus();
                showDialog();
                String url=  getUrl(query);
                if(serviceType==ServiceType.ASYNCTASK) {
                    new AlbumDownloadAsyncTask(TrackListFragment.this).execute(url);
                }else  if(serviceType==ServiceType.INTENTSERVICE) {

                    Intent intent = new Intent();
                    intent.putExtra(Constant.IMAGE_LIST_URL,url);
                    intent.setClass(mContext, AlbumDownloadIntentService.class);
                    mContext.startService(intent);

                }else if (serviceType==ServiceType.SERVICE) {

                    Intent intent = new Intent();
                    intent.putExtra(Constant.IMAGE_LIST_URL,url);
                    intent.setClass(mContext, AlbumDownloadUnBoundService.class);
                    mContext.startService(intent);

                }else if (serviceType==ServiceType.BOUNDSERVICE) {

                    Intent intent = new Intent();
                    intent.setClass(mContext, AlbumDownloadBoundService.class);
                    mContext.bindService(intent,mDownloadConnection, Context.BIND_AUTO_CREATE);

                }else{
                    new Thread(new DownloadRunnable(getUrl(query))).start();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // do something when text changes
                return false;
            }
        });
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mTrackListReceiver,
                new IntentFilter(INTENT_TRACK_LIST));
        mRecyclerView = (RecyclerView) parentView.findViewById(R.id.recycler_view);
        mTrackListAdapter = new TrackListAdapter(trackBeanList,this);
        mEmptyView = (TextView) parentView.findViewById(R.id.empty_view);

        RecyclerView.LayoutManager mLayoutManager;

        /*
        If tablet then show Grid Based layout else Linearlayout
         */
        //TODO : Use card view for tablet row view to get Material design look and feel eg : Use elevation
        if (isTablet(mContext)) {
            //TODO : if tablet in landscape then span can be increased to 5 or 6.
            mLayoutManager = new  GridLayoutManager(mContext,SPAN);
        }else{
            mLayoutManager = new LinearLayoutManager(mContext);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mTrackListAdapter);
        //if no data then show Empty View
        updateView();
        return parentView;
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private void showDialog(){
        dismissDialog();
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("fetching...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void dismissDialog(){
        if(mProgressDialog!=null&& mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
            mProgressDialog=null;
        }
    }

    private String getUrl(String searchAlbum){
        return Constant.BASE_URL + Uri.encode(searchAlbum, "UTF-8");
    }

    @Override
    public void onSuccess(Object response) {
        dismissDialog();
        if(response instanceof List ){
            List<TrackBean> trackBeanList = (List<TrackBean>) response;
            mTrackListAdapter.setTrackBeanList(trackBeanList);
            mTrackListAdapter.notifyDataSetChanged();
            this.trackBeanList = trackBeanList;
            updateView();
        }
    }
    private BroadcastReceiver mTrackListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            List<TrackBean> trackList = intent.getParcelableArrayListExtra(Constant.TRACK_LIST);
            onSuccess(trackList);
            updateView();

        }
    };
    /*
    Updates the view of page with empty view if list  is empty
     */
    private void updateView(){
        if (trackBeanList==null || trackBeanList.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
        else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(Object error) {
        dismissDialog();
        //TODO : Display error screen or message to user

    }
    ServiceConnection mDownloadConnection =   new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            AlbumDownloadBoundService albumDownloadUnBoundService = ((AlbumDownloadBoundService.AlbumDownloadBinder) iBinder).getService();
            albumDownloadUnBoundService.getDataFromServer(getUrl(mSearchView.getQuery().toString()));
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    public void onItemClick(View view) {
        int itemPosition = mRecyclerView.getChildLayoutPosition(view);
        TrackBean trackBean = trackBeanList.get(itemPosition);

        TrackDetailFragment trackDetailFragment = new TrackDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TRACK_BEAN,trackBean);
        trackDetailFragment.setArguments(bundle);
        pushFragment(trackDetailFragment);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mTrackListReceiver);
    }



   public class  DownloadRunnable implements Runnable {

       private String url;
       public DownloadRunnable(String url){
           this.url = url;
       }

       @Override
       public void run() {
           final ArrayList<TrackBean> trackBeanList = (ArrayList<TrackBean>) new UtilHelper().getAlbumList(url);
           mHandler.post(new Runnable() {
               @Override
               public void run() {
                   onSuccess(trackBeanList);
               }
           });

       }
   };
}
