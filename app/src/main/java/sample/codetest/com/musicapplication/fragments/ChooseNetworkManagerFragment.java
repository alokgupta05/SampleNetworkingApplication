package sample.codetest.com.musicapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sample.codetest.com.musicapplication.R;
import sample.codetest.com.musicapplication.utils.ServiceType;

/**
 * Created by 482127 on 3/31/2017.
 */

public class ChooseNetworkManagerFragment extends BaseFragment {

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = LayoutInflater.from(mContext).inflate(R.layout.fragment_choose_network
                , null);
        TextView txtBoundService = (TextView) parentView.findViewById(R.id.textViewBoundService);
        txtBoundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushFragment(new TrackListFragment(ServiceType.BOUNDSERVICE));
            }
        });
        TextView txtUnBoundService = (TextView) parentView.findViewById(R.id.textViewUnBoundService);
        txtUnBoundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushFragment(new TrackListFragment(ServiceType.SERVICE));
            }
        });
        TextView txtAsyncTaskService = (TextView) parentView.findViewById(R.id.textViewAsyncTask);
        txtAsyncTaskService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushFragment(new TrackListFragment(ServiceType.ASYNCTASK));

            }
        });
        TextView txtThreadervice = (TextView) parentView.findViewById(R.id.textViewThread);
        txtThreadervice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushFragment(new TrackListFragment(ServiceType.THREAD));
            }
        });
        TextView txtIntentService = (TextView) parentView.findViewById(R.id.textViewIntentService);
        txtIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushFragment(new TrackListFragment(ServiceType.INTENTSERVICE));
            }
        });

        return parentView;
    }
}
