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
public class ModuleInfoFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.module_info_fragment, container, false);
        Bundle a =getArguments();
        ((TextView)view.findViewById(R.id.module_name)).setText(getArguments().getString(ModuleListActivity.MODULE_NAME));
        ((TextView)view.findViewById(R.id.module_number)).setText(String.valueOf(getArguments().getInt(ModuleListActivity.MODULE_NUMBER)));

        return view;
    }
}
