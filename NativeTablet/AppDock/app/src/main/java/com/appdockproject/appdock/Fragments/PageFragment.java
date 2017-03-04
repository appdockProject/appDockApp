package com.appdockproject.appdock.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appdockproject.appdock.R;

/**
 * Created by jangerhard on 03-Mar-17.
 */

public class PageFragment extends Fragment {

    public static PageFragment newInstance() {
        return new PageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_feedback, container, false);
    }
}
