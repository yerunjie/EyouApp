package com.fitibo.eyouapp.main.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fitibo.eyouapp.R;
import com.fitibo.eyouapp.base.EyouBaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends EyouBaseFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        setTitleString("首页");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    public IndexFragment() {
        // Required empty public constructor
    }

    public static IndexFragment newInstance() {
        return new IndexFragment();
    }
}
