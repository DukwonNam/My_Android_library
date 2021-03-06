package com.duk.lab.android.calendar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duk.lab.android.R;
import com.duk.lab.android.media.VideoPlayerActivity;
import com.duk.lab.android.util.CommonUtil;

/**
 * Created by dukwonnam on 2017. 1. 24..
 */

public class CalendarMainFragment extends Fragment implements View.OnClickListener {
    private static final int[] ON_CLICK_LISTENER_ID_ARRAY = new int[] {
            R.id.monthCalendar,
            R.id.weekCalendar
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.media_main, container, false);
        for (int resId : ON_CLICK_LISTENER_ID_ARRAY) {
            view.findViewById(resId).setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.monthCalendar:
                CommonUtil.jumpToActivity(getActivity(), CalendarActivity.class);
                break;
        }
    }
}
