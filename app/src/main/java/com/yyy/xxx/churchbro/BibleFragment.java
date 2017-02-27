package com.yyy.xxx.churchbro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yyy.xxx.churchbro.model.Bible;
import com.yyy.xxx.churchbro.model.BibleLab;

/**
 * Created by len on 2017. 2. 7..
 */
public class BibleFragment extends Fragment {

    private static final String TAG = BibleFragment.class.getName();
    private static final String ARG_BIBLE_POSTION = "bible_Position";
    private static final String ARG_CHAPTER_POSTION = "chapter_Position";
    private static final String ARG_VERSE_POSTION = "verse_Position";
    private TextView text_contents;

    public static BibleFragment newInstance (int bibleNumber, int chapterNumber, int verseNumber) {
        BibleFragment fragment = new BibleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BIBLE_POSTION, bibleNumber);
        args.putInt(ARG_CHAPTER_POSTION, chapterNumber);
        args.putInt(ARG_VERSE_POSTION, verseNumber);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bible, container, false);
        getToolbar(view);

        text_contents = (TextView) view.findViewById(R.id.text_contents);
        //TODO SQLite와 REALMS 가져오기.
        StringBuffer sb = new StringBuffer();

        String[][] text = BibleLab.getIntence(getActivity()).getSelectVerse("t_kjv", Bible.getInstance().getBible(),Bible.getInstance().getChapter());

        Log.d(TAG, text.length + "");
        for (int i = 0; i < text.length; i++) {
            String contents = " " +(i+1) + " " + text[i][1];
            sb.append(contents);
        }
        text_contents.setText(sb.toString());

        return view;
    }

    @NonNull
    private Toolbar getToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //for crate home button
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.ic_logo);
        Button toolbar_title = (Button) toolbar.findViewById(R.id.toolbar_name_title);
        //TODO 버튼 눌렀을 때 상위 toolbar에 입력되어야 함
//        toolbar_title.setText(Bible.getInstance().getBible());
        toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivity(new Intent(activity.getApplicationContext(), BibleSelectActivity.class));
//                Toast.makeText(getActivity(),"창세기를 눌렀음",Toast.LENGTH_SHORT).show();
            }
        });
        Button toolbar_version = (Button) toolbar.findViewById(R.id.toolbar_name_version);
        toolbar_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"버전을 눌렀음",Toast.LENGTH_SHORT).show();
            }
        });
        return toolbar;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bible_menu, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.bible_settings){
            Toast.makeText(getActivity(), "전송이 되는가?", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.bible_search){
            Toast.makeText(getActivity(), "검색이 되는가?", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
