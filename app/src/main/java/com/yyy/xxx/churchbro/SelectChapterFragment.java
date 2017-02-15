package com.yyy.xxx.churchbro;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yyy.xxx.churchbro.model.BibleLab;

import java.util.ArrayList;

/**
 * Created by len on 2017. 2. 14..
 */
public class SelectChapterFragment extends Fragment {

    private static final String TAG = "SelectChapterFragment";
    private static final String ARG_BIBLE_POSTION = "bible_postion";

    //TODO 상위 액티비티에서 가져와야함.
    private int biblePostion = 1;
    private RecyclerView mRecyclerView;
    private ChapterNameAdapter mChapterNameAdapter;


    public static SelectChapterFragment newInstance (int bibleNumber) {
        SelectChapterFragment fragment = new SelectChapterFragment();
        Bundle args = new Bundle();
        args.putInt("number", bibleNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach 호출");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCrate 호출");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart 호출");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume 호출");
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_chapter, container, false);
         mRecyclerView = (RecyclerView) view.findViewById(R.id.chapter_RecyclerView);

        mChapterNameAdapter = new ChapterNameAdapter();
        Log.d("onCreateSelectChapter", biblePostion + "");
        mRecyclerView.setAdapter(mChapterNameAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));


        return view;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView ChapterNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ChapterNameTextView = (TextView) itemView.findViewById(R.id.item_bible_textview);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), ChapterNameTextView.getText() + "", Toast.LENGTH_SHORT).show();

        }
    }

    private class ChapterNameAdapter extends RecyclerView.Adapter<ViewHolder> {

        private ArrayList<String> ChapterNameList;
        private BibleLab bibleLab;

        public ChapterNameAdapter() {
            ChapterNameList = new ArrayList<>();
            bibleLab = BibleLab.getIntence(getActivity());
            ChapterNameList = bibleLab.getSelectedChapter("t_kjv", biblePostion);
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_bible, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.ChapterNameTextView.setText(ChapterNameList.get(position));

        }

        @Override
        public int getItemCount() {
            return ChapterNameList.size();
        }
    }

}
