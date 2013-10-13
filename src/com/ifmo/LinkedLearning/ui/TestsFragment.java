package com.ifmo.LinkedLearning.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ifmo.LinkedLearning.R;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 08.10.13
 * Time: 2:22
 * To change this template use File | Settings | File Templates.
 */
public class TestsFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        if (container == null) {
            return null;
        }
        return inflater.inflate(R.layout.no_info, container, false);
    }
}
