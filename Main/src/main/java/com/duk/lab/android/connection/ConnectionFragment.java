package com.duk.lab.android.connection;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.duk.lab.android.R;

/**
 * Created by Duk on 2016-12-19.
 */

public class ConnectionFragment extends Fragment implements View.OnClickListener {

    private HttpConnectionHelper mHttpConnectionHelper;
    private TextView mResultView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.connection_main, container, false);
        view.findViewById(R.id.url1).setOnClickListener(this);

        mResultView = (TextView) view.findViewById(R.id.resultText);
        mHttpConnectionHelper = new HttpConnectionHelper(mHttpConnectionListener);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.url1:
                mHttpConnectionHelper.requestUrl("https://api.github.com");
                break;
        }
    }

    private HttpConnectionHelper.HttpConnectionListener mHttpConnectionListener = new HttpConnectionHelper.HttpConnectionListener() {

        @Override
        public void onReceiveString(String result) {
            mResultView.setText(result);
        }
    };
}