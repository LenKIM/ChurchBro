package com.yyy.xxx.churchbro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;

import com.yyy.xxx.churchbro.model.Bible;

/**
 * Created by len on 2017. 2. 11..
 */

public class BibleSelectActivity extends AppCompatActivity
        implements SelectBibleFragment.onBibleNameListener, SelectChapterFragment.onChapterNumberistener,
SelectVerseFragment.onTextSendListener {


    private static final String TAG = BibleSelectActivity.class.getName();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context mContext;

    private Fragment bibleFragment;
    private Fragment chapterFragment;
    private Fragment chapterFragment2;
    private Fragment verseFragment;
    private Fragment verseFragment2;
    TabPagerAdapter pagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bible);

        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.select_activity_bible)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.select_activity_chapter)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.select_activity_verse)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);

        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void deliverBibleName(int biblePostion) {
        Log.d(TAG,"received Bible Number" + biblePostion);

        chapterFragment2 = SelectChapterFragment.newInstance(biblePostion);
        Bible.getInstance().setBible(biblePostion);
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(1);
    }

    @Override
    public void deliverChapterName(int chapterNumber) {
        Log.d(TAG, "received Chapter Number" + chapterNumber);

        Bible.getInstance().setChapter(chapterNumber);
        verseFragment2 = SelectVerseFragment.newInstance(Bible.getInstance().getBible(),chapterNumber);

        //화면을 새로고침하면서 상위 액티비티에 있는 프래그먼트에(BibleFragment)에다가 값 전달.

        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(2);
    }

    @Override
    public void isDeliverTextEnd(Boolean end) {

        if (end){
            Intent intent = new Intent(BibleSelectActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    public class TabPagerAdapter extends FragmentStatePagerAdapter {

        private int tabCount;

        public TabPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return bibleFragment = new SelectBibleFragment();
                case 1:

                    if (chapterFragment2 == null){
                        chapterFragment = new SelectChapterFragment();
                        return chapterFragment;
                    } else {
                        return chapterFragment2;
                    }
                case 2:

                    if (verseFragment2 == null) {
                        verseFragment = new SelectVerseFragment();
                        return verseFragment;
                    } else {
                        return verseFragment2;
                    }
                default:
                    return null;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragmet = (Fragment) super.instantiateItem(container, position);

            switch (position) {
                case 0 :
                    bibleFragment = (SelectBibleFragment) createdFragmet;
                    break;
                case 1 :
                    chapterFragment = (SelectChapterFragment)createdFragmet;
                    break;
                case 2 :
                    verseFragment = (SelectVerseFragment)createdFragmet;
                    break;
            }
            return createdFragmet;

        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }


}
