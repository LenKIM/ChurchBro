package com.yyy.xxx.churchbro;

import android.content.Context;
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

/**
 * Created by len on 2017. 2. 11..
 */

public class BibleSelectActivity extends AppCompatActivity implements SelectBibleFragment.onBibleNameListener{


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context mContext;
    private Fragment bibleFragment;
    private Fragment chapterFragment;
    private Fragment verseFragment;
    TabPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bible);
        mContext = this;

        mContext = this;
        // Adding Toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.select_activity_bible)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.select_activity_chapter)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.select_activity_verse)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
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

    }

    @Override
    public void deliverBibleName(int biblePostion) {
        Log.d("deliverBibleName", biblePostion + "");
        SelectChapterFragment.newInstance(biblePostion);
        pagerAdapter.notifyDataSetChanged();
        viewPager.invalidate();

        viewPager.setCurrentItem(1);

    }

    public class TabPagerAdapter extends FragmentStatePagerAdapter {

        // Count number of tabs
        private int tabCount;

        public TabPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {

            // Returning the current tabs
            switch (position) {
                case 0:
                    return new SelectBibleFragment();
                case 1:
                    return new SelectChapterFragment();
                case 2:
                    return new SelectVerseFragment();
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
        public int getCount() {
            return tabCount;
        }
    }
}
