package com.yyy.xxx.churchbro;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity{

    private static final int FRAGMENT_BIBLE = 1;
    private static final int FRAGMENT_BOARD = 2;
    private static final int FRAGMENT_SETTINGS = 3;
    ImageButton btn_bible;
    ImageButton btn_board;
    ImageButton btn_settings;
    BibleFragment fragment_bible;
    BoardFragment fragment_board;
    SettingsFragment fragment_settings;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        setInit();

        fragment_bible = new BibleFragment();
        fragment_board = new BoardFragment();
        fragment_settings = new SettingsFragment();

        setFragmentView(FRAGMENT_BIBLE);

        btn_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentView(FRAGMENT_BOARD);
            }
        });
        btn_bible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentView(FRAGMENT_BIBLE);
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentView(FRAGMENT_SETTINGS);
            }
        });
    }

    private void setFragmentView(int view) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        switch (view)
        {
            case FRAGMENT_BIBLE:

                fragmentTransaction.replace(R.id.fragment, fragment_bible);

                break;

            case FRAGMENT_BOARD:

                fragmentTransaction.replace(R.id.fragment, fragment_board);

                break;

            case FRAGMENT_SETTINGS:

                fragmentTransaction.replace(R.id.fragment, fragment_settings);

                break;

        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

//        FramgentManager를 통하면 프레그먼트를 추가 및 삭제가 가능하다.
//        FRagmentManager 와 FragmentTransaction 차이
//        FragmentManager fm = getSupportFragmentManager();
//        fm.beginTransaction();
    }


    private void setInit() {
        btn_bible = (ImageButton) findViewById(R.id.btn_bible);
        btn_board = (ImageButton) findViewById(R.id.btn_board);
        btn_settings = (ImageButton) findViewById(R.id.btn_settings);

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.bible_menu, menu);
//
//        return super.onCreateOptionsMenu(menu);
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()){
//            case R.id.bible_search :
//                Toast.makeText(MainActivity.this, "검색", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.bible_settings :
//                Toast.makeText(MainActivity.this, "설정", Toast.LENGTH_SHORT).show();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
