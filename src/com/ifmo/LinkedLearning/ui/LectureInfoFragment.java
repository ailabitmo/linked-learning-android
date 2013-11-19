package com.ifmo.LinkedLearning.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ifmo.LinkedLearning.R;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 08.10.13
 * Time: 2:22
 * To change this template use File | Settings | File Templates.
 */
public class LectureInfoFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.lecture_info_fragment, container, false);
        Bundle a =getArguments();
        ((TextView)view.findViewById(R.id.module_name)).setText(getArguments().getString(LectureListFragment.LECTURE_NAME));
        ((TextView)view.findViewById(R.id.module_number)).setText(String.valueOf(getArguments().getInt(LectureListFragment.LECTURE_NUMBER)));

        return view;
    }
}
