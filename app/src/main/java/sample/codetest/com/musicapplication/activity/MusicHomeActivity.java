package sample.codetest.com.musicapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import sample.codetest.com.musicapplication.R;
import sample.codetest.com.musicapplication.fragments.ChooseNetworkManagerFragment;
import sample.codetest.com.musicapplication.fragments.TrackListFragment;


/**
 * Created by Megha on 20-03-2017.
 */

public class MusicHomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.music_home_activity);
        //Push the landing fragment to Main Screen
        pushFragment(new ChooseNetworkManagerFragment());
    }

    protected void pushFragment( Fragment fragment){

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main,fragment,fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
        //TODO : We can use animation for smoother transistion  to frament
        this.getSupportFragmentManager().executePendingTransactions();

    }
}
