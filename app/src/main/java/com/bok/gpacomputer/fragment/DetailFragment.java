package com.bok.gpacomputer.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bok.gpacomputer.R;

/**
 * Created by JerichoJohn on 4/14/2017.
 */

public class DetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        return layoutInflater.inflate(R.layout.frag_detail , viewGroup, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String testStr = bundle.getString("test");
        }

    }

}
