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

import com.yyy.xxx.churchbro.model.Bible;
import com.yyy.xxx.churchbro.model.BibleLab;

import java.util.ArrayList;

/**
 * Created by len on 2017. 2. 14..
 */
public class SelectVerseFragment  extends Fragment {

    private static final String EXTRA_BIBLE_VERSE_ID = "com.yyy.xxx.churchbro.bibleverse";
    private static final String EXTRA_BIBLE_ID = "com.yyy.xxx.churchbro.bible";
    private static final String TAG = SelectVerseFragment.class.getName();

    private RecyclerView mRecyclerView;
    private int verseNumber = 1;
    private int bibleNumber = 1;
    private Boolean endSelected = false;
    onTextSendListener mListener;

    public interface onTextSendListener{
        void isDeliverTextEnd(Boolean end);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach 호출");

        try {
            mListener = (onTextSendListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement onTextSendListener");
        }
    }

    public static Fragment newInstance(int bibleNumber,int chatperNumber) {
        SelectVerseFragment selectVerseFragment = new SelectVerseFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_BIBLE_ID, bibleNumber);
        args.putInt(EXTRA_BIBLE_VERSE_ID, chatperNumber);
        Log.d(TAG, chatperNumber + "is created");
        selectVerseFragment.setArguments(args);
        return selectVerseFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            verseNumber = getArguments().getInt(EXTRA_BIBLE_VERSE_ID);
            bibleNumber = getArguments().getInt(EXTRA_BIBLE_ID);
            Log.d(TAG, verseNumber + "is created");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_verse, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.verse_RecyclerView);

        VerseNameAdapter adapter = new VerseNameAdapter("t_kjv", verseNumber, bibleNumber);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
        return view;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView VerseNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            VerseNameTextView = (TextView) itemView.findViewById(R.id.item_bible_textview);
        }

        @Override
        public void onClick(View v) {
            Bible.getInstance().setVerse(getLayoutPosition() + 1);
            mListener.isDeliverTextEnd(true);

        }
    }

    public class VerseNameAdapter extends RecyclerView.Adapter<ViewHolder> {

        private ArrayList<Integer> VerseNameList;
        private BibleLab bibleLab;

        public VerseNameAdapter(String version, int bibleNum, int verseNum) {

            VerseNameList = new ArrayList<>();

            bibleLab = BibleLab.getIntence(getActivity());
            if (verseNum == 0){
                verseNum = 1;
            }
            VerseNameList = bibleLab.getSelectedVerse(version, bibleNum ,verseNum);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_bible, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
//            Log.d(TAG, VerseNameList.get(position) + "");
            holder.VerseNameTextView.setText(VerseNameList.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return VerseNameList.size();
        }
    }

}
