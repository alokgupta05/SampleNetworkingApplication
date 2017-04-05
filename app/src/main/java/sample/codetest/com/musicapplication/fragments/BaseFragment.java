package sample.codetest.com.musicapplication.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import sample.codetest.com.musicapplication.R;


/**
 * Created by Megha on 21-03-2017.
 */


public class BaseFragment extends Fragment
{
    private FragmentActivity fragmentActivity = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity)context;
    }

    protected void pushFragment(boolean addToHistory, Fragment fragment){

        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main,fragment,fragment.getClass().getSimpleName());
        if(addToHistory)
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
        //TODO : We can use animation for smoother transistion from fragment to frament
        fragmentActivity.getSupportFragmentManager().executePendingTransactions();

    }

    protected void pushFragment( Fragment fragment){
        pushFragment(true,  fragment);
    }
}
