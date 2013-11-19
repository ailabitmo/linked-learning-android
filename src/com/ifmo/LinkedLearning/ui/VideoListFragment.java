package com.ifmo.LinkedLearning.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.youtube.player.*;
import com.ifmo.LinkedLearning.R;

/**
 * Created with IntelliJ IDEA.
 * User: Федор
 * Date: 08.10.13
 * Time: 2:22
 * To change this template use File | Settings | File Templates.
 */


public class VideoListFragment extends Fragment implements
        YouTubePlayer.OnInitializedListener {
    final static String  DEVELOPER_KEY = "AIzaSyCfq33Mt4LpgTPJovVsQI5aoaG-RHxV5_g";
    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        if (container == null) {
            return null;
        }

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.video_list_fragment, container, false);
            YouTubePlayerSupportFragment youTubePlayerFragment = (YouTubePlayerSupportFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
            youTubePlayerFragment.initialize(DEVELOPER_KEY, this);
        } catch (InflateException e) {
        }
        return view;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(getArguments().getString(LectureListFragment.LECTURE_VIDEO_ID));
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.v("Youtube","Fail to load");
    }
}
