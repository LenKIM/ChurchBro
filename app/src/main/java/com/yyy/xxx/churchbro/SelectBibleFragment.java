package com.yyy.xxx.churchbro;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yyy.xxx.churchbro.model.BibleLab;

import java.util.ArrayList;

/**
 * Created by len on 2017. 2. 14..
 */
public class SelectBibleFragment extends Fragment {

    private RecyclerView mBibleRecyclerView;
    private bibleNameAdapter mAdapter;
    private int bibleNameNumber;
    private String biblePostion;
    onBibleNameListener mBibleNameListener;

    //상위 액티비티와 통신하기 위한 인터페이스
    public interface onBibleNameListener{
        void deliverBibleName(int biblePostion);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_bible, container, false);

        mBibleRecyclerView = (RecyclerView) view.findViewById(R.id.bible_RecyclerView);
        mBibleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new bibleNameAdapter();


        mBibleRecyclerView.setAdapter(mAdapter);
        return view;
    }



    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView bibleNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            bibleNameTextView = (TextView) itemView.findViewById(R.id.item_bible_textview);
        }

        @Override
        public void onClick(View v) {
            //성경 번호를 넘김. +1을 하는 이유는 번호가 1부터 시작하기 때문입니다
            bibleNameNumber = getLayoutPosition()+1;
            mBibleNameListener.deliverBibleName(bibleNameNumber);

        }
    }

    private class bibleNameAdapter extends RecyclerView.Adapter<ViewHolder> {

        private ArrayList<String> bibleNameList;
        private BibleLab bibleLab;

        public bibleNameAdapter() {
            bibleNameList = new ArrayList<>();
            bibleLab = BibleLab.getIntence(getActivity());
            bibleNameList = bibleLab.setbibleNames();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_bible, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.bibleNameTextView.setText(bibleNameList.get(position));

        }

        @Override
        public int getItemCount() {
            return bibleNameList.size();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mBibleNameListener = (onBibleNameListener) context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
