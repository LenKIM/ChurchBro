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

import com.yyy.xxx.churchbro.model.BibleLab;

import java.util.ArrayList;

/**
 * Created by len on 2017. 2. 14..
 */
public class SelectChapterFragment extends Fragment {

    private static final String TAG = "SelectChapterFragment";
    private static final String ARG_BIBLE_POSTION = "bible_postion";

    private int biblePostion = 1;
    onChapterNumberistener mCallback;

    public interface onChapterNumberistener{
        void deliverChapterName(int chapterNumber);
    }

    public static SelectChapterFragment newInstance (int bibleNumber) {
        SelectChapterFragment fragment = new SelectChapterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BIBLE_POSTION, bibleNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach 호출");

        try {
            mCallback = (onChapterNumberistener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement onChapterNumberistener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCrate 호출");
        if (getArguments() != null){
            biblePostion = getArguments().getInt(ARG_BIBLE_POSTION);
        }
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
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chapter_RecyclerView);

        ChapterNameAdapter chapterNameAdapter = new ChapterNameAdapter(biblePostion);

        recyclerView.setAdapter(chapterNameAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));


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
//            Toast.makeText(getActivity(), ChapterNameTextView.getText() + "", Toast.LENGTH_SHORT).show();
            mCallback.deliverChapterName(getLayoutPosition()+1);
        }
    }

    public class ChapterNameAdapter extends RecyclerView.Adapter<ViewHolder> {

        private ArrayList<String> ChapterNameList;
        private BibleLab bibleLab;

        public ChapterNameAdapter(int bibleNumber) {

            ChapterNameList = new ArrayList<>();

            bibleLab = BibleLab.getIntence(getActivity());
            if (bibleNumber == 0){
                bibleNumber = 1;
            }

            ChapterNameList = bibleLab.getSelectedChapter("t_kjv", bibleNumber);
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
