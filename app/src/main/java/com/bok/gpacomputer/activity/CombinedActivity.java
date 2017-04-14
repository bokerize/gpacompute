package com.bok.gpacomputer.activity;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bok.gpacomputer.R;
import com.bok.gpacomputer.fragment.DetailFragment;
import com.bok.gpacomputer.fragment.GpaListFragment;

/**
 * Created by JerichoJohn on 4/14/2017.
 */

public class CombinedActivity extends AppCompatActivity implements GpaListFragment.OnItemSelectedListener {

    private static final String TAG = "CombinedActivity";

    private DetailFragment detailFragment;
    private GpaListFragment gpaListFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.combined_view);

        replaceFragments();

    }

    @Override
    public void onListItemSelected(String link) {
        Log.d(TAG, "onliest item selected " + link);
    }

    private void replaceFragments() {
        FragmentManager fm = getSupportFragmentManager();

        detailFragment = new DetailFragment();
        gpaListFragment = new GpaListFragment();

        // add
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.detailscontainer, detailFragment);
        ft.commit();
        ft = fm.beginTransaction();
        ft.replace(R.id.listcontainer, gpaListFragment);
        ft.commit();

//        gpaListFragment.loadData();

    }

//    private void replaceFragments() {
//
//        FragmentManager fm = getSupportFragmentManager();
//        // replace
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.listcontainer, new GpaListFragment());
//        ft.replace(R.id.detailscontainer, new DetailFragment());
//        ft.commit();
//
//    }
//
//
//    private void removeFragments() {
//
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment gpaListFragment = fm.findFragmentById(R.id.listcontainer);
//        Fragment fragment = fm.findFragmentById(R.id.detailscontainer);
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.remove(fragment);
//        ft.remove(gpaListFragment);
//
//        ft.commit();
//    }

}
