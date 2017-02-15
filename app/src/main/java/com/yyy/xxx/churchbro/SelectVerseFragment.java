package com.yyy.xxx.churchbro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by len on 2017. 2. 14..
 */
public class SelectVerseFragment  extends Fragment {

    private static final String EXTRA_BIBLE_VERSE_ID = "com.yyy.xxx.churchbro.bibleverse";
    private RecyclerView mRecyclerView;

    public static Intent newIntent(Context packageContext, int bibleName) {
        Intent intent = new Intent(packageContext, SelectVerseFragment.class);
        intent.putExtra(EXTRA_BIBLE_VERSE_ID, bibleName);
        return intent;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_chapter, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.chapter_RecyclerView);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        return view;
    }

}
